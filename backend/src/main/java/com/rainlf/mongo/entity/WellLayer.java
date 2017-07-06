package com.rainlf.mongo.entity;

import com.rainlf.mongo.domain.WellDomain;
import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * Created by Administrator on 2017/7/6.
 */
public class WellLayer {

    @Id
    private String id;

    private String mapId;
    private List<WellDomain> wellDomains;

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

    public List<WellDomain> getWellDomains() {
        return wellDomains;
    }

    public void setWellDomains(List<WellDomain> wellDomains) {
        this.wellDomains = wellDomains;
    }
}
