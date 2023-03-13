package com.dev.models;

import com.dev.objects.User;

public class UserDetailsModel {

    private String username;

    private int totalBids;

    private int credit;

    public UserDetailsModel(User user , int totalBids) {
        this.username = user.getUsername();
        this.totalBids = totalBids;
        this.credit = user.getCredit();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTotalBids() {
        return totalBids;
    }

    public void setTotalBids(int totalBids) {
        this.totalBids = totalBids;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }
}
