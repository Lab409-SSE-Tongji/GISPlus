package com.rainlf.mongo.entity;

import com.rainlf.mongo.domain.WaterPipeDomain;
import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * Created by Administrator on 2017/7/6.
 */
public class WaterPipeLayer {

    @Id
    private String id;

    private String mapId;
    List<WaterPipeDomain> waterPipeDomains;

    public WaterPipeLayer() {
    }

    public WaterPipeLayer(String mapId) {
        this.mapId = mapId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMapId() {
        return mapId;
    }

    public void setMapId(String mapId) {
        this.mapId = mapId;
    }

    public List<WaterPipeDomain> getWaterPipeDomains() {
        return waterPipeDomains;
    }

    public void setWaterPipeDomains(List<WaterPipeDomain> waterPipeDomains) {
        this.waterPipeDomains = waterPipeDomains;
    }
}
