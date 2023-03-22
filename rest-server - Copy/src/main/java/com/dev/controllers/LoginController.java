package com.dev.controllers;

import com.dev.models.UserStatsModel;
import com.dev.objects.User;
import com.dev.pojo.Stats;
import com.dev.responses.BasicResponse;
import com.dev.responses.LoginResponse;
import com.dev.responses.StatsResponse;
import com.dev.responses.UserStatsResponse;
import com.dev.utils.Definitions;
import com.dev.utils.Persist;
import com.dev.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.dev.utils.Errors.*;


@RestController
public class LoginController extends MainController{


    @Autowired
    private Utils utils;

    @Autowired
    private Persist persist;

    @Autowired
    private LiveUpdatesController liveUpdatesController;



    @RequestMapping(value = "sign-up")
    public BasicResponse signUp(String fullName, String email, String username, String password, String repeatPassword) {
        BasicResponse basicResponse = new BasicResponse();
        boolean success = false;
        Integer errorCode = null;
        fullName = fullName.trim();
        username = username.trim();
        password = password.trim();
        email = email.trim();
        repeatPassword = repeatPassword.trim();
        if (username != null) {
            if (utils.isValidUsername(username)) {
                if (password != null && repeatPassword != null) {
                    if (password.equals(repeatPassword)) {
                        if (utils.isStrongPassword(password) && utils.isStrongPassword(repeatPassword)) {
                            if (fullName != null) {
                                if (email != null) {
                                    User fromDb = persist.getUserByUsername(username);
                                    if (fromDb == null) {
                                        User toAdd = new User(username, utils.createHash(username, password), fullName, email, Definitions.USER_PARAM);
                                        persist.saveUser(toAdd);
                                        liveUpdatesController.sendStatsEvent();
                                        success = true;
                                    } else {
                                        errorCode = ERROR_USERNAME_ALREADY_EXISTS;
                                    }
                                } else {
                                    errorCode = ERROR_MISSING_EMAIL;
                                }
                            } else {
                                errorCode = ERROR_MISSING_FULLNAME;
                            }
                        } else {
                            errorCode = ERROR_WEAK_PASSWORD;
                        }
                    } else {
                        errorCode = ERROR_PASSWORD_DONT_MATCH;
                    }
                } else {
                    errorCode = ERROR_MISSING_PASSWORD;
                }
            } else {
                errorCode = ERROR_WEAK_USERNAME;
            }
        } else {
            errorCode = ERROR_MISSING_USERNAME;
        }
        basicResponse.setSuccess(success);
        basicResponse.setErrorCode(errorCode);
        return basicResponse;
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public BasicResponse login(String username, String password) {
        BasicResponse basicResponse = new BasicResponse(false,GENERAL_ERROR);
        if (username != null) {
            username = username.trim();
            if (password != null) {
                    String token = utils.createHash(username, password);
                    User user = persist.getUserByUsernameAndToken(username, token);
                    if (user != null) {
                        basicResponse = new LoginResponse(true, null, token, user.getId() ,user.getUserType());
                    } else {
                        basicResponse.setErrorCode(ERROR_WRONG_LOGIN_CREDS);
                    }
            }
            else {
                basicResponse.setErrorCode(ERROR_MISSING_PASSWORD);
            }
        } else {
            basicResponse.setErrorCode(ERROR_MISSING_USERNAME);
        }
        return basicResponse;
    }
    @RequestMapping(value = "get-user-stats", method = RequestMethod.GET)
    public BasicResponse getUserStats(String token , Integer userId){
        BasicResponse response = basicValidation(token, userId);
        if (response.isSuccess()){
            User user = persist.getUserById(userId);
            response = new UserStatsResponse(true,null,new UserStatsModel(user));
        }
        return response;
    }

    @RequestMapping(value = "get-stats", method = RequestMethod.GET)
    public BasicResponse getStats(){
        Stats stats = persist.getStats();
        BasicResponse response = new StatsResponse(true,null,stats);
        return response;
    }







}
