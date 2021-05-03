package com.example.forum.exception;

import org.springframework.http.HttpStatus;

public class MessageContainsBadWordsException extends ForumException {
    public MessageContainsBadWordsException() {
        super("Message contains bad words!", HttpStatus.BAD_REQUEST);
    }
}
