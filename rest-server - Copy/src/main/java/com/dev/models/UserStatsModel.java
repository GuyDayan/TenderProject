package com.dev.models;

import com.dev.objects.User;

public class UserStatsModel {

    private Integer credit;
    private String fullName;
    private String username;


    public UserStatsModel(User user) {
        this.credit = user.getCredit();
        this.fullName = user.getFullName();
        this.username = user.getUsername();
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
