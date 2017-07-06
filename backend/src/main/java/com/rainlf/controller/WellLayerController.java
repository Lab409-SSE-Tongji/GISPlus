package com.rainlf.controller;

import com.rainlf.service.WellLayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2017/7/6.
 */
@CrossOrigin
@RestController
@RequestMapping("layer/well")
public class WellLayerController {

    @Autowired
    private WellLayerService wellLayerService;

    @RequestMapping(value = "{mapId}", method = RequestMethod.GET)
    public ResponseEntity getWellLayer(@PathVariable("mapId") String mapId) {
        return ResponseEntity.ok(wellLayerService.getWellLayer(mapId));
    }

    @RequestMapping(value = "{mapId}", method = RequestMethod.POST)
    public ResponseEntity addWellLayer(@PathVariable("mapId") String mapId) {
        return ResponseEntity.ok(wellLayerService.addWellLayer(mapId));
    }

    @RequestMapping(value = "{layerId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteWellLayer(@PathVariable("layerId") String layerId) {
        return ResponseEntity.ok(wellLayerService.deleteWellLayer(layerId));
    }

}
