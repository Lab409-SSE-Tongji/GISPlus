package com.rainlf.service.imp;

import com.rainlf.mongo.entity.WellLayer;
import com.rainlf.mongo.repository.MongoWellLayerRepository;
import com.rainlf.service.ExcelService;
import com.rainlf.service.WellLayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2017/7/6.
 */
@Service
public class WellLayerServiceImp implements WellLayerService {

    @Autowired
    private ExcelService excelService;

    @Autowired
    private MongoWellLayerRepository mongoWellLayerRepository;

    @Override
    public WellLayer getWellLayer(String mapId) {
        return mongoWellLayerRepository.findByMapId(mapId);
    }

    @Override
    public String addWellLayer(String mapId) {
        if (mongoWellLayerRepository.findByMapId(mapId) != null) {
            return "EXITS";
        }
        mongoWellLayerRepository.insert(new WellLayer(mapId));
        return null;
    }

    @Override
    public String deleteWellLayer(String layerId) {
        mongoWellLayerRepository.delete(layerId);
        return null;
    }

    @Override
    public String importWellLayer(String layerId, MultipartFile file) {
        WellLayer wellLayer = excelService.importWellLayerFile(file);

        WellLayer wellLayerRemote = mongoWellLayerRepository.findById(layerId);
        wellLayerRemote.setWellDomains(wellLayer.getWellDomains());

        mongoWellLayerRepository.save(wellLayerRemote);

        return null;
    }
}
