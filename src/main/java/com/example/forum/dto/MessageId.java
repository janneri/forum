package com.example.forum.dto;

import java.util.Objects;

public final class MessageId extends Id {
    public MessageId(int intValue) {
        super(intValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageId messageId = (MessageId) o;
        return intValue == messageId.intValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(intValue);
    }
}
