package com.rainlf.mongo.repository;

import com.rainlf.mongo.entity.Recycle;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Administrator on 2017/7/6.
 */
public interface MongoRecycleRepository extends MongoRepository<Recycle, String>{
}
