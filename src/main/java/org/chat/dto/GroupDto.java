package org.chat.dto;

import io.smallrye.common.constraint.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class GroupDto {
    @NotNull
    private String groupName;
    @NotNull
    private List<String> username = new ArrayList<>();
}
