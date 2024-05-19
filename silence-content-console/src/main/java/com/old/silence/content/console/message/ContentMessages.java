package com.old.silence.content.console.message;

import org.springframework.http.HttpStatus;
import com.old.silence.core.context.ErrorCodedEnumMessageSourceResolvable;

public enum ContentMessages implements ErrorCodedEnumMessageSourceResolvable {


    PARAM_ERROR(HttpStatus.BAD_REQUEST, 30001),

    DATA_NOT_EXIST(HttpStatus.BAD_REQUEST, 30002),

    ALREADY_EXIST(HttpStatus.BAD_REQUEST, 30003),

    REPEAT_SUBMIT(HttpStatus.BAD_REQUEST, 30004),

    NO_PERMISSION(HttpStatus.BAD_REQUEST, 30005),

    DEVELOPING(HttpStatus.BAD_REQUEST, 30006),

    LOGIN_STATE_INVALID(HttpStatus.BAD_REQUEST, 30007),

    USER_STATUS_ERROR(HttpStatus.BAD_REQUEST, 30008),

    FORM_REPEAT_SUBMIT(HttpStatus.BAD_REQUEST, 30009),

    LOGIN_FAIL_LOCK(HttpStatus.BAD_REQUEST, 30010),
    LOGIN_FAIL_WILL_LOCK(HttpStatus.BAD_REQUEST, 30011),

    LOGIN_ACTIVE_TIMEOUT(HttpStatus.BAD_REQUEST, 30012),

    ;

    private final int httpStatusCode;

    private final int errorCode;

    ContentMessages(HttpStatus httpStatus, int errorCode) {
        this.httpStatusCode = httpStatus.value();
        this.errorCode = errorCode;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
