package com.dev.responses;

import com.dev.models.ProductDetailsModel;
import com.dev.objects.Bid;
import com.dev.objects.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsResponse extends BasicResponse{

    private ProductDetailsModel product;



    public ProductDetailsResponse(boolean success, Integer errorCode, Product product, List<Bid> myBids, int totalBids) {
        super(success,errorCode);
        this.product = new ProductDetailsModel(product,myBids,totalBids);

    }

    public ProductDetailsModel getProduct() {
        return product;
    }

    public void setProduct(ProductDetailsModel product) {
        this.product = product;
    }
}
