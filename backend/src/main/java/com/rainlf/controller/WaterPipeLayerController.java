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

    @RequestMapping(value = "{mapId}", method = RequestMethod.GET)
    public ResponseEntity getWellLayer(@PathVariable("mapId") String mapId) {
        return ResponseEntity.ok(waterPipeService.getWaterPipeLayer(mapId));
    }

    @RequestMapping(value = "{mapId}", method = RequestMethod.POST)
    public ResponseEntity addWellLayer(@PathVariable("mapId") String mapId) {
        return ResponseEntity.ok(waterPipeService.addWaterPipeLayer(mapId));
    }

    @RequestMapping(value = "{layerId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteWellLayer(@PathVariable("layerId") String layerId) {
        return ResponseEntity.ok(waterPipeService.deleteWaterPipeLayer(layerId));
    }

    @RequestMapping(value = "excel/{layerId}", method = RequestMethod.POST)
    public ResponseEntity importWellLayer(@PathVariable("layerId") String layerId, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(waterPipeService.importWaterPipeLayer(layerId, file));
    }
}
