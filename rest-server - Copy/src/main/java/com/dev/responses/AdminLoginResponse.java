package com.dev.responses;

public class AdminLoginResponse extends BasicResponse{

    private String token;

    public AdminLoginResponse(boolean success, Integer errorCode, String token) {
        super(success, errorCode);
        this.token = token;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
