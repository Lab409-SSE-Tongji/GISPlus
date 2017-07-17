package com.rainlf.mongo.repository;

import com.rainlf.mongo.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by Administrator on 2017/7/3.
 */
public interface MongoUserRepository extends MongoRepository<User, String> {

    User findByUsername(String username);

}
