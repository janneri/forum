package com.example.forum.dto;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

public class Id {
    @JsonValue
    public final int intValue;

    public Id(int intValue) {
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
        Id messageId = (Id) o;
        return intValue == messageId.intValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(intValue);
    }
}
