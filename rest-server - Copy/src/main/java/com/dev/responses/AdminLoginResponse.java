package com.dev.responses;

public class AdminLoginResponse extends BasicResponse{

    private String uniqueToken;

    public AdminLoginResponse(boolean success, Integer errorCode, String uniqueToken) {
        super(success, errorCode);
        this.uniqueToken = uniqueToken;
    }


    public String getUniqueToken() {
        return uniqueToken;
    }

    public void setUniqueToken(String uniqueToken) {
        this.uniqueToken = uniqueToken;
    }
}
