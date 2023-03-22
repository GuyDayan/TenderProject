package com.dev.pojo;

public class Stats {

    private int totalUsers;
    private int totalAuctions;
    private int totalBids;

    public Stats(int totalUsers, int totalAuctions, int totalBids) {
        this.totalUsers = totalUsers;
        this.totalAuctions = totalAuctions;
        this.totalBids = totalBids;
    }

    public int getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(int totalUsers) {
        this.totalUsers = totalUsers;
    }

    public int getTotalAuctions() {
        return totalAuctions;
    }

    public void setTotalAuctions(int totalAuctions) {
        this.totalAuctions = totalAuctions;
    }

    public int getTotalBids() {
        return totalBids;
    }

    public void setTotalBids(int totalBids) {
        this.totalBids = totalBids;
    }
}
