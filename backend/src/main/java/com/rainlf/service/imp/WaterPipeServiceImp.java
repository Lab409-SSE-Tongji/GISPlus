package com.rainlf.service.imp;

import com.rainlf.mongo.entity.WaterPipeLayer;
import com.rainlf.mongo.repository.MongoMapRepository;
import com.rainlf.mongo.repository.MongoWaterPipeLayerRepository;
import com.rainlf.service.ExcelService;
import com.rainlf.service.WaterPipeService;
import com.rainlf.util.ExcelUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


/**
 * Created by Administrator on 2017/7/6.
 */
@Service
public class WaterPipeServiceImp implements WaterPipeService {

    @Autowired
    private ExcelUtil excelUtil;

    @Autowired
    private ExcelService excelService;

    @Autowired
    private MongoMapRepository mongoMapRepository;

    @Autowired
    private MongoWaterPipeLayerRepository mongoWaterPipeLayerRepository;

    @Override
    public WaterPipeLayer getWaterPipeLayer(String mapId) {
        return mongoWaterPipeLayerRepository.findByMapId(mapId);
    }

    @Override
    public String addWaterPipeLayer(String mapId) {
        if (mongoWaterPipeLayerRepository.findByMapId(mapId) != null) {
            return "EXITS";
        }
        mongoWaterPipeLayerRepository.insert(new WaterPipeLayer(mapId));
        return null;
    }

    @Override
    public String updateWaterPipeLayer(WaterPipeLayer waterPipeLayer) {
        if (mongoWaterPipeLayerRepository.findById(waterPipeLayer.getId()) == null) {
            return "LAYER NOT EXITS";
        }
        mongoWaterPipeLayerRepository.save(waterPipeLayer);
        return null;
    }

    @Override
    public String deleteWaterPipeLayer(String layerId) {
        mongoWaterPipeLayerRepository.delete(layerId);
        return null;
    }

    @Override
    public String importWaterPipeLayer(String layerId, MultipartFile file) {
        WaterPipeLayer waterPipeLayer = excelService.importWaterPipeLayerFile(file);

        WaterPipeLayer waterPipeLayerRemote = mongoWaterPipeLayerRepository.findById(layerId);
        waterPipeLayerRemote.setWaterPipeDomains(waterPipeLayer.getWaterPipeDomains());

        mongoWaterPipeLayerRepository.save(waterPipeLayerRemote);
        return null;
    }

    @Override
    public String exportWaterPipeLayer(String layerId) {
        WaterPipeLayer waterPipeLayer = mongoWaterPipeLayerRepository.findById(layerId);
        String mapName = mongoMapRepository.findById(waterPipeLayer.getMapId()).getMapName();

        Workbook workbook = excelService.exportWaterPipeLayerFile(waterPipeLayer);
        String fileName = mapName+"_下水管道.xlsx";
        return excelUtil.writeToFile(fileName, workbook);
    }
}
