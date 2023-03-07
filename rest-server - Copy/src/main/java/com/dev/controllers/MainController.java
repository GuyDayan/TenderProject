package com.dev.controllers;


import com.dev.responses.BasicResponse;
import com.dev.utils.Errors;
import com.dev.utils.Persist;
import com.dev.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

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
    public boolean userHasPermissions(int userId, String token){
        return persist.userHasPermissions(userId,token);
    }
}
