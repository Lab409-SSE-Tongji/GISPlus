package com.rainlf.controller;

import com.rainlf.mongo.entity.WellLayer;
import com.rainlf.service.WellLayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * Created by Administrator on 2017/7/6.
 */
@CrossOrigin
@RestController
@RequestMapping("layer/well")
public class WellLayerController {

    @Autowired
    private WellLayerService wellLayerService;

    /**
     * 获取窨井盖图层
     * @param mapId
     * @return
     */
    @RequestMapping(value = "{mapId}", method = RequestMethod.GET)
    public ResponseEntity getWellLayer(@PathVariable("mapId") String mapId) {
        return ResponseEntity.ok(wellLayerService.getWellLayer(mapId));
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity updateWellLayer(@RequestBody WellLayer wellLayer) {
        return ResponseEntity.ok(wellLayerService.updateWellLayer(wellLayer));
    }

    /**
     * 添加空的窨井盖图层
     * @param mapId
     * @return
     */
    @RequestMapping(value = "{mapId}", method = RequestMethod.POST)
    public ResponseEntity addWellLayer(@PathVariable("mapId") String mapId) {
        return ResponseEntity.ok(wellLayerService.addWellLayer(mapId));
    }

    /**
     * 删除窨井盖图层
     * @param layerId
     * @return
     */
    @RequestMapping(value = "{layerId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteWellLayer(@PathVariable("layerId") String layerId) {
        return ResponseEntity.ok(wellLayerService.deleteWellLayer(layerId));
    }

    /**
     * 导入窨井盖图层
     * @param layerId
     * @param file
     * @return
     */
    @RequestMapping(value = "excel/{layerId}", method = RequestMethod.POST)
    public ResponseEntity importWellLayer(@PathVariable("layerId") String layerId, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(wellLayerService.importWellLayer(layerId, file));
    }

    /**
     * 导出窨井盖图层
     * @param layerId
     * @return
     */
    @RequestMapping(value = "excel/{layerId}", method = RequestMethod.GET)
    public ResponseEntity exportWellLayer(@PathVariable("layerId") String layerId) {
        return ResponseEntity.ok(wellLayerService.exportWellLayer(layerId));
    }

}
