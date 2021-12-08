package com.afs.restapi.advice;

public class ErrorResponse {
    private int code;
    private String errorMsg;

    public ErrorResponse(int code, String errorMsg) {
        this.code = code;
        this.errorMsg = errorMsg;
    }

    public int getCode() {
        return code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

}
