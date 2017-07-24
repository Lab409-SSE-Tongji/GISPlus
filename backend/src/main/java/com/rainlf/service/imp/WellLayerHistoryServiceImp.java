package com.rainlf.service.imp;

import com.rainlf.mongo.entity.WellLayer;
import com.rainlf.mongo.entity.WellLayerHistory;
import com.rainlf.mongo.repository.MongoWellLayerHistoryRepository;
import com.rainlf.mongo.repository.MongoWellLayerRepository;
import com.rainlf.service.WellLayerHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/7/24.
 */
@Service
public class WellLayerHistoryServiceImp implements WellLayerHistoryService {

    @Autowired
    private MongoWellLayerRepository mongoWellLayerRepository;

    @Autowired
    private MongoWellLayerHistoryRepository mongoWellLayerHistoryRepository;

    @Override
    public String addHistory(String layerId) {
        WellLayer wellLayer = mongoWellLayerRepository.findById(layerId);
        WellLayerHistory wellLayerHistory = new WellLayerHistory();

        wellLayerHistory.setMapId(wellLayer.getMapId());
        wellLayerHistory.setWellDomains(wellLayer.getWellDomains());
        wellLayerHistory.setCreateTime(new Date());

        mongoWellLayerHistoryRepository.insert(wellLayerHistory);
        return null;
    }

    @Override
    public String deleteHistory(String historyId) {
        mongoWellLayerHistoryRepository.delete(historyId);
        return null;
    }

    @Override
    public List<WellLayerHistory> getAllHistorys(String mapId) {
        return mongoWellLayerHistoryRepository.findAllByMapId(mapId);
    }
}
