package com.rainlf.controller;

import com.rainlf.mongo.entity.User;
import com.rainlf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2017/7/6.
 */
@CrossOrigin
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 根据用户名或获取用户信息
     * @param username
     * @return
     */
    @RequestMapping(value = "{username}", method = RequestMethod.GET)
    public ResponseEntity getUserInfo(@PathVariable("username") String username) {
        return ResponseEntity.ok(userService.getUser(username));
    }

    /**
     * 根据用户ID获取用户信息
     * @param userId
     * @return
     */
    @RequestMapping(value = "id/{userId}", method = RequestMethod.GET)
    public ResponseEntity getUserInfoById(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    /**
     * 更新用户信息
     * @param userId
     * @param user
     * @return
     */
    @RequestMapping(value = "id/{userId}", method = RequestMethod.PUT)
    public ResponseEntity updateUserInfo(@PathVariable("userId") String userId, @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUserInfo(user));
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @RequestMapping(value = "id/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUserInfo(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(userService.deleteUserInfo(userId));
    }

}
