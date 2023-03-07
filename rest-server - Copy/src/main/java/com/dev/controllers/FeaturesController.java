package com.dev.controllers;

import com.dev.models.ProductModel;
import com.dev.objects.*;
import com.dev.responses.BasicResponse;
import com.dev.responses.ProductsResponse;
import com.dev.utils.Errors;
import com.dev.utils.Persist;
import com.dev.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FeaturesController extends MainController{
    @Autowired
    private Persist persist;
    @Autowired
    private Utils utils;

    @RequestMapping(value = "get-my-products" , method = RequestMethod.GET)
    public BasicResponse getProducts(String token,Integer userId){
        BasicResponse response = basicValidation(token,userId);;
        if (response.isSuccess()){
            List<Product> products = persist.getProductsBySellerUserId(userId);
            List<Bid> bids = persist.getBidsBySellerUserId(userId);
            List<ProductModel> productsList = utils.calculateBiggestBids(products,bids);
            response = new ProductsResponse(true,null, productsList);
        }
        return response;
    }




    @RequestMapping(value = "add-new-product" , method = RequestMethod.POST)
    public BasicResponse addNewProduct(String token, Integer userId , String name ,String description , String logoUrl , Integer startingPrice){
        BasicResponse response;
        if (userId != null){
            if (token != null){
                if (userHasPermissions(userId,token)){
                    if (name !=null){
                        if (description!=null){
                            if (logoUrl!=null){
                                // add check to valid logo url
                                if (startingPrice!=null){
                                    if(startingPrice % 1 == 0){
                                        User user = persist.getUserByToken(token);
                                        Product product = new Product(name , logoUrl , description, startingPrice , user);
                                        persist.saveProduct(product);
                                        response = new BasicResponse(true,null);
                                    }else {
                                        response = new BasicResponse(false, Errors.PRODUCT_STARTING_PRICE_MUST_BE_INTEGER);

                                    }
                                }else {
                                    response = new BasicResponse(false, Errors.PRODUCT_STARTING_PRICE_REQUIRED);
                                }

                            }else {
                                response = new BasicResponse(false, Errors.PRODUCT_LOGO_URL_REQUIRED);
                            }
                        }else {
                            response = new BasicResponse(false, Errors.PRODUCT_DESCRIPTION_REQUIRED);
                        }
                    }else {
                        response = new BasicResponse(false, Errors.PRODUCT_NAME_REQUIRED);
                    }

                }else {
                    response= new BasicResponse(false, Errors.PERMISSION_ERROR_CODE);
                }
            }else {
                response = new BasicResponse(false,Errors.ERROR_MISSING_PASSWORD);
            }
        }else {
            response = new BasicResponse(false,Errors.ERROR_MISSING_USERNAME);
        }
        return response;
    }
}
