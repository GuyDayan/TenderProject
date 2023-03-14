
package com.dev.utils;

import com.dev.objects.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Persist {

    private final SessionFactory sessionFactory;

    @Autowired
    public Persist (SessionFactory sf) {
        this.sessionFactory = sf;
    }

    public User getUserByUsername (String username) {
        User found = null;
        Session session = sessionFactory.openSession();
        found = (User) session.createQuery("FROM User WHERE username = :username")
                .setParameter("username", username)
                .uniqueResult();
        session.close();
        return found;
    }

    public void saveUser (User user) {
        Session session = sessionFactory.openSession();
        session.save(user);
        session.close();
    }


    public User getUserByUsernameAndToken (String username, String token) {
        User found = null;
        Session session = sessionFactory.openSession();
        found = (User) session.createQuery("FROM User WHERE username = :username " +
                        "AND token = :token")
                .setParameter("username", username)
                .setParameter("token", token)
                .uniqueResult();
        session.close();
        return found;
    }

    public List<User> getAllUsers () {
        Session session = sessionFactory.openSession();
        List<User> allUsers = session.createQuery("FROM User ").list();
        session.close();
        return allUsers;
    }

    public User getUserByToken (String token) {
        Session session = sessionFactory.openSession();
        User user = (User) session.createQuery("From User WHERE token = :token")
                        .setParameter("token", token)
                                .uniqueResult();
        session.close();
        return user;
    }

    public List<Message> getMessagesByToken (String token) {
        Session session = sessionFactory.openSession();
        List<Message> messages = session.createQuery("FROM Message WHERE recipient.token = :token ")
                .setParameter("token", token)
                .list();
        session.close();
        return messages;
    }

    public List<Message> getConversation (String token, int recipientId) {
        Session session = sessionFactory.openSession();
        List<Message> messages =
                session.createQuery("FROM Message WHERE " +
                                "(sender.token=:token OR recipient.token=:token)" +
                                " AND " +
                                "(sender.id =:id OR recipient.id=:id) ORDER BY id DESC")
                .setParameter("token", token)
                .setParameter("id", recipientId)
                .list();
        session.close();
        return messages;
    }


    public User getUserById(int id){
        Session session = sessionFactory.openSession();
        User user  =
                (User) session.createQuery("FROM User WHERE id=:id").setParameter("id",id)
                        .uniqueResult();
        session.close();
        return user;
    }

    public void addProduct(Product product , Integer userId , int creditBalance){
        Session session = sessionFactory.openSession();
        session.save(product);
        session.close();
        updateUserCredit(userId,creditBalance);
    }

    public boolean userHasPermissions(int userId, String token) {
        boolean hasPermissions = false;
        User user = (User) sessionFactory.openSession().
                createQuery("FROM User WHERE id=:userId").
                setParameter("userId", userId).getSingleResult();
        if (user.getToken().equals(token)) hasPermissions = true;
        return hasPermissions;

    }

    public List<Product> getProductsBySellerUserId(Integer userId) {
        Session session = sessionFactory.openSession();
        List<Product> products =
                session.createQuery("FROM Product WHERE sellerUser.id =:userId ORDER BY id DESC")
                        .setParameter("userId", userId)
                        .list();
        session.close();
        return products;
    }
    public void saveMessage(Message message){

    }



    public List<Bid> getBidsBySellerUserId(Integer userId) {
        Session session = sessionFactory.openSession();
        List<Bid> bids =
                session.createQuery("FROM Bid WHERE sellerUser.id =:userId")
                        .setParameter("userId", userId).list();
        session.close();
        return bids;
    }

    public List<Bid> getBidsByBuyerUserId(Integer userId) {
        Session session = sessionFactory.openSession();
        List<Bid> bids =
                session.createQuery("FROM Bid WHERE buyerUser.id =:userId")
                        .setParameter("userId", userId).list();
        session.close();
        return bids;
    }

    public Product productIsExist(Integer productId) {
        Session session = sessionFactory.openSession();
        return (Product) session.createQuery("FROM Product where id =:productId").setParameter("productId"  , productId).uniqueResult();
    }

    public Product closeAuction(Integer productId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Product product = session.get(Product.class,productId);
        product.setOpenForSale(false);
        session.update(product);
        transaction.commit();
        session.close();
        return product;
    }

    public List<Product> getProductsForSale(Integer userId) {
        Session session = sessionFactory.openSession();
        List<Product> products =
                session.createQuery("FROM Product WHERE sellerUser.id != :userId")
                        .setParameter("userId", userId).list();
        session.close();
        return products;

    }

    public List<Product> getMyProductsForSale(Integer userId) {
        Session session = sessionFactory.openSession();
        List<Product> products =
                session.createQuery("FROM Product WHERE sellerUser.id =: userId")
                        .setParameter("userId", userId).list();
        session.close();
        return products;

    }


    public List<Bid> getBidsByProductIdBidDateAsc(Integer productId) {
        Session session = sessionFactory.openSession();
        List<Bid> bids =
                session.createQuery("FROM Bid WHERE product.id =:productId ORDER BY bidDate ASC")
                        .setParameter("productId", productId).list();
        session.close();
        return bids;
    }

    public Integer getBiggestBidOnProduct(Integer userId , Integer productId) {
        Integer maxOffer = null;
        Session session = sessionFactory.openSession();
        List<Bid> bids =
                session.createQuery("FROM Bid WHERE product.id =:productId AND buyerUser.id =:userId ORDER BY offer DESC")
                        .setParameter("productId", productId).setParameter("userId", userId).list();
        session.close();
        if (!(bids.size() == 0)){
            maxOffer = bids.get(0).getOffer();
        }
        return maxOffer;
    }

    public void placeBid(Bid bid,Integer userId, Integer creditBalance) {
        Session session = sessionFactory.openSession();
        session.save(bid);
        session.close();
        updateUserCredit(userId,creditBalance);
    }




    public List<Bid> getBuyerBidsOrderByDataAsc(Integer userId) {
        Session session = sessionFactory.openSession();
        List<Bid> bids =
                session.createQuery("FROM Bid WHERE buyerUser.id =:userId ORDER BY bidDate ASC")
                        .setParameter("userId",userId).list();
        session.close();
        return bids;
    }

    public List<Product> getWinningProducts(Integer userId) {
        Session session = sessionFactory.openSession();
        List<Product> products =
                session.createQuery("FROM Product WHERE winnerUser.id =:userId AND openForSale=FALSE")
                        .setParameter("userId",userId).list();
        session.close();
        return products;
    }

    public void saveWinnerUser(Integer productId , User winnerUser) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Product product = session.get(Product.class,productId);
        product.setWinnerUser(winnerUser);
        session.update(product);
        transaction.commit();
        session.close();
    }





    public Integer getUserCredit(String token, Integer userId) {
        Session session = sessionFactory.openSession();
        User user =
                (User) session.createQuery("FROM User WHERE id =:userId AND token=: token")
                        .setParameter("userId",userId).setParameter("token",token).uniqueResult();
        session.close();
        return user.getCredit();
    }

    public Bid getWinningBid(Integer winnerUserId, Integer closedProductId) {
        Session session = sessionFactory.openSession();
       Bid bid =
               (Bid) session.createQuery("FROM Bid WHERE buyerUser.id =: winnerUserId AND product.id=: closedProductId")
                        .setParameter("winnerUserId",winnerUserId).setParameter("closedProductId",closedProductId).uniqueResult();
        session.close();
        return bid;
    }

    public void updateUserCredit(Integer userId, Integer creditBalance) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class,userId);
        user.setCredit(creditBalance);
        session.update(user);
        transaction.commit();
        session.close();
    }
}
