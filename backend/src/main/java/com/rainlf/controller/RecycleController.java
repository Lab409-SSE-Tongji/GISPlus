package com.rainlf.controller;

import com.rainlf.service.RecycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Service;

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
     * 获取用户所有回收站地图
     * @param userId
     * @return
     */
    @RequestMapping(value = "{userId}", method = RequestMethod.GET)
    public ResponseEntity getRecycles(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(recycleService.getRecycles(userId));
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
