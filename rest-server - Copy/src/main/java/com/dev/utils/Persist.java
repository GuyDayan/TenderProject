
package com.dev.utils;

import com.dev.objects.*;
import com.dev.pojo.Stats;
import io.swagger.models.auth.In;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class Persist {

    private final SessionFactory sessionFactory;

    @Autowired
    public Persist (SessionFactory sf) {
        this.sessionFactory = sf;
    }

    @PostConstruct
    public void createAdmin(){
        Session session = sessionFactory.openSession();
        String username = "admin";
        String password = "123456";
        Utils utils = new Utils();
        User user = getAdminUser();
        if (user == null){
            User adminUser = new User(username, utils.createHash(username, password), "System-User", "admin@gmail.com", Definitions.ADMIN_PARAM);
            adminUser.setCredit(0);
            session.save(adminUser);
        }
        session.close();
    }
    public User getAdminUser(){
        Session session = sessionFactory.openSession();
        User adminUser = (User) session.createQuery("FROM User WHERE userType =:admin ")
                .setParameter("admin", Definitions.ADMIN_PARAM)
                .uniqueResult();
        session.close();
        return adminUser;
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
        List<User> allUsers = session.createQuery("FROM User WHERE userType=:userParam").
        setParameter("userParam" , Definitions.USER_PARAM).list();
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



    public User getUserById(Integer id){
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




    public List<Bid> getBidsOnMyProducts(Integer userId) {
        Session session = sessionFactory.openSession();
        List<Bid> bids =
                session.createQuery("FROM Bid WHERE sellerUser.id =:userId")
                        .setParameter("userId", userId).list();
        session.close();
        return bids;
    }


    public Product productIsExist(Integer productId) {
        Session session = sessionFactory.openSession();
        return (Product) session.createQuery("FROM Product where id =:productId").setParameter("productId"  , productId)
                .uniqueResult();
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
                session.createQuery("FROM Product WHERE sellerUser.id != :userId AND openForSale=TRUE ")
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




    public List<Bid> getBuyerBidsOrderByIdAsc(Integer userId) {
        Session session = sessionFactory.openSession();
        List<Bid> bids =
                session.createQuery("FROM Bid WHERE buyerUser.id =:userId ORDER BY id ASC")
                        .setParameter("userId",userId).list();
        session.close();
        return bids;
    }

    public List<Product> getWinningProducts(Integer userId) {
        Session session = sessionFactory.openSession();
        List<Product> products =
                session.createQuery("FROM Product WHERE winningBid.buyerUser.id =:userId AND openForSale=FALSE")
                        .setParameter("userId",userId).list();
        session.close();
        return products;
    }

    public void saveWinningBid(Bid winningBid , Integer productId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Product product = session.get(Product.class,productId);
        product.setWinningBid(winningBid);
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


    public void updateUserCredit(Integer userId, Integer creditBalance) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class,userId);
        user.setCredit(creditBalance);
        session.update(user);
        transaction.commit();
        session.close();
    }

    public void addToUserCredit(Integer userId , Integer creditToAdd){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class,userId);
        user.setCredit(user.getCredit() + creditToAdd);
        session.update(user);
        transaction.commit();
        session.close();
    }



    public void addToSystemCredit(Integer creditToAdd) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = (User) session.createQuery("FROM User WHERE userType=:adminParam")
                .setParameter("adminParam" , Definitions.ADMIN_PARAM).uniqueResult();
        if (user!=null){
            user.setCredit(user.getCredit() + creditToAdd);
        }
        session.update(user);
        transaction.commit();
        session.close();
    }

    public List<Bid> getBidsOnActiveAuctions() {
        Session session = sessionFactory.openSession();
        List<Bid> bids =
                session.createQuery("FROM Bid WHERE product.openForSale=TRUE").list();
        session.close();
        return bids;
    }


    public List<Integer> getBiddersIdOnProduct(Integer productId) {
        Session session = sessionFactory.openSession();
        List<Integer> biddersId =
                session.createQuery("SELECT DISTINCT buyerUser.id FROM Bid WHERE product.id =: productId").
                        setParameter("productId" , productId).list();
        session.close();
        return biddersId;
    }


    public Stats getStats() {
        Session session = sessionFactory.openSession();
        int totalUsers = session.createQuery("FROM User ").list().size();
        int totalAuctions = session.createQuery("FROM Product").list().size();
        int totalBids = session.createQuery("FROM Bid ").list().size();
        return new Stats(totalUsers,totalAuctions,totalBids);

    }

    public Bid getWinningBid(Integer productId) {
        Session session = sessionFactory.openSession();
       Bid winningBid = (Bid) session.createQuery("FROM Bid WHERE (product.id = :productId) AND" +
                        " (offer = (SELECT MAX(offer) FROM Bid WHERE product.id = :productId)) ORDER BY bidDate ASC")
                .setParameter("productId", productId).setMaxResults(1).getSingleResult();
       return winningBid;
    }

    public void refundNonWinnersBidsOffers(List <Bid> nonWinningBids) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        for (Bid bid : nonWinningBids){
            addToUserCredit(bid.getBuyerUser().getId() , bid.getOffer());
            session.update(bid);
        }
        transaction.commit();
        session.close();
    }
}
