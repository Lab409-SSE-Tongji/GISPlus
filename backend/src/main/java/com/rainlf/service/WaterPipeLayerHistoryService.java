package com.rainlf.service;

import com.rainlf.mongo.entity.WaterPipeLayerHistory;

import java.util.List;

/**
 * Created by Administrator on 2017/7/24.
 */
public interface WaterPipeLayerHistoryService {

    String addHistory(String layerId);

    String deleteHistory(String historyId);

    List<WaterPipeLayerHistory> getAllHistorys(String mapId);
}
