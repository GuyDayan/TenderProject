package com.dev.responses;

import com.dev.models.AllUsersModel;
import com.dev.objects.User;

import java.util.ArrayList;
import java.util.List;

public class AllUsersResponse extends BasicResponse {
    private List<AllUsersModel> users = new ArrayList<>();



    public AllUsersResponse(boolean success, Integer errorCode, List<User> users) {
        super(success, errorCode);
        for (User user : users){
            this.users.add(new AllUsersModel(user));
        }
    }

    public List<AllUsersModel> getUsers() {
        return users;
    }

    public void setUsers(List<AllUsersModel> users) {
        this.users = users;
    }
}
