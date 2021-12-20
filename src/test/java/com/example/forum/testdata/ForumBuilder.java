package com.example.forum.testdata;

import com.example.forum.command.InsertMessageBuilder;
import com.example.forum.command.InsertMessageboardBuilder;
import com.example.forum.dto.MessageboardId;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Represents the initial data, which is inserted to the database for integration tests.
 * Use TestdataDao to insert this. Override any of the default values relevant to your test case.
 */
public class ForumBuilder {
    /** TestdataDao will set the value to this generated field after database insert. */
    public MessageboardId messageboardId;

    public InsertMessageboardBuilder insertMessageboard = TestdataBuilder.insertMessageboard();

    public List<InsertMessageBuilder> messages = new ArrayList<>();


    public ForumBuilder withMessages(int count) {
        messages = IntStream.range(0, count).boxed()
                .map(idx -> TestdataBuilder.insertMessage()
                        .text("Some message!" + idx))
                .collect(Collectors.toList());

        return this;
    }

    public ForumBuilder withMessage(String text) {
        this.messages.add(TestdataBuilder.insertMessage()
                .text(text));
        return this;
    }
}
