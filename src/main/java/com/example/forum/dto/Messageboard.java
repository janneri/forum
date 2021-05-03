package com.example.forum.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = Messageboard.Builder.class)
public final class Messageboard {
    public final MessageboardId messageboardId;
    public final String name;
    public final long messageCount;

    private Messageboard(Builder builder) {
        messageboardId = builder.messageboardId;
        name = builder.name;
        messageCount = builder.messageCount;
    }


    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
        private MessageboardId messageboardId;
        private String name;
        private long messageCount;

        private Builder() {
        }

        public Builder messageboardId(MessageboardId messageboardId) {
            this.messageboardId = messageboardId;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder messageCount(long messageCount) {
            this.messageCount = messageCount;
            return this;
        }

        public Messageboard build() {
            return new Messageboard(this);
        }
    }
}
