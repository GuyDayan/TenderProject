package com.dev.responses;

import com.dev.models.AllUsersModel;
import com.dev.objects.User;

import java.util.ArrayList;
import java.util.List;

public class ManageResponse extends BasicResponse {
    private List<AllUsersModel> users = new ArrayList<>();
    private int credit;



    public ManageResponse(boolean success, Integer errorCode, List<User> users , int credit) {
        super(success, errorCode);
        for (User user : users){
            this.users.add(new AllUsersModel(user));
        }
        this.credit = credit;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public List<AllUsersModel> getUsers() {
        return users;
    }

    public void setUsers(List<AllUsersModel> users) {
        this.users = users;
    }
}
