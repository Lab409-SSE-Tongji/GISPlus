package com.rainlf.service;

import com.rainlf.mongo.entity.WaterPipeLayer;
import com.rainlf.mongo.entity.WellLayer;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;


/**
 * Created by Administrator on 2017/7/7.
 */
public interface ExcelService {

    WellLayer importWellLayerFile(MultipartFile file);

    WaterPipeLayer importWaterPipeLayerFile(MultipartFile file);

    Workbook exportWellLayerFile(WellLayer wellLayer);

    Workbook exportWaterPipeLayerFile(WaterPipeLayer waterPipeLayer);
}
