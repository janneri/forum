package com.example.forum.dto;

public final class MessageboardId extends BaseId {
    public MessageboardId(int intValue) {
        super(intValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageboardId that = (MessageboardId) o;
        return intValue() == that.intValue();
    }

    public static MessageboardId valueOf(String valueStr) {
        return new MessageboardId(Integer.parseInt(valueStr));
    }
}
