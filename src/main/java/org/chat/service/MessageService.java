package org.chat.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.chat.dto.Message;
import org.chat.dto.MessageRequest;
import org.chat.entity.User;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import java.util.List;

import static org.chat.common.ErrorCode.GROUP_NOT_FOUND;
import static org.chat.common.ErrorCode.USER_NOT_FOUND;
import static org.chat.exception.BaseException.throwIf;

@ApplicationScoped
@Slf4j
public class MessageService {
    @Inject
    @Channel("chat-out")
    Emitter<Message> emitter;

    @Inject
    GroupService groupService;
    @Inject
    UserService userService;

    public void sendMessage(MessageRequest request, String sender) {
        if (request.isAll()) {
            List<User> users = User.listAll();
            for (User user : users) {
                if (!user.getUsername().equals(sender)) {
                    Message message = Message.create(sender, user.getUsername(), null, request.content());
                    log.info("Message sent to {}: {}", user.getUsername(), message);
                    emitter.send(message);
                }
            }
        } else {
            if (request.recipient() != null) {
                throwIf(!userService.exists(request.recipient()), USER_NOT_FOUND, request.recipient());
            }
            if (request.group() != null) {
                throwIf(!groupService.exists(request.group()), GROUP_NOT_FOUND, request.group());
            }
            Message message = Message.create(sender, request.recipient(), request.group(), request.content());
            log.info("Message sent:{}", message);
            emitter.send(message);
        }
    }
}
