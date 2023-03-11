package com.dev.models;

import com.dev.objects.User;

public class UserModel {

    private Integer winnerUserId;
    private String username;

    public UserModel(User user){
        this.winnerUserId = user.getId();
        this.username = user.getUsername();

    }

    public Integer getWinnerUserId() {
        return winnerUserId;
    }

    public void setWinnerUserId(Integer winnerUserId) {
        this.winnerUserId = winnerUserId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
