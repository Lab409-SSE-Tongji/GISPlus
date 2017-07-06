package com.rainlf.mongo.repository;

import com.rainlf.mongo.entity.WellLayer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by Administrator on 2017/7/6.
 */
public interface MongoWellLayerRepository extends MongoRepository<WellLayer, String > {

    WellLayer findByMapId(String mapId);
}
