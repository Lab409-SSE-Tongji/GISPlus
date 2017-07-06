package com.rainlf.service;

import com.rainlf.mongo.entity.WellLayer;

/**
 * Created by Administrator on 2017/7/6.
 */
public interface WellLayerService {

    WellLayer getWellLayer(String mapId);

    String addWellLayer(String mapId);

    String deleteWellLayer(String layerId);
}
