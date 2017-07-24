package com.rainlf.mongo.repository;

import com.rainlf.mongo.entity.WellLayerHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by Administrator on 2017/7/24.
 */
public interface MongoWellLayerHistoryRepository extends MongoRepository<WellLayerHistory, String> {

    List<WellLayerHistory> findAllByMapId(String mapId);
}
