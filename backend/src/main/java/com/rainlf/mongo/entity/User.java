package com.rainlf.mongo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/7/3.
 */
public class User {

    @Id
    private String id;

    @Indexed(unique = true, direction = IndexDirection.ASCENDING, dropDups = true)
    private String username;

    private String password;
    private String name;
    private Date lastPasswordResetDate;
    private List<String> roles;

    private String email;
    private String phone;
    private String imgUrl;
    private String parentId;
    private List<String> mapId;

    public User() {
    }

    public User(String username, String password, String name, Date lastPasswordResetDate, List<String> roles) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastPasswordResetDate = lastPasswordResetDate;
        this.roles = roles;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<String> getMapId() {
        return mapId;
    }

    public void setMapId(List<String> mapId) {
        this.mapId = mapId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
