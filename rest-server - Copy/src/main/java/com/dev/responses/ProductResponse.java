package com.dev.responses;

import com.dev.models.ProductModel;
import com.dev.objects.Product;
import com.dev.objects.User;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

public class ProductResponse extends BasicResponse{


    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "logo_url")
    private String logoUrl;
    @Column
    private String description;

    @Column(name = "creation_date")
    private Date creationDate;
    @Column(name = "starting_price")
    private Integer startingPrice;
    @ManyToOne
    @JoinColumn(name = "seller_user_id")
    private User sellerUser;

    @Column(name = "is_open")
    private boolean isOpen;

    public ProductResponse(Product product){

    }
}
