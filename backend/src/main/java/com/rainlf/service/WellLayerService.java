package com.rainlf.service;

import com.rainlf.mongo.entity.WellLayer;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2017/7/6.
 */
public interface WellLayerService {

    WellLayer getWellLayer(String mapId);

    String addWellLayer(String mapId);

    String updateWellLayer(WellLayer wellLayer);

    String deleteWellLayer(String layerId);

    String importWellLayer(String layerId, MultipartFile file);

    String exportWellLayer(String layerId);
}
