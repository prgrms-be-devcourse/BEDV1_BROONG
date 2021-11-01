package com.prgrms.broong.exception;

public class ErrorResponse {

    private final String errorMessage;


    public ErrorResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public static ErrorResponse of(String errorMessage) {
        return new ErrorResponse(errorMessage);
    }

}
