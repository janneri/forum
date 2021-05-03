package com.example.forum.log;

import com.example.forum.util.StopWatch;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class LogMessageBuilder {
    private final boolean logParameters;
    private final boolean logReturnValues;
    private final Map<String, Object> keyVals;
    private static ThreadLocal<Long> txIdThreadLocal;
    private static ThreadLocal<String> actionThreadLocal;

    private LogMessageBuilder(Long txId, String action, boolean logParameters, boolean logReturnValues) {
        this.keyVals = new LinkedHashMap<>();
        this.keyVals.put("TxId", txId);
        this.keyVals.put("Action", action);
        this.logParameters = logParameters;
        this.logReturnValues = logReturnValues;
    }

    public LogMessageBuilder add(String key, Object val) {
        this.keyVals.put(key, val);
        return this;
    }

    public LogMessageBuilder time(StopWatch stopWatch) {
        this.keyVals.put("Time(ms)", stopWatch.end());
        return this;
    }

    public LogMessageBuilder throwsException(Exception e) {
        this.keyVals.put("Throws", e.getClass().getSimpleName());
        return this;
    }

    public LogMessageBuilder params(Optional<String> params) {
        if (logParameters) {
            this.keyVals.put("Params", params.orElse("-"));
        }

        return this;
    }

    public LogMessageBuilder returns(String returnValueAsString) {
        if (logReturnValues) {
            this.keyVals.put("Returns", returnValueAsString);
        }
        return this;
    }

    public static LogMessageBuilder withThreadLocalValues(final long txId, final String action,
                                                          boolean logParameters, boolean logReturnValues) {
        txIdThreadLocal = ThreadLocal.withInitial(() -> txId);
        actionThreadLocal = ThreadLocal.withInitial(() -> action);
        return new LogMessageBuilder(txId, action, logParameters, logReturnValues);
    }

    public static LogMessageBuilder withThreadLocalValues() {
        return new LogMessageBuilder(txIdThreadLocal != null ? txIdThreadLocal.get() : null,
                actionThreadLocal != null ? actionThreadLocal.get() : null, false, false);
    }

    public String toString() {
        return keyVals.keySet().stream()
                .map(key -> keyVals.get(key) == null ? null : key + "=" + keyVals.get(key))
                .collect(Collectors.joining(", "));
    }
}