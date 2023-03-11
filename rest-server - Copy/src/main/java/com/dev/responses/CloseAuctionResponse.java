package com.dev.responses;

import com.dev.models.UserModel;
import com.dev.objects.User;

public class CloseAuctionResponse extends BasicResponse{

    private UserModel userModel;

    public CloseAuctionResponse(boolean success, Integer errorCode , User user) {
        super(success, errorCode);
        this.userModel = new UserModel(user);
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
}
