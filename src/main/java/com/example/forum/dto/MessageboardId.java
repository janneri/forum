package com.example.forum.dto;

import java.util.Objects;

public final class MessageboardId extends Id {
    public MessageboardId(int intValue) {
        super(intValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageboardId that = (MessageboardId) o;
        return intValue == that.intValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(intValue);
    }
}
