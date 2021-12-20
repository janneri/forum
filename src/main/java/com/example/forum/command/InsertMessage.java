package com.example.forum.command;

import io.soabase.recordbuilder.core.RecordBuilder;

import static com.example.forum.util.InputValidationUtil.requireTextLength;

@RecordBuilder
public record InsertMessage(int userId, String text) {

    public InsertMessage {
        requireTextLength(text, 2);
    }

}
