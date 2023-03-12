package com.dev.objects;

import com.dev.utils.Definitions;

import javax.persistence.*;

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

    @Column
    private String creation_date;


    @Column(name = "last_login")
    private String lastLogin;

    @Column
    private double credit;


    public User() {
    }

    public User(String username, String token) {
        this.username = username;
        this.token = token;
        this.credit = Definitions.INITIAL_CREDIT;
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
}
