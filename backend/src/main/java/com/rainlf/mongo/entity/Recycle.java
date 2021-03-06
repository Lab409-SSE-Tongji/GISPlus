package com.rainlf.mongo.entity;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/6.
 */
public class Recycle {

    @Id
    private String id;

    private String userId;
    private String organId;
    private String mapName;
    private List<String> owners = new ArrayList<>();

    public Recycle() {
    }

    public Recycle(String id, String userId, String organId, String mapName, List<String> owners) {
        this.id = id;
        this.userId = userId;
        this.organId = organId;
        this.mapName = mapName;
        this.owners = owners;
    }

    public List<String> getOwners() {
        return owners;
    }

    public void setOwners(List<String> owners) {
        this.owners = owners;
    }

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

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
