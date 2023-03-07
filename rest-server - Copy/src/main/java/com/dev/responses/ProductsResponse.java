package com.dev.responses;

import com.dev.models.ProductModel;
import com.dev.objects.Product;
import com.dev.objects.ProductData;

import java.util.List;
import java.util.stream.Collectors;

public class ProductsResponse extends BasicResponse{

    private List<ProductModel> productsList;


    public ProductsResponse(boolean success, Integer errorCode, List<ProductModel> productsList) {
        super(success, errorCode);
        this.productsList = productsList;
    }
    //    private List<ProductModel> productsList;
//
//    public ProductsResponse(List<Product> products) {
//        this.productsList = products.stream().map(product -> new ProductModel(product)).collect(Collectors.toList());
//
//    }
//
//    public ProductsResponse(boolean success, Integer errorCode, List<Product> products) {
//        super(success, errorCode);
//        this.productsList = products.stream().map(product -> new ProductModel(product)).collect(Collectors.toList());;
//    }


    public List<ProductModel> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<ProductModel> productsList) {
        this.productsList = productsList;
    }
}
