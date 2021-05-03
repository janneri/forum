package com.example.forum.testdata;

import com.example.forum.ForumDao;
import com.example.forum.dto.MessageboardId;
import com.example.forum.util.SimpleJdbcQuery;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class TestdataDao {
    private ForumDao forumDao;
    private SimpleJdbcQuery simpleJdbcQuery;

    public TestdataDao(ForumDao forumDao, DataSource  dataSource) {
        this.forumDao = forumDao;
        this.simpleJdbcQuery = new SimpleJdbcQuery(dataSource);
    }

    private List<MessageboardId> insertedMessageboards = new ArrayList<>();

    @Transactional
    public ForumBuilder insertForum(ForumBuilder forumBuilder) {
        forumBuilder.messageboardId = forumDao.insertMessageboard(forumBuilder.insertMessageboard.build());
        insertedMessageboards.add(forumBuilder.messageboardId);

        forumBuilder.messages.forEach(msg -> forumDao.insertMessage(forumBuilder.messageboardId, msg.build()));
        return forumBuilder;
    }

    @Transactional
    public void cleanTestdata() {
        insertedMessageboards.forEach(messageboardId -> {
            simpleJdbcQuery.queryStr("delete from message where messageboard_id = ?")
                    .params(messageboardId)
                    .execute();

            simpleJdbcQuery.queryStr("delete from messageboard where messageboard_id = ?")
                    .params(messageboardId)
                    .execute();
        });
    }
}
