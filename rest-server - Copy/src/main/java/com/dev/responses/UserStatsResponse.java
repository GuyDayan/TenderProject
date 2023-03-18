package com.dev.responses;

import com.dev.models.UserStatsModel;

public class UserStatsResponse extends BasicResponse{

    private UserStatsModel userStats;

    public UserStatsResponse(boolean success, Integer errorCode, UserStatsModel userStats) {
        super(success, errorCode);
        this.userStats = userStats;
    }

    public UserStatsModel getUserStats() {
        return userStats;
    }

    public void setUserStats(UserStatsModel userStats) {
        this.userStats = userStats;
    }
}
