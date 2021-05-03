package com.example.forum.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.time.LocalDateTime;

@JsonDeserialize(builder = Message.Builder.class)
public final class Message {
    public final MessageId messageId;
    public final int userId;
    public final String text;
    public final LocalDateTime createTime;

    private Message(Builder builder) {
        messageId = builder.messageId;
        userId = builder.userId;
        text = builder.text;
        createTime = builder.createTime;
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
        private MessageId messageId;
        private int userId;
        private String text;
        private LocalDateTime createTime;

        private Builder() {
        }

        public Builder messageId(MessageId messageId) {
            this.messageId = messageId;
            return this;
        }

        public Builder userId(int userId) {
            this.userId = userId;
            return this;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder createTime(LocalDateTime createTime) {
            this.createTime = createTime;
            return this;
        }

        public Message build() {
            return new Message(this);
        }
    }
}
