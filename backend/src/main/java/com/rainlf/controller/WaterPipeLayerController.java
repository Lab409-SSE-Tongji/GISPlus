package com.rainlf.controller;

import com.rainlf.service.WaterPipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * Created by Administrator on 2017/7/6.
 */
@CrossOrigin
@RestController
@RequestMapping("layer/water")
public class WaterPipeLayerController {

    @Autowired
    private WaterPipeService waterPipeService;

    /**
     * 获取下水管道图层
     * @param mapId
     * @return
     */
    @RequestMapping(value = "{mapId}", method = RequestMethod.GET)
    public ResponseEntity getWaterPipeLayer(@PathVariable("mapId") String mapId) {
        return ResponseEntity.ok(waterPipeService.getWaterPipeLayer(mapId));
    }

    /**
     * 添加空的下水管道图层
     * @param mapId
     * @return
     */
    @RequestMapping(value = "{mapId}", method = RequestMethod.POST)
    public ResponseEntity addWaterPipeLayer(@PathVariable("mapId") String mapId) {
        return ResponseEntity.ok(waterPipeService.addWaterPipeLayer(mapId));
    }

    /**
     * 删除下水管道图层
     * @param layerId
     * @return
     */
    @RequestMapping(value = "{layerId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteWaterPipeLayer(@PathVariable("layerId") String layerId) {
        return ResponseEntity.ok(waterPipeService.deleteWaterPipeLayer(layerId));
    }

    /**
     * 导入下水管道图层
     * @param layerId
     * @param file
     * @return
     */
    @RequestMapping(value = "excel/{layerId}", method = RequestMethod.POST)
    public ResponseEntity importWaterPipeLayer(@PathVariable("layerId") String layerId, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(waterPipeService.importWaterPipeLayer(layerId, file));
    }

    /**
     * 导出下水管道图层
     * @param layerId
     * @return
     */
    @RequestMapping(value = "excel/{layerId}", method = RequestMethod.GET)
    public ResponseEntity exportWaterPipeLayer(@PathVariable("layerId") String layerId) {
        return ResponseEntity.ok(waterPipeService.exportWaterPipeLayer(layerId));
    }
}
