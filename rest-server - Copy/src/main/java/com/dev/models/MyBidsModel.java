package com.dev.models;

public class MyBidsModel {
    private int productId;
    private String productName;
    private int offer;
    private boolean openForSale;
    private boolean bidWinning;

    public MyBidsModel(int productId, String productName, int offer, boolean openForSale, boolean bidWinning) {
        this.productId = productId;
        this.productName = productName;
        this.offer = offer;
        this.openForSale = openForSale;
        this.bidWinning = bidWinning;
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
