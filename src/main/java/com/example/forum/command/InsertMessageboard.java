package com.example.forum.command;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = InsertMessageboard.Builder.class)
public final class InsertMessageboard {
    public final String name;

    private InsertMessageboard(Builder builder) {
        name = builder.name;
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
        private String name;

        private Builder() {
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public InsertMessageboard build() {
            return new InsertMessageboard(this);
        }
    }
}
