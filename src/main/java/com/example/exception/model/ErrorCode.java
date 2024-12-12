package com.example.exception.model;

public enum ErrorCode {
    AUTHENTICATION_EXCEPTION(1),
    EXPIRED_AUTHORIZATION_EXCEPTION(2),
    SIGNATURE_AUTHORIZATION_EXCEPTION(3),
    AUTHORIZATION_EXCEPTION(4),
    REFRESH_TOKEN_EXCEPTION(5),
    METHOD_ARGUMENT_NOT_VALID_EXCEPTION(6),
    CUSTOM_TEST_EXCEPTION(7);

    private Integer code;

    ErrorCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }
}
