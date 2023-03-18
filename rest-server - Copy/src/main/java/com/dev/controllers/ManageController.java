package com.dev.controllers;


import com.dev.objects.Product;
import com.dev.objects.User;
import com.dev.responses.*;
import com.dev.utils.Errors;
import com.dev.utils.Persist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import static com.dev.utils.Errors.*;
import static com.dev.utils.Definitions.*;
import java.util.List;

@RestController
public class ManageController extends MainController{

    @Autowired
    private Persist persist;

    @Autowired
    private LiveUpdatesController liveUpdatesController;


    @RequestMapping (value = "get-manage-details", method = RequestMethod.GET)
    public BasicResponse getAllUsers (String token,Integer userId) {
        BasicResponse response = basicValidation(token,userId);
        if (response.isSuccess()){
            User user = persist.getUserByToken(token);
            if (user!=null){
                List<User> users = persist.getAllUsers();
                response= new ManageResponse(true, null, users , user.getCredit());
            }
        }
        return response;
    }


    @RequestMapping (value = "get-user-details", method = RequestMethod.GET)
    public BasicResponse getUserDetails (String token , Integer userId) {
        BasicResponse response = new BasicResponse(false, Errors.GENERAL_ERROR);
        User adminUser = persist.getUserByToken(token);
        if (adminUser!=null){
            if (adminUser.getUserType().equals(ADMIN_PARAM)){
                User user = persist.getUserById(userId);
                if (user!=null){
                    int totalAuctions = persist.getMyProductsForSale(userId).size();
                    response = new UserDetailsResponse(true,null,user,totalAuctions);
                }else {
                    response.setErrorCode(Errors.ERROR_MISSING_USERNAME);
                }
            }else {
                response.setErrorCode(PERMISSION_ERROR_CODE);
            }
            response.setErrorCode(NO_SUCH_USER);
        }
        return response;
    }

    @RequestMapping (value = "add-credit", method = RequestMethod.POST)
    public BasicResponse addCredit (String token , Integer userId , int creditToAdd) {
        BasicResponse response = new BasicResponse(false, Errors.GENERAL_ERROR);
        User adminUser = persist.getUserByToken(token);
        if (adminUser!=null){
            User user = persist.getUserById(userId);
            if (adminUser.getUserType().equals(ADMIN_PARAM)){
                if (user!=null){
                    if (creditToAdd % 1 == 0){
                        User currentUser = persist.getUserById(userId);
                        persist.updateUserCredit(userId,currentUser.getCredit() + creditToAdd);
                        int totalAuctions = persist.getMyProductsForSale(userId).size();
                        currentUser = persist.getUserById(userId);
                        response = new UserDetailsResponse(true,null,currentUser,totalAuctions);
                    }else {
                        response.setErrorCode(Errors.ERROR_MUST_BE_INTEGER);
                    }
                }else {
                    response.setErrorCode(Errors.ERROR_MISSING_USERNAME);
                }
            }else {
                response.setErrorCode(PERMISSION_ERROR_CODE);
            }

        }else {
            response.setErrorCode(NO_SUCH_USER);
        }
        return response;
    }


}
