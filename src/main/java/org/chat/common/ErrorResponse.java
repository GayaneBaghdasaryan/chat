package org.chat.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
    private String messageDetails;
    private Boolean status;
}
