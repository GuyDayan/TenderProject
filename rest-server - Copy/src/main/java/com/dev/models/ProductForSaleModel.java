package com.dev.models;

import com.dev.objects.Product;

import static com.dev.utils.Definitions.simpleDateFormat;

public class ProductForSaleModel {
    private int id;
    private String name;

    private String logoUrl;
    private String description;
    private String creationDate;
    private Integer startingPrice;

    private boolean isOpenForSale;

    private int biggestBid;


    public ProductForSaleModel(Product product){
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.creationDate = simpleDateFormat.format(product.getCreationDate());
        this.startingPrice = product.getStartingPrice();
        this.logoUrl = product.getLogoUrl();
        this.isOpenForSale = product.isOpenForSale();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(Integer startingPrice) {
        this.startingPrice = startingPrice;
    }

    public boolean isOpenForSale() {
        return isOpenForSale;
    }

    public void setOpenForSale(boolean openForSale) {
        isOpenForSale = openForSale;
    }

    public int getBiggestBid() {
        return biggestBid;
    }

    public void setBiggestBid(int biggestBid) {
        this.biggestBid = biggestBid;
    }
}
