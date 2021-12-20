package com.example.forum.exception;

import com.example.forum.log.LogMessageBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ForumException.class)
    public ResponseEntity<ErrorResponse> handleForumException(HttpServletRequest request, ForumException exception) {
        logError(request.getServletPath(), exception);
        return createErrorResponse(exception, exception.status, exception.getMessage());
    }

    protected ResponseEntity<Object> handleExceptionInternal(
            Exception exception, @Nullable Object body, HttpHeaders headers, HttpStatus httpStatus, WebRequest request) {
        // Find the root cause
        var causeException = NestedExceptionUtils.getMostSpecificCause(exception);
        // Hide the detailed message of unknown errors for security reasons
        var errorMessage = causeException instanceof ForumException ? causeException.getMessage() : "Internal Error";
        // Log consistently
        logError(((ServletWebRequest) request).getRequest().getServletPath(), causeException);
        var errorResponse = new ErrorResponse(causeException.getClass().getSimpleName(), errorMessage);
        return ResponseEntity
                .status(httpStatus)
                .body(errorResponse);
    }

    private void logError(String path, Throwable exception) {
        LogMessageBuilder builder = LogMessageBuilder.withThreadLocalValues()
                .add("path", path);
        LOG.error("{}", builder, exception);
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(Exception exception, HttpStatus httpStatus, String message) {
        var errorResponse = new ErrorResponse(exception.getClass().getSimpleName(), message);
        return ResponseEntity
                .status(httpStatus)
                .body(errorResponse);
    }
}
