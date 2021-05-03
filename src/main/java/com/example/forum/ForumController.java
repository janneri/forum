package com.example.forum;

import com.example.forum.command.EditMessage;
import com.example.forum.command.InsertMessage;
import com.example.forum.command.InsertMessageboard;
import com.example.forum.dto.Message;
import com.example.forum.dto.MessageId;
import com.example.forum.dto.Messageboard;
import com.example.forum.dto.MessageboardId;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ForumController {
    private ForumService forumService;

    public ForumController(ForumService forumService) {
        this.forumService = forumService;
    }

    @GetMapping("/messageboards")
    public List<Messageboard> getMessageboards() {
        return forumService.getMessageboards();
    }

    @PostMapping("/messageboards")
    public MessageboardId insertMessageboard(@RequestBody InsertMessageboard insertMessageboard) {
        return forumService.insertMessageboard(insertMessageboard);
    }

    @GetMapping("/messageboards/{messageboardId}/messages")
    public List<Message> getMessages(@PathVariable MessageboardId messageboardId) {
        return forumService.getMessages(messageboardId);
    }

    @PostMapping("/messageboards/{messageboardId}/messages")
    public Message insertMessage(@PathVariable MessageboardId messageboardId,
                                 @RequestBody InsertMessage insertMessage) {
        return forumService.insertMessage(messageboardId, insertMessage);
    }

    @DeleteMapping("/messageboards/{messageboardId}/messages/{messageId}")
    public int deleteMessage(@PathVariable MessageboardId messageboardId,
                             @PathVariable MessageId messageId) {
        return forumService.deleteMessage(messageboardId, messageId);
    }

    @PatchMapping("/messageboards/{messageboardId}/messages/{messageId}")
    public int editMessage(@PathVariable MessageboardId messageboardId,
                           @PathVariable MessageId messageId,
                           @RequestBody EditMessage editMessage) {
        return forumService.editMessage(messageboardId, messageId, editMessage);
    }

}
