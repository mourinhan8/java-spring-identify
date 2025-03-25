package com.mourinhan.identify_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "Uncategorized error"),
    INVALID_KEY(HttpStatus.BAD_REQUEST, "Invalid message"),
    USER_EXISTED(HttpStatus.BAD_REQUEST, "User existed"),
    USERNAME_INVALID(HttpStatus.BAD_REQUEST, "Username must be at least {min} character"),
    PASSWORD_INVALID(HttpStatus.BAD_REQUEST, "Password must be at least {min} character"),
    USER_NOT_EXISTED(HttpStatus.NOT_FOUND, "User not existed"),
    UNAUTHENTICATED(HttpStatus.UNAUTHORIZED, "Unauthenticated"),
    UNAUTHORIZED(HttpStatus.FORBIDDEN, "You do not have permission"),
    INVALID_DOB(HttpStatus.BAD_REQUEST, "Your age must be at least {min}"),
    ;

    ErrorCode(HttpStatus code, String message) {
        this.code = code;
        this.message = message;
    }
    private final HttpStatus code;
    private final String message;

}
