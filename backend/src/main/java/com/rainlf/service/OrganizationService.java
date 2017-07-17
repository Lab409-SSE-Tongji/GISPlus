package com.rainlf.service;

import com.rainlf.mongo.entity.Organization;

import java.util.List;

/**
 * Created by Administrator on 2017/7/17.
 */
public interface OrganizationService {

    String addOrganization(Organization organization);

    String deleteOrganization(String organId);

    String updateOrganization(Organization organization);

    List<Organization> getOrganizations();

    Organization getOrganizationById(String organId);
}
