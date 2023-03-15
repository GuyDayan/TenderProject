package com.dev.controllers;


import com.dev.objects.AdminUser;
import com.dev.objects.Product;
import com.dev.objects.User;
import com.dev.responses.*;
import com.dev.utils.Errors;
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

    @RequestMapping (value = "get-manage-details", method = RequestMethod.GET)
    public BasicResponse getAllUsers (String uniqueToken) {
        BasicResponse response = new BasicResponse(false, Errors.GENERAL_ERROR);
        AdminUser adminUser = persist.validateAdminToken(uniqueToken);
        if (adminUser!=null){
            List<User> users = persist.getAllUsers();
            response= new ManageResponse(true, null, users , adminUser.getCredit());
        }
        return response;
    }


    @RequestMapping (value = "get-user-details", method = RequestMethod.GET)
    public BasicResponse getUserDetails (String uniqueToken , Integer userId) {
        BasicResponse response = new BasicResponse(false, Errors.GENERAL_ERROR);
        AdminUser adminUser = persist.validateAdminToken(uniqueToken);
        if (adminUser!=null){
            User user = persist.getUserById(userId);
            if (user!=null){
                int totalAuctions = persist.getMyProductsForSale(userId).size();
                response = new UserDetailsResponse(true,null,user,totalAuctions);
            }else {
                response.setErrorCode(Errors.ERROR_MISSING_USERNAME);
            }
        }
        return response;
    }

    @RequestMapping (value = "add-credit", method = RequestMethod.POST)
    public BasicResponse addCredit (String uniqueToken , Integer userId , int creditToAdd) {
        BasicResponse response = new BasicResponse(false, Errors.GENERAL_ERROR);
        AdminUser adminUser = persist.validateAdminToken(uniqueToken);
        if (adminUser!=null){
            User user = persist.getUserById(userId);
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
        }
        return response;
    }

    @RequestMapping (value = "get-all-open-auctions", method = RequestMethod.GET)
    public BasicResponse getAllAuctions (String uniqueToken) {
        BasicResponse response = new BasicResponse(false, Errors.GENERAL_ERROR);
        AdminUser adminUser = persist.validateAdminToken(uniqueToken);
        if (adminUser!=null){
            List<Product> products = persist.getProductsForSale(0);
            response = new AllProductsAdminResponse(true,null,products);
        }
        return response;
    }
}
