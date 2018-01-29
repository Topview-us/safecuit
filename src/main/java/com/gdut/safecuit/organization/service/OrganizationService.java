package com.gdut.safecuit.organization.service;

import com.gdut.safecuit.organization.common.po.Organization;
import com.gdut.safecuit.organization.common.po.example.OrganizationExample;
import com.gdut.safecuit.organization.dao.OrganizationMapper;
import com.gdut.safecuit.user.common.po.User;
import com.gdut.safecuit.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

@Service
public class OrganizationService {

    @Resource
    private OrganizationMapper organizationMapper;

    @Resource
    private UserService userService;

    @Transactional
    public int insert(Organization org, User user) {
        // 从全局变量中获取 orgId
        int orgId = new Random().nextInt(100000000);

        // 添加用户
        user.setOrgId(orgId);
        userService.insert(user);

        // 添加机构
        org.setOrgId(orgId);
        org.setParentId(org.getAreaId()); // 默认添加机构是上级为area
        org.setManagerId(user.getUserId());
        org.setDelTag(0);

        //
        // 更新省市区树状图
        //
        return organizationMapper.insertSelective(org);
    }

    public boolean isExist(int orgId) {
        return selectOrganizationByOrgId(orgId) != null;
    }

    public Organization selectOrganizationByOrgId(int orgId) {
        Organization org = organizationMapper.selectByPrimaryKey(orgId);
        return org.getDelTag() == 1 ? null : org;
    }

    public List<Organization> selectOrganizationsByPage(int offset, int limit) {
        OrganizationExample example = new OrganizationExample();
        example.setOffset(offset);
        example.setLimit(limit);
        example.or()
                .andDelTagEqualTo(0);
        return organizationMapper.selectByExample(example);
    }

    public List<Organization> selectOrganizationByParentId(int parentId) {
        OrganizationExample example = new OrganizationExample();
        example.or()
                .andParentIdEqualTo(parentId);
        return organizationMapper.selectByExample(example);
    }

    public int delete(int orgId) {
        return organizationMapper.deleteByPrimaryKey(orgId);
    }

    public int fakeDelete(int orgId) {
        Organization org = new Organization();
        org.setOrgId(orgId);
        org.setDelTag(1);
        return organizationMapper.updateByPrimaryKeySelective(org);
    }

    public int update(Organization org) {
        Organization originalOrg = selectOrganizationByOrgId(org.getOrgId());
        // 设置禁止修改项
        org.setDelTag(null);
        return organizationMapper.updateByPrimaryKeySelective(org);
    }
}
