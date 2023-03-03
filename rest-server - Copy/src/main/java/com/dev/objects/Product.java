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

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "starting_price")
    private Integer startingPrice;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User ownerUser;


    public Product(){

    }

    public Product(String name, String logoUrl, String description, Integer startingPrice, User ownerUser) {
        this.name = name;
        this.logoUrl = logoUrl;
        this.description = description;
        this.startingPrice = startingPrice;
        this.ownerUser = ownerUser;
        this.creationDate = new Date();
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

    public User getOwnerUser() {
        return ownerUser;
    }

    public void setOwnerUser(User ownerUser) {
        this.ownerUser = ownerUser;
    }
}
