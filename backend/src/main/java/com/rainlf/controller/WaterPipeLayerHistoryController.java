package com.rainlf.controller;

import com.rainlf.service.WaterPipeLayerHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2017/7/24.
 */

@CrossOrigin
@RestController
@RequestMapping("/history/layer/water")
public class WaterPipeLayerHistoryController {

    @Autowired
    private WaterPipeLayerHistoryService waterPipeLayerHistoryService;

    @RequestMapping(value = "{layerId}", method = RequestMethod.POST)
    public ResponseEntity addHistory(@PathVariable("layerId") String layerId) {
        return ResponseEntity.ok(waterPipeLayerHistoryService.addHistory(layerId));
    }

    @RequestMapping(value = "{historyId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteHistory(@PathVariable("historyId") String historyId) {
        return ResponseEntity.ok(waterPipeLayerHistoryService.deleteHistory(historyId));
    }

    @RequestMapping(value = "{mapId}", method = RequestMethod.GET)
    public ResponseEntity getAllHistorys(@PathVariable("mapId") String mapId) {
        return ResponseEntity.ok(waterPipeLayerHistoryService.getAllHistorys(mapId));
    }

}
