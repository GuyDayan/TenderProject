package com.dev.models;

import com.dev.objects.Product;
import com.dev.utils.Definitions;
import com.dev.utils.Utils;

public class ProductsForSaleModel {


    private int id;
    private String name;
    private String logoUrl;

    private String openingSaleDate;

    private int totalBids;

    private int myTotalBids;

    public ProductsForSaleModel(Product product , int totalBids , int myTotalBids) {
        this.id = product.getId();
        this.name = product.getName();
        this.logoUrl = product.getLogoUrl();
        this.openingSaleDate = Utils.simpleDateFormat.format(product.getOpeningSaleDate());
        this.totalBids = totalBids;
        this.myTotalBids = myTotalBids;
    }

    public int getTotalBids() {
        return totalBids;
    }

    public void setTotalBids(int totalBids) {
        this.totalBids = totalBids;
    }

    public int getMyTotalBids() {
        return myTotalBids;
    }

    public void setMyTotalBids(int myTotalBids) {
        this.myTotalBids = myTotalBids;
    }

    public String getOpeningSaleDate() {
        return openingSaleDate;
    }

    public void setOpeningSaleDate(String openingSaleDate) {
        this.openingSaleDate = openingSaleDate;
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

}
