package com.medical.medonline.exception;

public class ValidationException extends RuntimeException {
    private Integer code;
    public ValidationException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
