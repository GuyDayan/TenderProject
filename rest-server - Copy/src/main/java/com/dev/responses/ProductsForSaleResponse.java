package com.dev.responses;

import com.dev.models.ProductsForSaleModel;
import com.dev.objects.Product;
import com.dev.pojo.TotalBidsCounter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductsForSaleResponse extends BasicResponse{

    private List<ProductsForSaleModel> products = new ArrayList<>();

    public ProductsForSaleResponse(boolean success, Integer errorCode, Map<Product, TotalBidsCounter> productsBidsMap) {
        super(success, errorCode);
        for (Map.Entry<Product , TotalBidsCounter> entry : productsBidsMap.entrySet()){
            this.products.add(new ProductsForSaleModel(entry.getKey() , entry.getValue().getAllUsersTotalBids(), entry.getValue().getUserTotalBids()));
        }
    }

    public List<ProductsForSaleModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsForSaleModel> products) {
        this.products = products;
    }
}
