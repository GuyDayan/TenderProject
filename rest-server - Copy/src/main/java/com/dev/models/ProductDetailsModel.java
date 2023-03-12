package com.dev.models;

import com.dev.objects.Bid;
import com.dev.objects.Product;

import java.util.ArrayList;
import java.util.List;

import static com.dev.utils.Definitions.simpleDateFormat;

public class ProductDetailsModel {
    private int id;
    private String name;
    private String logoUrl;
    private String openingSaleDate;
    private String description;
    private String sellerUsername;
    private boolean isOpenForSale;
    private int totalBids;

    private List<MyBidModel> myBids = new ArrayList<>();


    public ProductDetailsModel(Product product , List<Bid> bids , int totalBids){
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.sellerUsername = product.getSellerUser().getUsername();
        this.openingSaleDate = simpleDateFormat.format(product.getOpeningSaleDate());
        this.logoUrl = product.getLogoUrl();
        this.isOpenForSale = product.isOpenForSale();
        this.totalBids = totalBids;
        for (Bid bid : bids){
            this.myBids.add(new MyBidModel(bid));
        }
    }


    public int getTotalBids() {
        return totalBids;
    }

    public void setTotalBids(int totalBids) {
        this.totalBids = totalBids;
    }

    public List<MyBidModel> getMyBids() {
        return myBids;
    }

    public void setMyBids(List<MyBidModel> myBids) {
        this.myBids = myBids;
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

    public String getOpeningSaleDate() {
        return openingSaleDate;
    }

    public void setOpeningSaleDate(String openingSaleDate) {
        this.openingSaleDate = openingSaleDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSellerUsername() {
        return sellerUsername;
    }

    public void setSellerUsername(String sellerUsername) {
        this.sellerUsername = sellerUsername;
    }

    public boolean isOpenForSale() {
        return isOpenForSale;
    }

    public void setOpenForSale(boolean openForSale) {
        isOpenForSale = openForSale;
    }


}
