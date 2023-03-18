package com.dev.eventObjects;

public class PlaceBidEvent extends BasicEvent{


    private String bidderUsername;

    public PlaceBidEvent(String eventType, String bidderUsername) {
        super(eventType);
        this.bidderUsername = bidderUsername;
    }

    public String getBidderUsername() {
        return bidderUsername;
    }

    public void setBidderUsername(String bidderUsername) {
        this.bidderUsername = bidderUsername;
    }
}
