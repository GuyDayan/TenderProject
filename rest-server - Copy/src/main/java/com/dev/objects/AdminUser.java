package com.dev.objects;

import javax.persistence.*;

@Entity
@Table (name = "admin_users")
public class AdminUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private Integer credit;

    @Column
    private String uniqueToken;

    public AdminUser(String username, String password , String uniqueToken) {
        this.username = username;
        this.password = password;
        this.uniqueToken = uniqueToken;
        this.credit = 0;
    }

    public AdminUser() {

    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
