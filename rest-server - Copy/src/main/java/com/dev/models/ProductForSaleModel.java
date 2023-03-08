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
        this.isOpenForSale = product.isOpenForSale();
    }
}
