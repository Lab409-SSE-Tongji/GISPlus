package com.rainlf.controller;

import com.rainlf.mongo.entity.Organization;
import com.rainlf.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2017/7/17.
 */
@CrossOrigin
@RestController
@RequestMapping("organ")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    /**
     * 新建组织
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addOrganization(@RequestBody Organization organization) {
        return ResponseEntity.ok(organizationService.addOrganization(organization));
    }

    /**
     * 删除组织
     * @return
     */
    @RequestMapping(value = "{organId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOrganization(@PathVariable("organId") String organId) {
        return ResponseEntity.ok(organizationService.deleteOrganization(organId));
    }

    /**
     * 修改组织
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity deleteOrganization(@RequestBody Organization organization) {
        return ResponseEntity.ok(organizationService.updateOrganization(organization));
    }

    /**
     * 获取全部组织
     * @return
     */
    @RequestMapping(value = "organs", method = RequestMethod.GET)
    public ResponseEntity getOrganizations() {
        return ResponseEntity.ok(organizationService.getOrganizations());
    }


    @RequestMapping(value = "{organId}", method = RequestMethod.GET)
    public ResponseEntity getOrganizationById(@PathVariable("organId") String organId) {
        return ResponseEntity.ok(organizationService.getOrganizationById(organId));
    }
}

