package com.example.forum.log;

import com.example.forum.util.StopWatch;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Aspect
@Component
public class AuditLoggerAspect {
    private static final Logger LOG = LoggerFactory.getLogger(AuditLoggerAspect.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private AtomicLong txIdSequence = new AtomicLong();
    @Value("${auditlog.log.all.parameters}") private boolean logParameters;
    @Value("${auditlog.log.return.values}") private boolean logReturnValues;

    @Around("@annotation(auditLog)")
    public Object logMethods(ProceedingJoinPoint jp, AuditLog auditLog) throws Throwable {
        long txId = txIdSequence.incrementAndGet();
        String methodName = jp.getSignature().getName();
        Optional<String> params = parseParameters(jp);
        var logmessageBuilder = LogMessageBuilder.withThreadLocalValues(txId, methodName, logParameters, logReturnValues);

        LOG.info("{}", logmessageBuilder.params(params));

        var stopWatch = StopWatch.start();
        try {
            Object result = jp.proceed(jp.getArgs());
            LOG.info("{}", logmessageBuilder.time(stopWatch).returns(objectMapper.writeValueAsString(result)));
            return result;
        }
        catch (Exception e) {
            LOG.error("{}", logmessageBuilder.time(stopWatch).throwsException(e));
            throw e;
        }
    }

    private Optional<String> parseParameters(ProceedingJoinPoint jp) {
        if (!logParameters) {
            return Optional.empty();
        }

        String[] argNames = ((MethodSignature) jp.getSignature()).getParameterNames();
        Object[] values = jp.getArgs();
        Map<String, Object> params = new HashMap<>();
        if (argNames.length != 0) {
            for (int i = 0; i < argNames.length; i++) {
                params.put(argNames[i], values[i]);
            }
        }

        if (!params.isEmpty()) {
            try {
                return Optional.of(objectMapper.writeValueAsString(params));
            } catch (JsonProcessingException e) {
                LOG.info("Method {}. Parsing parameters failed!",  jp.getSignature().getName());
            }
        }

        return Optional.empty();
    }

}



