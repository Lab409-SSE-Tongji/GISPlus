package com.rainlf.service;

import com.rainlf.mongo.entity.User;

import java.util.List;

/**
 * Created by Administrator on 2017/7/3.
 */
public interface UserService {

    String addUser(User user);

    User getUser(String username);

    User getUserById(String userId);

    String updateUserInfo(User user);

    String deleteUserInfo(String userId);

    List<User> getAllUsers();

    String addUserInfo(User user);

    List<User> getAllUsersByOrganId(String organId);

}
