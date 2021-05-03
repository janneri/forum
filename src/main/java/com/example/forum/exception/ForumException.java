package com.example.forum.exception;

import org.springframework.http.HttpStatus;

public class ForumException extends RuntimeException {
    public HttpStatus status;

    public ForumException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

}
