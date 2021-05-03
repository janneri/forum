package com.example.forum;

import com.example.forum.command.EditMessage;
import com.example.forum.command.InsertMessage;
import com.example.forum.command.InsertMessageboard;
import com.example.forum.dto.Message;
import com.example.forum.dto.MessageId;
import com.example.forum.dto.Messageboard;
import com.example.forum.dto.MessageboardId;
import com.example.forum.log.AuditLog;
import com.example.forum.util.MessageModerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ForumService {
    private ForumDao forumDao;
    private MessageModerator messageModerator;

    public ForumService(ForumDao forumDao, MessageModerator messageModerator) {
        this.forumDao = forumDao;
        this.messageModerator = messageModerator;
    }

    @Transactional(readOnly = true)
    public List<Message> getMessages(MessageboardId messageboardId) {
        return forumDao.getMessages(messageboardId);
    }

    @Transactional(readOnly = true)
    public List<Messageboard> getMessageboards() {
        return forumDao.getMessageboards();
    }

    @AuditLog
    @Transactional
    public Message insertMessage(MessageboardId messageboardId, InsertMessage insertMessage) {
        messageModerator.check(insertMessage.text);
        return forumDao.insertMessage(messageboardId, insertMessage);
    }

    @AuditLog
    @Transactional
    public MessageboardId insertMessageboard(InsertMessageboard insertMessageboard) {
        return forumDao.insertMessageboard(insertMessageboard);
    }

    @AuditLog
    @Transactional
    public int deleteMessage(MessageboardId messageboardId, MessageId messageId) {
        return forumDao.deleteMessage(messageboardId, messageId);
    }

    @AuditLog
    @Transactional
    public int editMessage(MessageboardId messageboardId, MessageId messageId, EditMessage editMessage) {
        messageModerator.check(editMessage.text);
        return forumDao.editMessage(messageboardId, messageId, editMessage);
    }
}
