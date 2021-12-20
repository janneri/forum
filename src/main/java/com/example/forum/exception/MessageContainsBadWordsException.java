package com.example.forum.exception;

import org.springframework.http.HttpStatus;

public class MessageContainsBadWordsException extends ForumException {
    public MessageContainsBadWordsException(String message) {
        super("Message contains bad words! Message=" + message, HttpStatus.BAD_REQUEST);
    }
}
