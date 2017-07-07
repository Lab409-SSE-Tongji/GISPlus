package com.rainlf.mongo.repository;

import com.rainlf.mongo.entity.WaterPipeLayer;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Administrator on 2017/7/6.
 */
public interface MongoWaterPipeLayerRepository extends MongoRepository<WaterPipeLayer, String> {

    WaterPipeLayer findByMapId(String mapId);

    WaterPipeLayer findById(String LayerId);
}
