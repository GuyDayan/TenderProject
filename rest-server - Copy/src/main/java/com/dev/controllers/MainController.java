package com.dev.controllers;


import com.dev.objects.User;
import com.dev.responses.BasicResponse;
import com.dev.responses.UsernameResponse;
import com.dev.utils.Errors;
import com.dev.utils.Persist;
import com.dev.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.dev.utils.Errors.ERROR_NO_SUCH_TOKEN;

@RestController
public class MainController {

    @Autowired
    private Persist persist;
    @Autowired
    private Utils utils;



    public BasicResponse basicValidation(String token , Integer userId){
        BasicResponse response;
        if (userId!=null){
            if (token!=null){
                if (userHasPermissions(userId,token)){
                    response = new BasicResponse(true,null);
                }else {
                    response = new BasicResponse(false,Errors.PERMISSION_ERROR_CODE);
                }
            }else {
                response = new BasicResponse(false, Errors.ERROR_MISSING_TOKEN);
            }
        }else {
            response = new BasicResponse(false,Errors.ERROR_MISSING_USERNAME);
        }
        return response;
    }
//    @RequestMapping(value = "get-username", method = RequestMethod.GET)
//    public BasicResponse getUsername (String token) {
//        User user = persist.getUserByToken(token);
//        BasicResponse basicResponse = null;
//        if (user != null) {
//            basicResponse = new UsernameResponse(true, null, user.getUsername());
//        } else {
//            basicResponse = new BasicResponse(false, ERROR_NO_SUCH_TOKEN);
//        }
//        return basicResponse;
//    }


    public boolean userHasPermissions(int userId, String token){
        return persist.userHasPermissions(userId,token);
    }
}
