package com.dev.responses;

import com.dev.models.MyProductsModel;

import java.util.List;

public class MyProductsResponse extends BasicResponse{

    private List<MyProductsModel> productsList;


    public MyProductsResponse(boolean success, Integer errorCode, List<MyProductsModel> productsList) {
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


    public List<MyProductsModel> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<MyProductsModel> productsList) {
        this.productsList = productsList;
    }
}
