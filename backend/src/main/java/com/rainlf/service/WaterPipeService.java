package com.rainlf.service;

import com.rainlf.mongo.entity.WaterPipeLayer;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.ws.Service;

/**
 * Created by Administrator on 2017/7/6.
 */
public interface WaterPipeService {

    WaterPipeLayer getWaterPipeLayer(String mapId);

    String addWaterPipeLayer(String mapId);

    String updateWaterPipeLayer(WaterPipeLayer waterPipeLayer);

    String deleteWaterPipeLayer(String layerId);

    String importWaterPipeLayer(String layerId, MultipartFile file);

    String exportWaterPipeLayer(String layerId);
}
