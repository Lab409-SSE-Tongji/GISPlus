package com.rainlf.service.imp;

import com.rainlf.mongo.entity.Map;
import com.rainlf.mongo.repository.MongoMapRepository;
import com.rainlf.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/7/6.
 */
@Service
public class MapServiceImp implements MapService {

    @Autowired
    private MongoMapRepository mongoMapRepository;

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
}
