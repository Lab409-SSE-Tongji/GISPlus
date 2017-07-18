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

    /**
     * 新建地图
     * @param map
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addMap(@RequestBody Map map) {
        return ResponseEntity.ok(mapService.addMap(map));
    }

    /**
     * 获取用户所有地图 用户
     * @param userId
     * @return
     */
    @RequestMapping(value = "/userMaps", method = RequestMethod.GET)
    public ResponseEntity getMaps(@RequestParam("userId") String userId) {
        return ResponseEntity.ok(mapService.getMaps(userId));
    }

    /**
     * 获取用户所有地图 组织管理员
     * @param organId
     * @return
     */
    @RequestMapping(value = "organMaps", method = RequestMethod.GET)
    public ResponseEntity getMapsByOrganId(@RequestParam("organId") String organId) {
        return ResponseEntity.ok(mapService.getMapsByOrganId(organId));
    }

    /**
     * 获取用户所有地图 超级管理员
     * @param
     * @return
     */
    @RequestMapping(value = "allMaps", method = RequestMethod.GET)
    public ResponseEntity getAllMaps() {
        return ResponseEntity.ok(mapService.getAllMaps());
    }


    /**
     * 更新地图信息
     * @param map
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity updateMapInfo(@RequestBody Map map) {
        return ResponseEntity.ok(mapService.updateMapInfo(map));
    }

    /**
     * 删除地图
     * @param mapId
     * @return
     */
    @RequestMapping(value = "{mapId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteMap(@PathVariable("mapId") String mapId) {
        return ResponseEntity.ok(mapService.deleteMap(mapId));
    }

    /**
     * 分配地图
     * @param mapId
     * @param userId
     * @return
     */
    @RequestMapping(value = "deliver", method = RequestMethod.POST)
    public ResponseEntity deliverMap(@RequestParam("mapId") String mapId, @RequestParam("userId") String userId) {
        return ResponseEntity.ok(mapService.deliverMap(mapId, userId));
    }

    @RequestMapping(value = "deliverMaps", method = RequestMethod.GET)
    public ResponseEntity getDeliverMaps(@RequestParam("userId") String userId) {
        return ResponseEntity.ok(mapService.getDeliverMaps(userId));
    }
}
