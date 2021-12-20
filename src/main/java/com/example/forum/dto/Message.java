package com.example.forum.dto;

import java.time.LocalDateTime;

public record Message(MessageId messageId, int userId, String text, LocalDateTime createTime) { }
