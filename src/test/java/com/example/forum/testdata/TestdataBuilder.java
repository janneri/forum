package com.example.forum.testdata;

import com.example.forum.command.InsertMessageBuilder;
import com.example.forum.command.InsertMessageboardBuilder;

public final class TestdataBuilder {

    // some existing random user from fixture
    public static int userId = 1;

    // a messageboard with default data
    public static InsertMessageboardBuilder insertMessageboard() {
        return InsertMessageboardBuilder.builder()
                .name("A Messageboard");
    }

    // a message with default data
    public static InsertMessageBuilder insertMessage() {
        return InsertMessageBuilder.builder()
                .userId(userId)
                .text("Some message!");
    }

}