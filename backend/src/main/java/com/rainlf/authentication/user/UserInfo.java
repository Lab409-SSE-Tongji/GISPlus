package com.rainlf.authentication.user;

import com.rainlf.mongo.entity.User;
import com.sun.org.apache.bcel.internal.generic.NEW;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Justin on 2017/6/2.
 */

public class UserInfo {

    private String username;
    private String password;
    private Date lastPasswordResetDate;
    private List<String> roles = new ArrayList<>();

    private String name;

    public UserInfo() {
    }

    public UserInfo(String username, String password, Date lastPasswordResetDate, List<String> roles, String name) {
        this.username = username;
        this.password = password;
        this.lastPasswordResetDate = lastPasswordResetDate;
        this.roles = roles;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Date lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
