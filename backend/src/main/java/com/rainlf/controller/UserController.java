package com.rainlf.controller;

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

    @RequestMapping(value = "{username}", method = RequestMethod.GET)
    public ResponseEntity getUserInfo(@PathVariable("username") String username) {
        return ResponseEntity.ok(userService.getUser(username));
    }

    @RequestMapping(value = "id/{userId}", method = RequestMethod.GET)
    public ResponseEntity getUserInfoById(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }
}
