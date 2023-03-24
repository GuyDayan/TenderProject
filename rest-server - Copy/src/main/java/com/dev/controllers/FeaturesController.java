package com.dev.controllers;

import com.dev.models.MyBidsModel;
import com.dev.models.MyProductsModel;
import com.dev.objects.*;
import com.dev.pojo.TotalBidsCounter;
import com.dev.responses.*;
import com.dev.utils.Definitions;
import com.dev.utils.Errors;
import com.dev.utils.Persist;
import com.dev.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class FeaturesController extends MainController {
    @Autowired
    private Persist persist;
    @Autowired
    private Utils utils;

    @Autowired
    private LiveUpdatesController liveUpdatesController;

    @RequestMapping(value = "get-my-products", method = RequestMethod.GET)
    public BasicResponse getMyProducts(String token, Integer userId) {
        BasicResponse response = basicValidation(token, userId);
        if (response.isSuccess()) {
            List<Product> products = persist.getMyProductsForSale(userId);
            List<Bid> bids = persist.getBidsOnMyProducts(userId);
            List<MyProductsModel> productsList = utils.calculateBiggestBids(products, bids);
            response = new MyProductsResponse(true, null, productsList);
        }
        return response;
    }
    @RequestMapping(value = "get-my-bids" , method = RequestMethod.GET)
    public BasicResponse getMyBids(String token, Integer userId){
        BasicResponse response = basicValidation(token, userId);
        if (response.isSuccess()){
            List<Bid> myBids = persist.getBuyerBidsOrderByIdAsc(userId);
            response = new MyBidsResponse(true,null,myBids);
        }
        return response;
    }

    @RequestMapping(value = "place-bid", method = RequestMethod.POST)
    public BasicResponse placeBid(String token, Integer userId, Integer productId, int offer) {
        BasicResponse response = basicValidation(token, userId);
        if (response.isSuccess()) {
            if (offer % 1 == 0){
                if (productId != null) {
                    Product product = persist.productIsExist(productId);
                    if (product != null) {
                        User sellerUser = product.getSellerUser();
                        if (sellerUser.getId() != userId) {
                            if (product.isOpenForSale()) {
                                int creditBalance = persist.getUserCredit(token,userId) - offer;
                                if (creditBalance >= 0){
                                    Integer maxBidOffer = persist.getBiggestBidOnProduct(userId,productId);
                                    User buyerUser = persist.getUserById(userId);
                                    Bid bid = new Bid(sellerUser,buyerUser, product, offer);
                                    if ((maxBidOffer != null && offer > maxBidOffer) || (maxBidOffer == null && offer > product.getStartingPrice())) {
                                        persist.placeBid(bid,userId,creditBalance-Definitions.BID_COST_FEE);
                                        persist.addToSystemCredit(Definitions.BID_COST_FEE);
                                        liveUpdatesController.sendPlaceBidEvent(sellerUser.getId(),buyerUser.getUsername());
                                        liveUpdatesController.sendStatsEvent();
                                        response = new BasicResponse(true, null);
                                    } else {
                                        response = new BasicResponse(false, Errors.ERROR_OFFER_LOW);
                                    }
                                }else {
                                    response = new BasicResponse(false, Errors.ERROR_NOT_ENOUGH_CREDIT);

                                }
                            } else {
                                response = new BasicResponse(false, Errors.ERROR_PRODUCT_NOT_ON_SALE);

                            }
                        } else {
                            response = new BasicResponse(false, Errors.ERROR_BID_ON_YOUR_PRODUCT);
                        }
                    } else {
                        response = new BasicResponse(false, Errors.ERROR_PRODUCT_DOESNT_EXIST);

                    }
                } else {
                    response = new BasicResponse(false, Errors.ERROR_MISSING_PRODUCT_ID);
                }
            }else {
                response = new BasicResponse(false, Errors.ERROR_MUST_BE_INTEGER);

            }
        }
        return response;
    }


    @RequestMapping(value = "get-product-details", method = RequestMethod.GET)
    public BasicResponse getProductDetails(String token, Integer userId,Integer productId) {
        BasicResponse response = basicValidation(token, userId);
        if (response.isSuccess()) {
            Product product = persist.productIsExist(productId);
            if (product!=null){
                List<Bid> allProductBids = persist.getBidsByProductIdBidDateAsc(productId);
                List<Bid> myBids = allProductBids.stream().filter(bid-> bid.getBuyerUser().getId() == userId).collect(Collectors.toList());
                response = new ProductDetailsResponse(true,null,product,myBids,allProductBids.size());
            }
        }
        return response;
    }

    @RequestMapping(value = "get-products-for-sale", method = RequestMethod.GET)
    public BasicResponse getProductsForSale(String token, Integer userId) {
        BasicResponse response = basicValidation(token, userId);
        if (response.isSuccess()) {
            List<Product> productsForSale = persist.getProductsForSale(userId);
            List<Bid> bidsOnActiveAuctions = persist.getBidsOnActiveAuctions();
            Map<Product, TotalBidsCounter>  productsBidsMap =  utils.calculateProductsBidsMap(productsForSale,bidsOnActiveAuctions,userId);
            response = new ProductsForSaleResponse(true,null,productsBidsMap);
        }
        return response;

    }


    @RequestMapping(value = "close-auction", method = RequestMethod.POST)
    public BasicResponse closeAuction(String token, Integer userId, Integer productId) {
        BasicResponse response = basicValidation(token, userId);
        if (response.isSuccess()) {
            if (productId != null) {
                Product product = persist.productIsExist(productId);
                if (product != null) {
                    if (product.getSellerUser().getId() == userId) {
                        List<Bid> bidsOnProductAsc = persist.getBidsByProductIdBidDateAsc(productId);
                        if (bidsOnProductAsc.size() >= Definitions.MIN_BIDS_FOR_CLOSE_AUCTION) {
                            List<Bid> refundedBids = bidsOnProductAsc;
                            Product closedProduct = persist.closeAuction(productId);
                            List<Integer> biddersId = persist.getBiddersIdOnProduct(productId);
                            if (!closedProduct.isOpenForSale()) {
                                // update winner user , if winner user charge seller 5% of offer
                                liveUpdatesController.sendCloseAuctionEvent(product.getSellerUser().getUsername(),biddersId);
                                Bid winningBid = persist.getWinningBid(productId);
                                if (winningBid != null){
                                    persist.saveWinningBid(winningBid, closedProduct.getId());
                                    User sellerUser = winningBid.getSellerUser();
                                    Integer winningBidOffer = winningBid.getOffer();
                                    double sellerProfitCredit = (winningBidOffer * Definitions.PRODUCT_SELL_PROFIT_PERCENT);
                                    persist.addToUserCredit(sellerUser.getId() , (int) sellerProfitCredit);
                                    double systemProfitCredit = (winningBidOffer * (1-Definitions.PRODUCT_SELL_PROFIT_PERCENT));
                                    persist.addToSystemCredit((int)systemProfitCredit);
                                    refundedBids = refundedBids.stream().filter(bid -> (bid.getId() != winningBid.getId())).collect(Collectors.toList());
                                }
                                persist.refundNonWinnersBidsOffers(refundedBids);
                                liveUpdatesController.sendStatsEvent();
                                response = new BasicResponse(true, null);
                            } else {
                                response = new BasicResponse(false, Errors.GENERAL_ERROR);
                            }
                        } else {
                            response = new BasicResponse(false, Errors.PRODUCT_HASNT_ENOUGH_BIDS_FOR_CLOSE);
                        }
                    } else {
                        response = new BasicResponse(false, Errors.ERROR_USER_DOESNT_OWNER);
                    }

                } else {
                    response = new BasicResponse(false, Errors.ERROR_PRODUCT_DOESNT_EXIST);

                }
            } else {
                response = new BasicResponse(false, Errors.ERROR_MISSING_PRODUCT_ID);
            }
        }
        return response;
    }

    @RequestMapping(value = "add-new-product", method = RequestMethod.POST)
    public BasicResponse addNewProduct(String token, Integer userId, String name, String description, String logoUrl, Integer startingPrice) {
        BasicResponse response;
        name = name.trim();
        description = description.trim();
        logoUrl = logoUrl.trim();
        if (userId != null) {
            if (token != null) {
                if (userHasPermissions(userId, token)) {
                    if (name != null) {
                        if (description != null) {
                            if (logoUrl != null) {
                                // add check to valid logo url
                                if (startingPrice != null) {
                                    if (startingPrice % 1 == 0) {
                                        int creditBalance = persist.getUserCredit(token,userId) - Definitions.ADD_PRODUCT_FEE;
                                        if (creditBalance >= 0){
                                            User user = persist.getUserByToken(token);
                                            Product product = new Product(name, logoUrl, description, startingPrice, user);
                                            persist.addProduct(product,userId, creditBalance);
                                            persist.addToSystemCredit(Definitions.ADD_PRODUCT_FEE);
                                            liveUpdatesController.sendStatsEvent();
                                            response = new BasicResponse(true, null);
                                        }else {
                                            response = new BasicResponse(false, Errors.ERROR_NOT_ENOUGH_CREDIT);
                                        }

                                    } else {
                                        response = new BasicResponse(false, Errors.ERROR_MUST_BE_INTEGER);

                                    }
                                } else {
                                    response = new BasicResponse(false, Errors.PRODUCT_STARTING_PRICE_REQUIRED);
                                }

                            } else {
                                response = new BasicResponse(false, Errors.PRODUCT_LOGO_URL_REQUIRED);
                            }
                        } else {
                            response = new BasicResponse(false, Errors.PRODUCT_DESCRIPTION_REQUIRED);
                        }
                    } else {
                        response = new BasicResponse(false, Errors.PRODUCT_NAME_REQUIRED);
                    }

                } else {
                    response = new BasicResponse(false, Errors.PERMISSION_ERROR_CODE);
                }
            } else {
                response = new BasicResponse(false, Errors.ERROR_MISSING_PASSWORD);
            }
        } else {
            response = new BasicResponse(false, Errors.ERROR_MISSING_USERNAME);
        }
        return response;
    }




}