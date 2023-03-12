package com.dev.responses;

import com.dev.models.ProductsForSaleModel;
import com.dev.objects.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductsForSaleResponse extends BasicResponse{

    private List<ProductsForSaleModel> products = new ArrayList<>();

    public ProductsForSaleResponse(boolean success, Integer errorCode, List<Product> products) {
        super(success, errorCode);
        for (Product product: products){
            this.products.add(new ProductsForSaleModel(product));
        }
    }

    public List<ProductsForSaleModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsForSaleModel> products) {
        this.products = products;
    }
}
