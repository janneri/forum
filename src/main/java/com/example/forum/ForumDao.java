package com.example.forum;

import com.example.forum.command.EditMessage;
import com.example.forum.command.InsertMessage;
import com.example.forum.command.InsertMessageboard;
import com.example.forum.dto.Message;
import com.example.forum.dto.MessageId;
import com.example.forum.dto.Messageboard;
import com.example.forum.dto.MessageboardId;
import com.example.forum.util.EnhancedRowMapper;
import com.example.forum.util.SimpleJdbcQuery;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.forum.util.QueryUtil.getMessageId;
import static com.example.forum.util.QueryUtil.getMessageboardId;

@Repository
public class ForumDao {
    private final SimpleJdbcQuery simpleJdbcQuery;
    private final SimpleJdbcInsert messageJdbcInsert;
    private final SimpleJdbcInsert messageBoardJdbcInsert;

    public ForumDao(DataSource dataSource) {
        this.simpleJdbcQuery = new SimpleJdbcQuery(dataSource);
        this.messageJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("message").usingGeneratedKeyColumns("message_id");
        this.messageBoardJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("messageboard").usingGeneratedKeyColumns("messageboard_id");
    }

    /*
    Note: With builder pattern the code would be less prone to errors with the order of constructor fields.
          On the other hand, the builder is boilerplate.

    private EnhancedRowMapper<Message> messageRowMapper = rs -> Message.builder()
            .messageId(getMessageId(rs))
            .userId(rs.getInt("user_id"))
            .createTime(rs.getTimestamp("create_time").toLocalDateTime())
            .text(rs.getString("text"))
            .build();
     */
    private final EnhancedRowMapper<Message> messageRowMapper = rs -> new Message(
            getMessageId(rs),
            rs.getInt("user_id"),
            rs.getString("text"),
            rs.getTimestamp("create_time").toLocalDateTime());

    public List<Message> getMessages(MessageboardId messageboardId) {
        return simpleJdbcQuery
                .queryStr("select * from message where messageboard_id = ? order by message_id")
                .params(messageboardId)
                .listWith(messageRowMapper);
    }

    public Message getMessage(MessageId messageId) {
        return simpleJdbcQuery
                .queryStr("select * from message where message_id = ?")
                .params(messageId)
                .getExactlyOne(messageRowMapper);
    }

    public List<Messageboard> getMessageboards() {
        var counts = getMessageCounts();
        return simpleJdbcQuery.queryStr("select * from messageboard order by messageboard_id")
                .listWith(rs -> new Messageboard(
                        getMessageboardId(rs),
                        rs.getString("name"),
                        counts.getOrDefault(getMessageboardId(rs), 0L)));
    }

    public Map<MessageboardId, Long> getMessageCounts() {
        var counts = new HashMap<MessageboardId, Long>();

        simpleJdbcQuery.queryStr("""
            select messageboard_id, count(*) msg_count
            from message group by messageboard_id
            """)
            .listWith(rs -> counts.put(getMessageboardId(rs), rs.getLong("msg_count")));

        return counts;
    }

    public Message insertMessage(MessageboardId messageboardId, InsertMessage insertMessage) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("messageboard_id", messageboardId.intValue())
                .addValue("user_id", insertMessage.userId())
                .addValue("text", insertMessage.text())
                .addValue("create_time", LocalDateTime.now());

        int id = messageJdbcInsert.executeAndReturnKey(params).intValue();
        return getMessage(new MessageId(id));
    }

    public MessageboardId insertMessageboard(InsertMessageboard insertMessageboard) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", insertMessageboard.name());

        int id = messageBoardJdbcInsert.executeAndReturnKey(params).intValue();
        return new MessageboardId(id);
    }

    public int deleteMessage(MessageboardId messageboardId, MessageId messageId) {
        return simpleJdbcQuery
                .queryStr("delete from message where messageboard_id = ? and message_id = ?")
                .params(messageboardId, messageId)
                .execute();
    }

    public int editMessage(MessageboardId messageboardId, MessageId messageId, EditMessage editMessage) {
        return simpleJdbcQuery
                .queryStr("update message set text = ? where messageboard_id = ? and message_id = ?")
                .params(editMessage.text(), messageboardId, messageId)
                .execute();
    }
}
