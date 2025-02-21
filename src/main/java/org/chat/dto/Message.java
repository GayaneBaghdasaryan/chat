package org.chat.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record Message(String id, String sender, String recipient, String group, String content, LocalDateTime timestamp) {

    public static Message create(String sender, String recipient, String group, String content) {
        return new Message(UUID.randomUUID().toString(), sender, recipient, group, content, LocalDateTime.now());
    }
}
