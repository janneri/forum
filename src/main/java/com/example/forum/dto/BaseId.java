package com.example.forum.dto;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

/** Parent class for all Ids */
public class BaseId {
    private final int intValue;

    @JsonValue
    public int intValue() {
        return intValue;
    }

    public BaseId(int intValue) {
        this.intValue = intValue;
    }

    @Override
    public String toString() {
        return "" + intValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseId messageId = (BaseId) o;
        return intValue == messageId.intValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(intValue);
    }
}
