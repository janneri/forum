package com.example.forum.command;

import static com.example.forum.util.InputValidationUtil.requireTextLength;

public record EditMessage(String text) {
    public EditMessage {
        requireTextLength(text, 2);
    }
}
