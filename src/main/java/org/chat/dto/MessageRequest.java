package org.chat.dto;

import com.sun.istack.NotNull;

public record MessageRequest(String recipient, String group, boolean isAll, @NotNull String content) {}
