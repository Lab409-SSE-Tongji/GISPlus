package com.rainlf.service;

import com.rainlf.mongo.entity.WaterPipeLayer;

import javax.xml.ws.Service;

/**
 * Created by Administrator on 2017/7/6.
 */
public interface WaterPipeService {

    WaterPipeLayer getWaterPipeLayer(String mapId);

    String addWaterPipeLayer(String mapId);

    String deleteWaterPipeLayer(String layerId);
}
