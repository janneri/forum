package com.example.forum.exception;

import org.springframework.http.HttpStatus;

public class InvalidJsonException extends ForumException {
    public InvalidJsonException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
