package com.dialectric.payload.response;

public class RegisterResponse {
    private String result;

    public RegisterResponse(String result) {
        this.result = result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}
