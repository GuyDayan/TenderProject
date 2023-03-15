package com.dev.models;
import com.dev.objects.User;
import com.dev.utils.Definitions;
import com.dev.utils.Utils;

import java.util.Date;

public class AllUsersModel {

    private int id;
    private String username;
    private String email;
    private String creationDate;
    private String lastLogin;


    public AllUsersModel(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.creationDate = Utils.simpleDateFormat.format(user.getCreationDate());
        this.lastLogin = user.getLastLogin();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }
}
