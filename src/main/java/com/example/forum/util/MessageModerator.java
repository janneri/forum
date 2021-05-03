package com.example.forum.util;

import com.example.forum.exception.MessageContainsBadWordsException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class MessageModerator {
    private static final List<String> BAD_WORDS = List.of("shit", "crap", "adidas");

    public void check(String message) {
        if (message != null && containsBadWords(message)) {
            throw new MessageContainsBadWordsException();
        };
    }

    private boolean containsBadWords(String message) {
        return BAD_WORDS.stream().anyMatch(badWord -> message.contains(badWord));
    }
}
