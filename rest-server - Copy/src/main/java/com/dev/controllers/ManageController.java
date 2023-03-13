package com.dev.controllers;


import com.dev.objects.User;
import com.dev.responses.AllUsersResponse;
import com.dev.responses.BasicResponse;
import com.dev.responses.UserDetailsResponse;
import com.dev.utils.Persist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ManageController extends MainController{

    @Autowired
    private Persist persist;

    @RequestMapping (value = "get-all-users", method = RequestMethod.GET)
    public BasicResponse getAllUsers () {
        List<User> users = persist.getAllUsers();
        return new AllUsersResponse(true, null, users);
    }

    @RequestMapping (value = "get-user-details", method = RequestMethod.GET)
    public BasicResponse getUserDetails (Integer userId) {
        User user = persist.getUserById(userId);
        int totalBids = persist.getBidsByBuyerUserId(userId).size();
        return new UserDetailsResponse(true,null,user,totalBids);
    }
}
