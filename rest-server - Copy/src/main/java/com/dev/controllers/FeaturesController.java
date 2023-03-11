package com.dev.controllers;

import com.dev.models.MyProductsModel;
import com.dev.objects.*;
import com.dev.responses.BasicResponse;
import com.dev.responses.CloseAuctionResponse;
import com.dev.responses.MyProductsResponse;
import com.dev.responses.ProductForSaleResponse;
import com.dev.utils.Definitions;
import com.dev.utils.Errors;
import com.dev.utils.Persist;
import com.dev.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FeaturesController extends MainController{
    @Autowired
    private Persist persist;
    @Autowired
    private Utils utils;

    @RequestMapping(value = "get-my-products" , method = RequestMethod.GET)
    public BasicResponse getMyProducts(String token,Integer userId){
        BasicResponse response = basicValidation(token,userId);
        if (response.isSuccess()){
            List<Product> products = persist.getProductsBySellerUserId(userId);
            List<Bid> bids = persist.getBidsBySellerUserId(userId);
            List<MyProductsModel> productsList = utils.calculateBiggestBids(products,bids);
            response = new MyProductsResponse(true,null, productsList);
        }
        return response;
    }
    @RequestMapping(value = "place-bid" , method = RequestMethod.POST)
    public BasicResponse placeBid(String token,Integer userId,Integer productId ,int offer){
        BasicResponse response = basicValidation(token,userId);
        if (response.isSuccess()){
            if (productId!=null){
                Product product = persist.productIsExist(productId);
                if (product!=null){
                    if (product.getSellerUser().getId() != userId){
                        if (product.isOpenForSale()){
                            Integer maxOffer = persist.getBiggestBidOnProduct(productId);
                            User buyerUser = persist.getUserById(userId);
                            Bid bid = new Bid(buyerUser,product,offer);
                            if (maxOffer!=null){
                                if (offer > maxOffer){
                                    persist.saveBid(bid);
                                    response = new BasicResponse(true,null);

                                }else {
                                    response = new BasicResponse(false,Errors.ERROR_OFFER_LOW);
                                }
                            }else {
                                if (offer > product.getStartingPrice()){
                                   persist.saveBid(bid);
                                   response = new BasicResponse(true,null);
                                }else {
                                    response = new BasicResponse(false,Errors.ERROR_OFFER_LOW);
                                }
                            }
                        }else {
                            response = new BasicResponse(false,Errors.ERROR_PRODUCT_NOT_ON_SALE);

                        }
                    }else {
                        response = new BasicResponse(false,Errors.ERROR_BID_ON_YOUR_PRODUCT);
                    }
                }else {
                    response = new BasicResponse(false,Errors.ERROR_PRODUCT_DOESNT_EXIST);

                }
            }else {
                response = new BasicResponse(false,Errors.ERROR_MISSING_PRODUCT_ID);
            }
        }
        return response;
    }


    @RequestMapping(value = "get-product" , method = RequestMethod.GET)
    public BasicResponse getProduct(String token , Integer userId){
        BasicResponse response = basicValidation(token,userId);
        if (response.isSuccess()){

        }
        return response;
    }

    @RequestMapping(value = "get-products-for-sale" , method = RequestMethod.GET)
    public BasicResponse getProductsForSale(String token , Integer userId){
        BasicResponse response = basicValidation(token,userId);
        if (response.isSuccess()){
            List<Product> productsForSale = persist.getProductsForSale(userId);
            response = new ProductForSaleResponse(true,null, productsForSale);
        }
        return response;

    }


    @RequestMapping(value = "close-auction" ,method = RequestMethod.POST)
    public BasicResponse closeAuction(String token , Integer userId , Integer productId){
        BasicResponse response = basicValidation(token,userId);
        if (response.isSuccess()){
            if (productId != null){
                Product product = persist.productIsExist(productId);
                if (product!= null){
                    if (product.getSellerUser().getId() == userId){
                        List<Bid> bidsOnProductAsc = persist.getBidsByProductIdAsc(productId);
                        if (bidsOnProductAsc.size() >= Definitions.MIN_BIDS_FOR_CLOSE_AUCTION){
                            Product updatedProduct = persist.closeAuction(productId);
                            if (!updatedProduct.isOpenForSale()){
                                User winnerUser  = utils.checkForAuctionWinner(bidsOnProductAsc,product);
                                if (winnerUser != null){
                                    response = new CloseAuctionResponse(true,null,winnerUser);
                                }else {
                                    response = new CloseAuctionResponse(true,null, null);
                                }
                            }else {
                                response = new BasicResponse(false, Errors.GENERAL_ERROR);
                            }
                        }else {
                            response = new BasicResponse(false, Errors.PRODUCT_HASNT_ENOUGH_BIDS);
                        }
                    }else {
                        response = new BasicResponse(false,Errors.ERROR_USER_DOESNT_OWNER);
                    }

                }else {
                    response = new BasicResponse(false,Errors.ERROR_PRODUCT_DOESNT_EXIST);

                }
            }else {
                response = new BasicResponse(false,Errors.ERROR_MISSING_PRODUCT_ID);
            }
        }
        return response;
    }

    @RequestMapping(value = "add-new-product" , method = RequestMethod.POST)
    public BasicResponse addNewProduct(String token, Integer userId , String name ,String description , String logoUrl , Integer startingPrice){
        BasicResponse response;
        name = name.trim();
        description = description.trim();
        logoUrl = logoUrl.trim();
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
