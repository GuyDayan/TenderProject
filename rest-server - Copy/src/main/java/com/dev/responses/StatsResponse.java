package com.dev.responses;

import com.dev.pojo.Stats;

public class StatsResponse extends BasicResponse{

    private Stats stats;



    public StatsResponse(boolean success, Integer errorCode, Stats stats) {
        super(success, errorCode);
        this.stats = stats;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }
}
