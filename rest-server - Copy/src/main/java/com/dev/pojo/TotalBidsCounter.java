package com.dev.pojo;

public class TotalBidsCounter {

    private int allUsersTotalBids;
    private int userTotalBids;

    public TotalBidsCounter(int allUsersTotalBids, int userTotalBids) {
        this.allUsersTotalBids = allUsersTotalBids;
        this.userTotalBids = userTotalBids;
    }

    public int getAllUsersTotalBids() {
        return allUsersTotalBids;
    }

    public void setAllUsersTotalBids(int allUsersTotalBids) {
        this.allUsersTotalBids = allUsersTotalBids;
    }

    public int getUserTotalBids() {
        return userTotalBids;
    }

    public void setUserTotalBids(int userTotalBids) {
        this.userTotalBids = userTotalBids;
    }
}
