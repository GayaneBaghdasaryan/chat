package org.chat.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.chat.common.ErrorCode;
import org.chat.dto.GroupDto;
import org.chat.entity.Group;
import org.chat.entity.User;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.chat.common.ErrorCode.USER_NOT_FOUND;
import static org.chat.exception.BaseException.throwIf;

@ApplicationScoped
@Slf4j
public class GroupService {

    @Transactional
    public Group create(GroupDto groupDto, Long adminId) {
        List<String> usernames = groupDto.getUsername();

        if (usernames == null || usernames.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Usernames cannot be null");
        }

        List<User> users = User.list("username in ?1", usernames);
        throwIf(users.isEmpty(), USER_NOT_FOUND, usernames.toString());

        User admin = User.findById(adminId);
        users.add(admin);

        Group group = new Group();
        group.setAdmin(admin.getUsername());
        group.setGroupName(groupDto.getGroupName());
        group.setUsers(new HashSet<>(users));
        group.persist();
        log.info("Message group created successfully!{}", group);
        return group;
    }

    @Transactional
    public boolean exists(String groupName) {
        return Group.find("groupName", groupName).firstResult() != null;
    }

    @Transactional
    public GroupDto search(String groupName, Long userId) {
        log.info("Search group :{}", groupName);

        Group group = Group.find(
                "groupName",
                groupName
        ).firstResult();

        boolean isMember = group.getUsers().stream()
                .anyMatch(user -> user.id.equals(userId));
        throwIf(group == null || !group.isPersistent() || !isMember, ErrorCode.INVALID_PARAM, groupName);

        GroupDto dto = new GroupDto();
        dto.setGroupName(group.getGroupName());
        dto.setUsername(
                group.getUsers().stream()
                        .map(User::getUsername)
                        .collect(Collectors.toList())
        );

        return dto;
    }


}
