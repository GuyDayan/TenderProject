package com.dev.objects;


import javax.persistence.*;

@Entity
@Table(name = "tenders")
public class Tender {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User ownerUser;

    @Column(name = "opening_date")
    private boolean openingDate;

    @Column(name = "closing_date")
    private boolean closingDate;


    public Tender() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getOwnerUser() {
        return ownerUser;
    }

    public void setOwnerUser(User ownerUser) {
        this.ownerUser = ownerUser;
    }

    public boolean isOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(boolean openingDate) {
        this.openingDate = openingDate;
    }

    public boolean isClosingDate() {
        return closingDate;
    }

    public void setClosingDate(boolean closingDate) {
        this.closingDate = closingDate;
    }
}
