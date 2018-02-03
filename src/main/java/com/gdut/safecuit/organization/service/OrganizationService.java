package com.gdut.safecuit.organization.service;

import com.gdut.safecuit.common.UniqueMainKeyMapper;
import com.gdut.safecuit.common.util.MatchUtil;
import com.gdut.safecuit.common.util.StringUtil;
import com.gdut.safecuit.device.service.DataTreeService;
import com.gdut.safecuit.organization.common.po.Organization;
import com.gdut.safecuit.organization.common.po.example.OrganizationExample;
import com.gdut.safecuit.organization.dao.OrganizationMapper;
import com.gdut.safecuit.user.common.po.User;
import com.gdut.safecuit.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.gdut.safecuit.organization.common.util.OrganizationConstant.*;

@Service
public class OrganizationService {

    @Resource
    private OrganizationMapper organizationMapper;

    @Resource
    private UniqueMainKeyMapper uniqueMainKeyMapper;

    @Resource
    private UserService userService;

    @Resource
    private DataTreeService dataTreeService;

    public boolean isExist(Integer orgId) {
        return orgId != null && selectByOrgId(orgId) != null;
    }

    public boolean isExist(String orgName) {
        return orgName != null && selectByOrgName(orgName) != null;
    }

    @Transactional
    public int insert(Organization org, User user) {
        // 从全局变量中获取 orgId
        int orgId = uniqueMainKeyMapper.getMainKey();
        uniqueMainKeyMapper.updateMainKey(orgId + 1, orgId);
        orgId++;

        // 添加用户
        user.setOrgId(orgId);
        userService.insert(user);

        // 添加机构
        org.setOrgId(orgId);
        org.setParentId(org.getAreaId()); // 默认添加机构是上级为area
        org.setManagerId(user.getUserId());
        org.setDelTag(0);

        int effect = organizationMapper.insertSelective(org);
        if (effect > 0) {
            // 更新省市区树状图
            dataTreeService.insertOrg(orgId);
        }
        return effect;
    }

    public int fakeDelete(int orgId) {
        Organization org = new Organization();
        org.setOrgId(orgId);
        org.setDelTag(1);
        return organizationMapper.updateByPrimaryKeySelective(org);
    }

    @Transactional
    public int fakeDelete(List<Integer> ids) {
        if (ids == null) {
            return -1;
        }
        int effect = 0;
        for (int id : ids) {
            // 假删除机构
            effect += fakeDelete(id);

            // 假删除机构员工
            userService.fakeDeleteByOrgId(id);
        }
        return effect;
    }

    public int delete(int orgId) {
        return organizationMapper.deleteByPrimaryKey(orgId);
    }

    public Organization selectByOrgId(int orgId) {
        Organization org = organizationMapper.selectByPrimaryKey(orgId);
        return org.getDelTag() == 1 ? null : org;
    }

    public Organization selectByOrgName(String orgName) {
        if (orgName == null) {
            return null;
        }
        OrganizationExample example = new OrganizationExample();
        example.or()
                .andNameEqualTo(orgName)
                .andDelTagEqualTo(0);
        List<Organization> orgs = organizationMapper.selectByExample(example);
        return orgs.size() == 0 ? null : orgs.get(0);
    }

    public List<Organization> selectOrganizationByParentId(int parentId) {
        OrganizationExample example = new OrganizationExample();
        example.or()
                .andParentIdEqualTo(parentId)
                .andDelTagEqualTo(0);
        return organizationMapper.selectByExample(example);
    }

    public List<Organization> selectOrganizationsByArea(int areaId) {
        OrganizationExample example = new OrganizationExample();
        example.or()
                .andAreaIdEqualTo(areaId)
                .andDelTagEqualTo(0);
        return organizationMapper.selectByExample(example);
    }

    public List<Organization> fuzzySearchByOrgName(String orgName) {
        OrganizationExample example = new OrganizationExample();
        example.or()
                .andDelTagEqualTo(0)
                .andNameLike("%" + orgName + "%");
        return organizationMapper.selectByExample(example);
    }

    public int update(Organization org) {
//        Organization originalOrg = selectByOrgId(org.getOrgId());
        // 设置禁止修改项
        org.setDelTag(null);
        return organizationMapper.updateByPrimaryKeySelective(org);
    }

    public List<Organization> selectOrganizationsByParent(int parentId, int page, int limit) {
        if (page < 0 || limit < 0) {
            return new ArrayList<>();
        }
        int offset = (page - 1) * limit;
        OrganizationExample example = new OrganizationExample();
        example.setLimit(limit);
        example.setOffset(offset);
        example.or()
                .andParentIdEqualTo(parentId)
                .andDelTagEqualTo(0);
        return organizationMapper.selectByExample(example);
    }

    public long getTotalRowsByParentId(int parentId) {
        OrganizationExample example = new OrganizationExample();
        example.or()
                .andParentIdEqualTo(parentId)
                .andDelTagEqualTo(0);
        return organizationMapper.countByExample(example);
    }

    public boolean isValidName(String name) {
        return StringUtil.isString(name, getNameMinLength(), getNameMaxLength());
    }

    public boolean isValidAddress(String address) {
        return StringUtil.isString(address, getAddressMinLength(), getAddressMaxLength());
    }

    public boolean isValidEmail(String email) {
        return StringUtil.isString(email, getEmailMinLength(), getEmailMaxLength()) && MatchUtil.isEmail(email);
    }

    public boolean isValidPhone(String phone) {
        return StringUtil.isString(phone, getPhoneMinLength(), getPhoneMaxLength()) && MatchUtil.isPhoneNumber(phone);
    }

    public boolean isValidDescription(String desc) {
        return StringUtil.isString(desc, getDescriptionMinLength(), getDescriptionMaxLength());
    }
}
