package com.dev.models;

import com.dev.objects.Bid;

public class MyBidsModel {
    private int productId;
    private String productName;
    private int offer;
    private boolean openForSale;
    private boolean bidWinning;

    public MyBidsModel(Bid bid , Boolean isWinning) {
        this.productId = bid.getProduct().getId();
        this.productName = bid.getProduct().getName();
        this.offer = bid.getOffer();
        this.openForSale = bid.getProduct().isOpenForSale();
        this.bidWinning = isWinning;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getOffer() {
        return offer;
    }

    public void setOffer(int offer) {
        this.offer = offer;
    }

    public boolean isOpenForSale() {
        return openForSale;
    }

    public void setOpenForSale(boolean openForSale) {
        this.openForSale = openForSale;
    }

    public boolean isBidWinning() {
        return bidWinning;
    }

    public void setBidWinning(boolean bidWinning) {
        this.bidWinning = bidWinning;
    }
}
