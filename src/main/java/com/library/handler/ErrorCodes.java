package com.library.handler;

import org.springframework.http.HttpStatus;

import lombok.Getter;

public enum ErrorCodes {

    NO_CODE(0, "No code", HttpStatus.NOT_IMPLEMENTED),
    INCORRECT_PASSWORD(1, "Incorrect password", HttpStatus.BAD_REQUEST),
    BAD_CREDENTIALS(2, "email or password is incorrect", HttpStatus.FORBIDDEN),
    ACCOUNT_DISABLED(3,"User account is disabled", HttpStatus.FORBIDDEN),
    ACCOUNT_USED(4,"This account is already used", HttpStatus.BAD_REQUEST)
    
    ;

    @Getter
    private final int code;
    @Getter
    private final String description;
    @Getter
    private final HttpStatus httpStatus;

    private ErrorCodes(int code, String description, HttpStatus httpStatus) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }

}
