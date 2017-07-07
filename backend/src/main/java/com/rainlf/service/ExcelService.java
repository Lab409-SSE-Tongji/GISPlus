package com.rainlf.service;

import com.rainlf.mongo.entity.WaterPipeLayer;
import com.rainlf.mongo.entity.WellLayer;
import org.springframework.web.multipart.MultipartFile;


/**
 * Created by Administrator on 2017/7/7.
 */
public interface ExcelService {

    WellLayer importWellLayerFile(MultipartFile file);

    WaterPipeLayer importWaterPipeLayerFile(MultipartFile file);
}
