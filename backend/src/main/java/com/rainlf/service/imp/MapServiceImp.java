package com.rainlf.service.imp;

import com.rainlf.mongo.entity.Map;
import com.rainlf.mongo.entity.Recycle;
import com.rainlf.mongo.repository.MongoMapRepository;
import com.rainlf.mongo.repository.MongoRecycleRepository;
import com.rainlf.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/7/6.
 */
@Service
public class MapServiceImp implements MapService {

    @Autowired
    private MongoMapRepository mongoMapRepository;

    @Autowired
    private MongoRecycleRepository mongoRecycleRepository;

    @Override
    public String addMap(Map map) {
        mongoMapRepository.insert(map);
        return null;
    }

    @Override
    public List<Map> getMaps(String userId) {
        return mongoMapRepository.findAllByUserId(userId);
    }

    @Override
    public String updateMapInfo(Map map) {
        Map mapRemote = mongoMapRepository.findOne(map.getId());
        mapRemote.setMapName(map.getMapName());
        mongoMapRepository.save(mapRemote);
        return null;
    }

    @Override
    public String deleteMap(String mapId) {
        Map map = mongoMapRepository.findOne(mapId);
        mongoMapRepository.delete(mapId);
        mongoRecycleRepository.insert(new Recycle(map.getId(),
                map.getUserId(),
                map.getOrganId(),
                map.getMapName()));
        return null;
    }

    @Override
    public List<Map> getMapsByOrganId(String organId) {
        return mongoMapRepository.findAllByOrganId(organId);
    }

    @Override
    public List<Map> getAllMaps() {
        return mongoMapRepository.findAll();
    }
}
