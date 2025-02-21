package org.chat.exception;

import lombok.NoArgsConstructor;
import org.chat.common.ErrorCode;
import java.io.Serializable;
@NoArgsConstructor
public class BaseException extends
        RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;
    ErrorCode errorCode;
    String identifier;

    public BaseException(ErrorCode errorCode, String identifier){
        super();
        this.errorCode = errorCode;
        this.identifier = identifier;
    }
    public static void throwIf(boolean statement, ErrorCode code, String identifier) throws BaseException {
        if (statement) {
            throw new BaseException(code, identifier);
        }
    }

}