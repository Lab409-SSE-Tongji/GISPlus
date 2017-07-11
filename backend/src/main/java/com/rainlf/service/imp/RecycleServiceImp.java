package com.rainlf.service.imp;

import com.rainlf.mongo.entity.Map;
import com.rainlf.mongo.entity.Recycle;
import com.rainlf.mongo.repository.MongoMapRepository;
import com.rainlf.mongo.repository.MongoRecycleRepository;
import com.rainlf.mongo.repository.MongoWaterPipeLayerRepository;
import com.rainlf.mongo.repository.MongoWellLayerRepository;
import com.rainlf.service.RecycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/7/11.
 */
@Service
public class RecycleServiceImp implements RecycleService {

    @Autowired
    private MongoMapRepository mongoMapRepository;

    @Autowired
    private MongoWellLayerRepository mongoWellLayerRepository;

    @Autowired
    private MongoWaterPipeLayerRepository mongoWaterPipeLayerRepository;

    @Autowired
    private MongoRecycleRepository mongoRecycleRepository;

    @Override
    public List<Recycle> getRecycles(String userId) {
        return mongoRecycleRepository.findAllByUserId(userId);
    }

    @Override
    public String restoreRecycle(String recycleId) {
        Recycle recycle = mongoRecycleRepository.findOne(recycleId);
        mongoRecycleRepository.delete(recycleId);
        mongoMapRepository.insert(new Map(recycle.getId(),
                recycle.getUserId(),
                recycle.getMapName()));
        return null;
    }

    @Override
    public String deleteRecycle(String recycleId) {
        String mapId = mongoRecycleRepository.findOne(recycleId).getId();
        try {
            mongoWellLayerRepository.delete(mongoWellLayerRepository.findByMapId(mapId));
            mongoWaterPipeLayerRepository.delete(mongoWaterPipeLayerRepository.findByMapId(mapId));
        } catch (Exception e) {
//            e.printStackTrace();
        }
        mongoRecycleRepository.delete(recycleId);
        return null;
    }
}
