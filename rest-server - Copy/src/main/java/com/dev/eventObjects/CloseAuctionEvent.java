package com.dev.eventObjects;

public class CloseAuctionEvent extends BasicEvent{

    private String sellerUsername;


    public CloseAuctionEvent(String eventType, String sellerUsername) {
        super(eventType);
        this.sellerUsername = sellerUsername;
    }

    public String getSellerUsername() {
        return sellerUsername;
    }

    public void setSellerUsername(String sellerUsername) {
        this.sellerUsername = sellerUsername;
    }
}
