package com.rainlf.service;

import com.rainlf.mongo.entity.WellLayerHistory;

import java.util.List;

/**
 * Created by Administrator on 2017/7/24.
 */
public interface WellLayerHistoryService {

    String addHistory(String layerId);

    String deleteHistory(String historyId);

    List<WellLayerHistory> getAllHistorys(String mapId);
}
