package com.dev.models;

import com.dev.objects.Product;

public class ProductsForSaleModel {


    private int id;
    private String name;
    private String logoUrl;
    private boolean isOpenForSale;

    public ProductsForSaleModel(Product product) {
        this.id = product.getId();
        this.name = product.getName();
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

    public boolean isOpenForSale() {
        return isOpenForSale;
    }

    public void setOpenForSale(boolean openForSale) {
        isOpenForSale = openForSale;
    }
}
