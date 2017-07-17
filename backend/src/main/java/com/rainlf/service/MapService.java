package com.rainlf.service;

import com.rainlf.mongo.entity.Map;

import java.util.List;

/**
 * Created by Administrator on 2017/7/6.
 */
public interface MapService {

    String addMap(Map map);

    List<Map> getMaps(String userId);

    String updateMapInfo(Map map);

    String deleteMap(String mapId);

    List<Map> getMapsByOrganId(String organId);

    List<Map> getAllMaps();
}
