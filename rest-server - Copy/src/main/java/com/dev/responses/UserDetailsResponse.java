package com.dev.responses;

import com.dev.models.AllUsersModel;
import com.dev.models.UserDetailsModel;
import com.dev.objects.User;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsResponse extends BasicResponse{

    private UserDetailsModel user;



    public UserDetailsResponse(boolean success, Integer errorCode, User user, int totalBids) {
        super(success, errorCode);
        this.user = new UserDetailsModel(user,totalBids);
    }

    public UserDetailsModel getUserDetailsModel() {
        return user;
    }

    public void setUserDetailsModel(UserDetailsModel user) {
        this.user = user;
    }
}
