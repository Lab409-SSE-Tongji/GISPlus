package com.rainlf.service.imp;

import com.rainlf.mongo.entity.User;
import com.rainlf.mongo.repository.MongoUserRepository;
import com.rainlf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/7/3.
 */
@Service
public class UserServiceImp implements UserService {

    @Autowired
    private MongoUserRepository mongoUserRepository;

    @Override
    public String addUser(User user) {
        mongoUserRepository.insert(user);
        return null;
    }

    @Override
    public User getUser(String username) {
        return mongoUserRepository.findByUsername(username);
    }

    @Override
    public User getUserById(String userId) {
        return mongoUserRepository.findOne(userId);
    }
}
