package com.rainlf.service.imp;

import com.rainlf.mongo.entity.User;
import com.rainlf.mongo.repository.MongoUserRepository;
import com.rainlf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

    @Override
    public String updateUserInfo(User user) {
        User userRemote = mongoUserRepository.findOne(user.getId());

        userRemote.setName(user.getName());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = user.getPassword();
        if ((rawPassword.indexOf('*') == -1) &&  (rawPassword.length() > 6)) {
            userRemote.setPassword(encoder.encode(rawPassword));
        }
        userRemote.setEmail(user.getEmail());
        userRemote.setPhone(user.getPhone());

        mongoUserRepository.save(userRemote);
        return null;
    }

    @Override
    public String deleteUserInfo(String userId) {
        mongoUserRepository.delete(userId);
        return null;
    }


    @Override
    public List<User> getAllUsers() {
        return mongoUserRepository.findAll();
    }

    @Override
    public String addUserInfo(User user) {
        final String username = user.getUsername();
        if (mongoUserRepository.findByUsername(username) != null) {
            return "EXIT";
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = user.getPassword();
        user.setPassword(encoder.encode(rawPassword));
        user.setLastPasswordResetDate(new Date());
        mongoUserRepository.insert(user);
        return null;
    }

    @Override
    public List<User> getAllUsersByOrganId(String organId) {
        return mongoUserRepository.findAllByOrganId(organId);
    }

}
