package com.rainlf.authentication.user;

import com.rainlf.mongo.entity.User;
import com.rainlf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/7/2.
 * Write implements here for project.
 */
@Service
public class UserInfoServiceImp implements UserInfoService {

    @Autowired
    private UserService userService;

    @Override
    public UserInfo getUserInfo(String username) {
        User user = userService.getUser(username);
        if (user == null) {
            return null;
        } else {
            return new UserInfo(user.getUsername(),
                    user.getPassword(),
                    user.getLastPasswordResetDate(),
                    user.getRoles(),
                    user.getName());
        }
    }

    @Override
    public UserInfo addUserInfo(UserInfo userInfoToAdd) {
        userService.addUser(new User(userInfoToAdd.getUsername(),
                userInfoToAdd.getPassword(),
                userInfoToAdd.getName(),
                userInfoToAdd.getLastPasswordResetDate(),
                userInfoToAdd.getRoles()));
        return userInfoToAdd;
    }
}
