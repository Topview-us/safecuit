package com.gdut.safecuit.organization.service;

import com.gdut.safecuit.BaseTest;
import com.gdut.safecuit.organization.common.po.Organization;
import com.gdut.safecuit.user.common.po.User;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class OrganizationServiceTest extends BaseTest {

    @Resource
    private OrganizationService organizationService;

    @Test
    public void insert() {
        Organization org = new Organization();
        for (int i = 0; i < 26; i++) {
            org.setName("机构" + i);
            org.setAreaId(110102);
            org.setAddress("address");
            org.setEmail("aaaa@bbbbbb.com");
            org.setPhone("18045631296");
            org.setDescription("desc");

            User user = new User();
            user.setUsername("username" + i);
            user.setRealName("realName" + i);
            user.setPassword("123456");
            user.setPhone("13015654345");
            user.setQq(123667312 + i * i);
            organizationService.insert(org, user);
        }
    }

    @Test
    public void selectOrganizationByOrgId() {
        System.out.println(organizationService.selectByOrgId(1046707));
    }

    @Test
    public void delete() {
        organizationService.delete(119406064);
    }

    @Test
    public void fakeDelete() {
        organizationService.fakeDelete(98223113);
    }

    @Test
    public void update() {
        Organization org = new Organization();
        org.setOrgId(1046707);
        org.setPhone("123232-323");
        org.setDelTag(9);
        organizationService.update(org);
    }

    @Test
    public void fuzzySearchByOrgName() {
        List<Organization> orgs = organizationService.fuzzySearchByOrgName("na");
        System.out.println("-------------------------------------");
        for (Organization org : orgs) {
            System.out.println(org);
        }
        System.out.println("-------------------------------------");
    }

    @Test
    public void selectOrganizationsByArea() {
        List<Organization> orgs = organizationService.selectOrganizationsByArea(110102);
        System.out.println("-------------------------------------");
        for (Organization org : orgs) {
            System.out.println(org);
        }
        System.out.println("-------------------------------------");
    }

    @Test
    public void isExist() {
        System.out.println("---------------------------------");
        System.out.println(organizationService.isExist("name10"));
        System.out.println("---------------------------------");
    }

    @Test
    public void fakeDeleteOrganizations() {
        List<Integer> list = new ArrayList<>();
        list.add(52);
        list.add(53);
        organizationService.fakeDelete(list);
    }
}