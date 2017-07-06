package com.rainlf.mongo.entity;

import org.springframework.data.annotation.Id;

/**
 * Created by Administrator on 2017/7/6.
 */
public class Map {

    @Id
    private String id;

    private String userId;
    private String mapName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }
}
