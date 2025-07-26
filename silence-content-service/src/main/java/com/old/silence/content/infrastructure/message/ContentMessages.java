package com.old.silence.content.infrastructure.message;

import org.springframework.http.HttpStatus;
import com.old.silence.core.context.ErrorCodedEnumMessageSourceResolvable;

/**
 * @author MurrayZhang
 * @Description
 */
public enum ContentMessages implements ErrorCodedEnumMessageSourceResolvable {

    CHILD_TAGS_CONTAINS_PARENT_TAG_TO_UPDATE(HttpStatus.BAD_REQUEST, 51),
    AT_LEAST_ONE_VALID_PROVISION_VERSION_TO_BE_KEPT(HttpStatus.BAD_REQUEST, 52),
    EXISTS_REVIEWING_TASK(HttpStatus.BAD_REQUEST, 53),
    REVIEWING_EOA_TASK_NOT_EXIST(HttpStatus.BAD_REQUEST, 54),
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
