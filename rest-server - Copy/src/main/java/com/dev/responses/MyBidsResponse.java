package com.dev.responses;

import com.dev.models.MyBidsModel;
import com.dev.objects.Bid;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyBidsResponse extends BasicResponse{

    private List<MyBidsModel> myBids = new ArrayList<>();

    public MyBidsResponse(boolean success, Integer errorCode, Map<Bid, Boolean> myBids) {
        super(success, errorCode);
        for (Map.Entry<Bid,Boolean> entry : myBids.entrySet()){
            this.myBids.add(new MyBidsModel(entry.getKey() , entry.getValue()));
        }
    }

    public List<MyBidsModel> getMyBids() {
        return myBids;
    }

    public void setMyBids(List<MyBidsModel> myBids) {
        this.myBids = myBids;
    }
}
