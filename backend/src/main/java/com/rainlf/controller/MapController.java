package com.rainlf.controller;

import com.rainlf.mongo.entity.Map;
import com.rainlf.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2017/7/6.
 */
@CrossOrigin
@RestController
@RequestMapping("map")
public class MapController {

    @Autowired
    private MapService mapService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addMap(@RequestBody Map map) {
        return ResponseEntity.ok(mapService.addMap(map));
    }

    @RequestMapping(value = "{userId}/maps", method = RequestMethod.GET)
    public ResponseEntity getMaps(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(mapService.getMaps(userId));
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity updateMapInfo(@RequestBody Map map) {
        return ResponseEntity.ok(mapService.updateMapInfo(map));
    }

    @RequestMapping(value = "{mapId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteMap(@PathVariable("mapId") String mapId) {
        return ResponseEntity.ok(mapService.deleteMap(mapId));
    }
}
