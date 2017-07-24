package com.rainlf.mongo.repository;

import com.rainlf.mongo.entity.WaterPipeLayerHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by Administrator on 2017/7/24.
 */
public interface MongoWaterPipeLayerHistoryRepository extends MongoRepository<WaterPipeLayerHistory, String> {

    List<WaterPipeLayerHistory> findAllByMapId(String mapId);
}
