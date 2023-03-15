package com.dev.models;

import com.dev.objects.Bid;
import com.dev.objects.Product;
import com.dev.objects.User;
import com.dev.utils.Utils;

import javax.persistence.*;
import java.util.Date;
import static com.dev.utils.Definitions.simpleDateFormat;


public class MyBidModel {

    private int id;

    private int productId;
    private Integer offer;
    private String bidDate;

    public MyBidModel(Bid bid) {
        this.id = bid.getId();
        this.productId = bid.getProduct().getId();
        this.offer = bid.getOffer();
        this.bidDate = Utils.simpleDateFormat.format(bid.getBidDate());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Integer getOffer() {
        return offer;
    }

    public void setOffer(Integer offer) {
        this.offer = offer;
    }

    public String getBidDate() {
        return bidDate;
    }

    public void setBidDate(String bidDate) {
        this.bidDate = bidDate;
    }
}
