package com.dev.utils;

import com.dev.models.MyProductsModel;
import com.dev.objects.Bid;
import com.dev.objects.Product;
import com.dev.objects.User;
import com.dev.pojo.TotalBidsCounter;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.dev.utils.Definitions.MINIMAL_PASSWORD_LENGTH;
import static com.dev.utils.Definitions.MINIMAL_USERNAME_LENGTH;

@Component
public class Utils {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");


    public String createHash (String username, String password) {
        String raw = String.format("%s_%s", username, password);
        String myHash = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(raw.getBytes());
            byte[] digest = md.digest();
            myHash = DatatypeConverter
                    .printHexBinary(digest).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        return myHash;
    }

    public boolean isStrongPassword (String password) {
        return password.length() >= MINIMAL_PASSWORD_LENGTH;
    }
    public String formatDate(Date date){
        return simpleDateFormat.format(date);
    }

    public boolean isValidUsername(String username){
        return username.length() >= MINIMAL_USERNAME_LENGTH
                && username.matches("[a-zA-Z]+");
    }

    public List<MyProductsModel> calculateBiggestBids(List<Product> products , List<Bid> bids) {
        List<MyProductsModel> productModelList = new ArrayList<>();
        for (Product product : products){
            int currentBiggestBid = product.getStartingPrice();
            for (Bid bid : bids){
                if (product.getId() == bid.getProduct().getId()){
                    if (bid.getOffer() > currentBiggestBid){
                        currentBiggestBid = bid.getOffer();
                    }
                }
            }
            MyProductsModel productModel = new MyProductsModel(product,currentBiggestBid);
            productModelList.add(productModel);
        }
        return productModelList;
    }

    public User checkForAuctionWinner(List<Bid> bidsOnProductAsc, Product currentProduct) {
        User winnerUser = null;
        Integer maxPrice = currentProduct.getStartingPrice();
        for (Bid bid : bidsOnProductAsc){
            if (bid.getOffer() > maxPrice){
                maxPrice = bid.getOffer();
                winnerUser = bid.getBuyerUser();
            }
        }
        return winnerUser;
    }



    public Map<Bid, Boolean> calculateBidsStatusMap(List<Bid> bids, List<Product> winningProducts) {
        Map<Bid, Boolean> bidsStatusMap = new HashMap<>();
        if (winningProducts.size() == 0){
            for (Bid bid : bids){
                bidsStatusMap.put(bid,false);
            }
        }else {
            for (Product product : winningProducts){
                Bid highestBid = null;
                for (Bid bid : bids){
                    if (bid.getProduct().getId() == product.getId()){
                        if (highestBid == null){
                            highestBid = bid;
                            bidsStatusMap.put(highestBid , true);
                        }else {
                            if (bid.getOffer() > highestBid.getOffer()){
                                for (Map.Entry<Bid, Boolean> entry : bidsStatusMap.entrySet()) {
                                    if (entry.getKey().equals(highestBid)) {
                                        entry.setValue(false);
                                        break;
                                    }
                                }
                                highestBid = bid;
                                bidsStatusMap.put(highestBid,true);
                            }else {
                                bidsStatusMap.put(bid,false);
                            }
                        }

                    }
                }
        }

        }
        return bidsStatusMap;
    }


    public Map<Product,TotalBidsCounter> calculateProductsBidsMap(List<Product> productsForSale, List<Bid> bidsOnActiveAuctions , Integer userId) {
        Map<Product,TotalBidsCounter> totalBidsCounterMap = new HashMap<>();
        for (Product product : productsForSale){
            TotalBidsCounter totalBidsCounter = new TotalBidsCounter(0,0);
            for (Bid bid : bidsOnActiveAuctions){
                if (bid.getProduct().getId() == product.getId()){
                    if (bid.getBuyerUser().getId() == userId){
                        totalBidsCounter.setUserTotalBids(totalBidsCounter.getUserTotalBids() + 1);
                    }
                    totalBidsCounter.setAllUsersTotalBids(totalBidsCounter.getAllUsersTotalBids()+1);
                }
            }
            totalBidsCounterMap.put(product,totalBidsCounter);
        }
        return totalBidsCounterMap;
    }
}
