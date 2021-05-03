package com.example.forum.testdata;

import com.example.forum.command.InsertMessage;
import com.example.forum.command.InsertMessageboard;
import com.example.forum.dto.MessageboardId;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ForumBuilder {
    public MessageboardId messageboardId;
    public InsertMessageboard.Builder insertMessageboard = InsertMessageboard.builder()
            .name("A Messageboard");

    public int userId = 1;

    public List<InsertMessage.Builder> messages = new ArrayList<>();

    public ForumBuilder withMessages(int count) {
        messages = IntStream.range(0, count).boxed()
                .map(idx -> TestdataBuilder.insertMessage
                        .text("Some message!" + idx))
                .collect(Collectors.toList());

        return this;
    }

    public ForumBuilder withMessage(String text) {
        this.messages.add(TestdataBuilder.insertMessage.text(text));
        return this;
    }
}
