package com.example.forum.command;

import io.soabase.recordbuilder.core.RecordBuilder;

import static com.example.forum.util.InputValidationUtil.requireTextLength;

@RecordBuilder
public record InsertMessageboard(String name) {

    public InsertMessageboard {
        requireTextLength(name, 3);
    }

}
