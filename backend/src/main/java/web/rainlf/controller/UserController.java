package web.rainlf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.rainlf.mysql.table.UserDO;
import web.rainlf.service.UserService;

/**
 * Created by Administrator on 2017/7/1.
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody UserDO userDO) {
        return ResponseEntity.ok(userService.add(userDO));
    }

    @RequestMapping(value = "{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable String userId) {
        return ResponseEntity.ok(userService.delete(userId));
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody UserDO userDO) {
        return ResponseEntity.ok(userService.update(userDO));
    }

    @RequestMapping(value = "{username}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserDO(username));
    }

}
