package com.example.forum.command;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = EditMessage.Builder.class)
public final class EditMessage {
    public final String text;

    private EditMessage(Builder builder) {
        text = builder.text;
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
        private String text;

        private Builder() {
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public EditMessage build() {
            return new EditMessage(this);
        }
    }
}
