package com.rainlf.service.imp;

import com.rainlf.mongo.entity.WaterPipeLayer;
import com.rainlf.mongo.entity.WaterPipeLayerHistory;
import com.rainlf.mongo.repository.MongoWaterPipeLayerHistoryRepository;
import com.rainlf.mongo.repository.MongoWaterPipeLayerRepository;
import com.rainlf.service.WaterPipeLayerHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/7/24.
 */
@Service
public class WaterPipeLayerHistoryServiceImp implements WaterPipeLayerHistoryService {

    @Autowired
    private MongoWaterPipeLayerRepository mongoWaterPipeLayerRepository;

    @Autowired
    private MongoWaterPipeLayerHistoryRepository mongoWaterPipeLayerHistoryRepository;

    @Override
    public String addHistory(String layerId) {
        WaterPipeLayer waterPipeLayer = mongoWaterPipeLayerRepository.findById(layerId);
        WaterPipeLayerHistory waterPipeLayerHistory = new WaterPipeLayerHistory();

        waterPipeLayerHistory.setMapId(waterPipeLayer.getMapId());
        waterPipeLayerHistory.setWaterPipeDomains(waterPipeLayer.getWaterPipeDomains());
        waterPipeLayerHistory.setCreateTime(new Date());

        mongoWaterPipeLayerHistoryRepository.insert(waterPipeLayerHistory);
        return null;
    }

    @Override
    public String deleteHistory(String historyId) {
        mongoWaterPipeLayerHistoryRepository.delete(historyId);
        return null;
    }

    @Override
    public List<WaterPipeLayerHistory> getAllHistorys(String mapId) {
        return mongoWaterPipeLayerHistoryRepository.findAllByMapId(mapId);
    }
}
