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
}
