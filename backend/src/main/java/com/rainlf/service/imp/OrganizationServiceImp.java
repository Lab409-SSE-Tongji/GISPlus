package com.rainlf.service.imp;

import com.rainlf.mongo.entity.Organization;
import com.rainlf.mongo.repository.MongoOrganizationRepository;
import com.rainlf.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/7/17.
 */
@Service
public class OrganizationServiceImp implements OrganizationService{

    @Autowired
    private MongoOrganizationRepository mongoOrganizationRepository;

    @Override
    public String addOrganization(Organization organization) {
        mongoOrganizationRepository.insert(organization);
        return null;
    }

    @Override
    public String deleteOrganization(String organId) {
        mongoOrganizationRepository.delete(organId);
        return null;
    }

    @Override
    public String updateOrganization(Organization organization) {
        Organization organizationRemonte = mongoOrganizationRepository.findOne(organization.getId());
        // 修改对应信息
        organizationRemonte.setName(organization.getName());
        // 存储
        mongoOrganizationRepository.save(organizationRemonte);
        return null;
    }

    @Override
    public List<Organization> getOrganizations() {
        return mongoOrganizationRepository.findAll();
    }
}
