package com.rainlf.service.imp;

import com.rainlf.mongo.entity.WellLayer;
import com.rainlf.mongo.repository.MongoMapRepository;
import com.rainlf.mongo.repository.MongoWellLayerRepository;
import com.rainlf.service.ExcelService;
import com.rainlf.service.WellLayerService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017/7/6.
 */
@Service
public class WellLayerServiceImp implements WellLayerService {

    @Value("${file.server.local.url}")
    private String fileServerLocalUrl;

    @Value("${file.server.remote.url}")
    private String fileServerRemoteUrl;

    @Value("${file.server.export}")
    private String fileServerExport;

    @Autowired
    private ExcelService excelService;

    @Autowired
    private MongoWellLayerRepository mongoWellLayerRepository;

    @Autowired
    private MongoMapRepository mongoMapRepository;

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

    @Override
    public String exportWellLayer(String layerId) {
        WellLayer wellLayer = mongoWellLayerRepository.findById(layerId);
        String mapName = mongoMapRepository.findById(wellLayer.getMapId()).getMapName();

        Workbook workbook = excelService.exportWellLayerFile(wellLayer);
        String fileName = mapName+"_窨井盖.xlsx";
        try {
            FileOutputStream out = new FileOutputStream(fileServerLocalUrl+fileServerExport+fileName);
            workbook.write(out);
            out.close();
            return fileServerRemoteUrl+fileServerExport+fileName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
