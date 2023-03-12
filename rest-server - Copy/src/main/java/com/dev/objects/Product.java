package com.dev.objects;
import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "logo_url")
    private String logoUrl;
    @Column
    private String description;
    @Column(name = "opening_sale_date")
    private Date openingSaleDate;
    @Column(name = "creation_date")
    private Date creationDate;
    @Column(name = "starting_price")
    private Integer startingPrice;
    @ManyToOne
    @JoinColumn(name = "seller_user_id")
    private User sellerUser;

    @ManyToOne
    @JoinColumn(name = "winner_user_id")
    private User winnerUserId;

    @Column(name = "is_open_for_sale")
    private boolean openForSale;


    public Product(){

    }

    public Product(String name, String logoUrl, String description, Date openingSaleDate ,Integer startingPrice, User sellerUser) {
        this.name = name;
        this.logoUrl = logoUrl;
        this.description = description;
        this.startingPrice = startingPrice;
        this.sellerUser = sellerUser;
        this.openingSaleDate = openingSaleDate;
        this.creationDate = new Date();
        this.openForSale = true;
    }

    public Date getOpeningSaleDate() {
        return openingSaleDate;
    }

    public void setOpeningSaleDate(Date openingSaleDate) {
        this.openingSaleDate = openingSaleDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(Integer startingPrice) {
        this.startingPrice = startingPrice;
    }

    public User getSellerUser() {
        return sellerUser;
    }

    public boolean isOpenForSale() {
        return openForSale;
    }

    public void setOpenForSale(boolean openForSale) {
        this.openForSale = openForSale;
    }

    public void setSellerUser(User sellerUser) {
        this.sellerUser = sellerUser;
    }

    public User getWinnerUserId() {
        return winnerUserId;
    }

    public void setWinnerUserId(User winnerUserId) {
        this.winnerUserId = winnerUserId;
    }
}
