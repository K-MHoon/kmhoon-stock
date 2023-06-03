package com.kmhoon.licensingservice.model.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorMessage {

    private String message;
    private String code;
    private String detail;

    public ErrorMessage(String message, String code, String detail) {
        this.message = message;
        this.code = code;
        this.detail = detail;
    }

    public ErrorMessage(String message, String detail) {
        this(message, "", detail);
    }

    public ErrorMessage(String message) {
        this(message, "", "");
    }
}
