package com.example.forum.testdata;

import com.example.forum.command.InsertMessage;
import com.example.forum.command.InsertMessageboard;

public final class TestdataBuilder {

    // some existing random user from fixture
    public static int userId = 1;

    // a messageboard with default data
    public static InsertMessageboard.Builder insertMessageboard = InsertMessageboard.builder()
            .name("A Messageboard");

    // a message with default data
    public static InsertMessage.Builder insertMessage = InsertMessage.builder()
            .userId(userId)
            .text("Some message!");

}