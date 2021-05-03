package com.example.forum.command;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = InsertMessage.Builder.class)
public final class InsertMessage {
    public final int userId;
    public final String text;

    private InsertMessage(Builder builder) {
        userId = builder.userId;
        text = builder.text;
    }


    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
        private int userId;
        private String text;

        private Builder() {
        }

        public Builder userId(int userId) {
            this.userId = userId;
            return this;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public InsertMessage build() {
            return new InsertMessage(this);
        }
    }
}
