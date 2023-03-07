package com.dev.utils;

import com.dev.models.ProductModel;
import com.dev.objects.Bid;
import com.dev.objects.Product;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import static com.dev.utils.Definitions.MINIMAL_PASSWORD_LENGTH;
import static com.dev.utils.Definitions.MINIMAL_USERNAME_LENGTH;

@Component
public class Utils {


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

    public boolean isValidUsername(String username){
        return username.length() >= MINIMAL_USERNAME_LENGTH
                && username.matches("[a-zA-Z]+");
    }

    public List<ProductModel> calculateBiggestBids(List<Product> products , List<Bid> bids) {
        List<ProductModel> productModelList = new ArrayList<>();
        for (Product product : products){
            int currentBiggestBid = product.getStartingPrice();
            for (Bid bid : bids){
                if (product.getId() == bid.getProduct().getId()){
                    if (bid.getOffer() > currentBiggestBid){
                        currentBiggestBid = bid.getOffer();
                    }
                }
            }
            ProductModel productModel = new ProductModel(product,currentBiggestBid);
            productModelList.add(productModel);
        }
        return productModelList;
    }
}
