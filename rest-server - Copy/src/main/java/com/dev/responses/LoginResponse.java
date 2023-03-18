package com.dev.responses;

public class LoginResponse extends BasicResponse{
    private String token;
    private Integer userId;
    private String userType;





    public LoginResponse(String token, Integer userId) {
        this.token = token;
        this.userId = userId;
    }

    public LoginResponse(boolean success, Integer errorCode, String token, Integer userId , String userType) {
        super(success, errorCode);
        this.token = token;
        this.userId = userId;
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userTpe) {
        this.userType = userTpe;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
