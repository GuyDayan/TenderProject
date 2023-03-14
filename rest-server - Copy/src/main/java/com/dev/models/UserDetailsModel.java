package com.dev.models;

import com.dev.objects.User;

public class UserDetailsModel {

    private String username;


    private int totalAuctions;

    private int credit;

    public UserDetailsModel(User user , int totalAuctions) {
        this.username = user.getUsername();
        this.totalAuctions = totalAuctions;
        this.credit = user.getCredit();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTotalAuctions() {
        return totalAuctions;
    }

    public void setTotalAuctions(int totalAuctions) {
        this.totalAuctions = totalAuctions;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }
}
