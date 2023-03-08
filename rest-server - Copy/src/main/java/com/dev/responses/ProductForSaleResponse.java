package com.dev.responses;

import com.dev.models.MessageModel;
import com.dev.models.ProductForSaleModel;
import com.dev.objects.Message;
import com.dev.objects.Product;
import com.dev.objects.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductForSaleResponse extends BasicResponse{

    private List<ProductForSaleModel> products;


    public ProductForSaleResponse(boolean success, Integer errorCode, List<Product> products) {
        super(success, errorCode);
        for (Product product : products){
            this.products.add(new ProductForSaleModel(product));
        }
    }


}
