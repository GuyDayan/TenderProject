package com.dev.models;

import com.dev.objects.Product;


public class MyProductsModel {

    private int id;
    private String name;

//    private String logoUrl;
//    private String description;
//    private String creationDate;
//    private Integer startingPrice;
//    private String username;
//    private int userId;

    private boolean isOpenForSale;
    private int biggestBid;


//    public ProductModel(Product product){
//        this.id = product.getId();
//        this.name = product.getName();
//        this.description = product.getDescription();
//        this.creationDate = simpleDateFormat.format(product.getCreationDate());
//        this.startingPrice = product.getStartingPrice();
//        this.username = product.getSellerUser().getUsername();
//        this.userId = product.getSellerUser().getId();
//    }

    public MyProductsModel(Product product , int biggestBid){
        this.id = product.getId();
        this.name = product.getName();
        this.biggestBid = biggestBid;
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
