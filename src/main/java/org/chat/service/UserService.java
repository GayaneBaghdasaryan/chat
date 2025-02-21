package org.chat.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.chat.dto.UserDto;
import org.chat.entity.User;

@ApplicationScoped
public class UserService {

    @Transactional
    public User register(UserDto userDto) {
        User user = new User();
        user.setPassword(userDto.getPassword());
        user.setUsername(userDto.getUsername());
        user.persist();
        return user;
    }

    @Transactional
    public boolean exists(String username) {
        return User.find("username", username).firstResult() != null;
    }
}
