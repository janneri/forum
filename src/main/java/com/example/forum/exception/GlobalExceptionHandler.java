package com.example.forum.exception;

import com.example.forum.log.LogMessageBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ForumException.class)
    public ResponseEntity<ErrorResponse> handleForumException(HttpServletRequest request, ForumException exception) {
        logError(request, exception);
        return createErrorResponse(exception, exception.status, exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(HttpServletRequest request, Exception exception) {
        logError(request, exception);
        // Hide the message of unknown exceptions for security reasons.
        return createErrorResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR, "Internal error");
    }

    private void logError(HttpServletRequest request, Exception exception) {
        LogMessageBuilder builder = LogMessageBuilder.withThreadLocalValues()
                .add("path", request.getServletPath());
        LOG.error("{}", builder, exception);
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(Exception exception, HttpStatus httpStatus, String message) {
        var errorResponse = new ErrorResponse(exception.getClass().getSimpleName(), message);
        return ResponseEntity
                .status(httpStatus)
                .body(errorResponse);
    }
}
