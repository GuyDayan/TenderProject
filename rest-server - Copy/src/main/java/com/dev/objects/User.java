package com.dev.objects;

import com.dev.utils.Definitions;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table (name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private String username;

    @Column
    private String token;

    @Column
    private String email;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "creation_date")
    private Date creationDate;


    @Column(name = "last_login")
    private String lastLogin;

    @Column
    private Integer credit;


    public User() {
    }

    public User(String username, String token,String fullName,String email) {
        this.username = username;
        this.token = token;
        this.fullName = fullName;
        this.email = email;
        this.credit = Definitions.INITIAL_CREDIT;
        this.creationDate = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }
}
