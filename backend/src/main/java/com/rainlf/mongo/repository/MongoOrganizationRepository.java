package com.rainlf.mongo.repository;

import com.rainlf.mongo.entity.Organization;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Administrator on 2017/7/17.
 */
public interface MongoOrganizationRepository extends MongoRepository<Organization, String> {
}
