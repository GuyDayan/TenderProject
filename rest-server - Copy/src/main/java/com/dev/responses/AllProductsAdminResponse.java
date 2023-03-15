package com.dev.responses;

import com.dev.models.ProductsForSaleModel;
import com.dev.objects.Product;

import java.util.ArrayList;
import java.util.List;

public class AllProductsAdminResponse extends BasicResponse{

    private List<Product> products;

    public AllProductsAdminResponse(boolean success, Integer errorCode, List<Product> products) {
        super(success, errorCode);
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
