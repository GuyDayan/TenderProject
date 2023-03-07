package com.dev.objects;

public class ProductData {

    private String name;

    private Integer biggestBid;
    private boolean isTenderOpened;

    public ProductData(String name, Integer biggestBid, boolean isTenderOpened) {
        this.name = name;
        this.biggestBid = biggestBid;
        this.isTenderOpened = isTenderOpened;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBiggestBid() {
        return biggestBid;
    }

    public void setBiggestBid(Integer biggestBid) {
        this.biggestBid = biggestBid;
    }

    public boolean isTenderOpened() {
        return isTenderOpened;
    }

    public void setTenderOpened(boolean tenderOpened) {
        isTenderOpened = tenderOpened;
    }
}
