package com.rainlf.controller;

import com.rainlf.service.WellLayerHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2017/7/24.
 */

@CrossOrigin
@RestController
@RequestMapping("history/layer/well")
public class WellLayerHistoryController {

    @Autowired
    private WellLayerHistoryService wellLayerHistoryServic;

    @RequestMapping(value = "{layerId}", method = RequestMethod.POST)
    public ResponseEntity addHistory(@PathVariable("layerId") String layerId) {
        return ResponseEntity.ok(wellLayerHistoryServic.addHistory(layerId));
    }

    @RequestMapping(value = "{historyId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteHistory(@PathVariable("historyId") String historyId) {
        return ResponseEntity.ok(wellLayerHistoryServic.deleteHistory(historyId));
    }

    @RequestMapping(value = "{mapId}", method = RequestMethod.GET)
    public ResponseEntity getAllHistorys(@PathVariable("mapId") String mapId) {
        return ResponseEntity.ok(wellLayerHistoryServic.getAllHistorys(mapId));
    }
}
