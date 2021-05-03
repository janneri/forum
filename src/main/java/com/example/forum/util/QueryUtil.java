package com.example.forum.util;

import com.example.forum.dto.MessageId;
import com.example.forum.dto.MessageboardId;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class QueryUtil {
    private QueryUtil() {
        // util
    }

    public static MessageId getMessageId(ResultSet resultSet) throws SQLException {
        return new MessageId(resultSet.getInt("message_id"));
    }

    public static MessageboardId getMessageboardId(ResultSet resultSet) throws SQLException {
        return new MessageboardId(resultSet.getInt("messageboard_id"));
    }
}
