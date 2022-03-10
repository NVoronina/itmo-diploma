package com.medical.medonline.exception;

public class NotFoundException extends RuntimeException {
    private Integer code;
    public NotFoundException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

}
