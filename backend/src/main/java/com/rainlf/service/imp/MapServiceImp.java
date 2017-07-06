package com.rainlf.service.imp;

import com.rainlf.mongo.entity.Map;
import com.rainlf.mongo.repository.MongoMapRepository;
import com.rainlf.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
