package com.rainlf.mongo.entity;

import org.springframework.data.annotation.Id;

/**
 * Created by Administrator on 2017/7/17.
 */
public class Organization {

    @Id
    private String id;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
