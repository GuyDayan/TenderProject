package com.dev.responses;

import com.dev.models.MyBidsModel;
import com.dev.objects.Bid;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyBidsResponse extends BasicResponse{

    private List<MyBidsModel> myBids = new ArrayList<>();



    public MyBidsResponse(boolean success, Integer errorCode, List<Bid> myBids) {
        super(success, errorCode);
        for (Bid bid : myBids){
            this.myBids.add(new MyBidsModel(bid));
        }
    }

    public List<MyBidsModel> getMyBids() {
        return myBids;
    }

    public void setMyBids(List<MyBidsModel> myBids) {
        this.myBids = myBids;
    }
}
