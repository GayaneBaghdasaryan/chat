package org.chat.service;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.chat.dto.Message;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@ApplicationScoped
@Slf4j
public class MessageHistoryService {

    private final List<Message> history = new CopyOnWriteArrayList<>();

    @Incoming("chat-in")
    public void consume(Message message) {
        log.info("Received message:{}", message);
        history.add(message);
    }

    public List<Message> getHistoryBySender(String sender) {
        log.info("History of messages for sender:{}", sender);
        return history.stream()
                .filter(m -> m.sender().equals(sender))
                .collect(Collectors.toList());
    }

    public List<Message> getHistoryByRecipient(String recipient) {
        log.info("History of messages for recipient:{}", recipient);
        return history.stream()
                .filter(m -> m.recipient().equals(recipient))
                .collect(Collectors.toList());
    }

}
