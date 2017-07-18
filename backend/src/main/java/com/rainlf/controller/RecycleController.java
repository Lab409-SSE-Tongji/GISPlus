package com.rainlf.controller;

import com.rainlf.service.RecycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2017/7/11.
 */
@CrossOrigin
@RestController
@RequestMapping("recycle")
public class RecycleController {

    @Autowired
    private RecycleService recycleService;

    /**
     * 获取所有回收站地图 用户
     * @param userId
     * @return
     */
    @RequestMapping(value = "userRecycle", method = RequestMethod.GET)
    public ResponseEntity getUserRecycles(@RequestParam("userId") String userId) {
        return ResponseEntity.ok(recycleService.getUserRecycles(userId));
    }

    /**
     * 获取所有回收站地图 管理员
     * @param organId
     * @return
     */
    @RequestMapping(value = "organRecycle", method = RequestMethod.GET)
    public ResponseEntity getOrganIdRecycles(@RequestParam("organId") String organId) {
        return ResponseEntity.ok(recycleService.getOrganRecycles(organId));
    }

    /**
     * 获取所有回收站地图 超级管理员
     * @param
     * @return
     */
    @RequestMapping(value = "allRecycle", method = RequestMethod.GET)
    public ResponseEntity getAllRecycles() {
        return ResponseEntity.ok(recycleService.getAllRecycles());
    }

    /**
     * 还原用户地图
     * @param recycleId
     * @return
     */
    @RequestMapping(value = "restore/{recycleId}", method = RequestMethod.POST)
    public ResponseEntity restoreRecycle(@PathVariable("recycleId") String recycleId) {
        return ResponseEntity.ok(recycleService.restoreRecycle(recycleId));
    }

    /**
     * 删除回收站地图
     * @param recycleId
     * @return
     */
    @RequestMapping(value = "{recycleId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteRecycle(@PathVariable("recycleId") String recycleId) {
        return ResponseEntity.ok(recycleService.deleteRecycle(recycleId));
    }

    /**
     * 清空回收站地图
     * @param userId
     * @return
     */
    @RequestMapping(value = "all/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteRecycles(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(recycleService.deleteRecycles(userId));
    }
}
