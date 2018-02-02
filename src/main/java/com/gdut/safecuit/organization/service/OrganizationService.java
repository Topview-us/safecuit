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

    @Transactional
    public int insert(Organization org, User user) {
        // 从全局变量中获取 orgId
//        int orgId = uniqueMainKeyMapper.getMainKey();
//        uniqueMainKeyMapper.updateMainKey(orgId + 1, orgId);
        int orgId = 5;

        // 添加用户
        user.setOrgId(orgId);
        userService.insert(user);

        // 添加机构
        org.setOrgId(orgId);
        org.setParentId(org.getAreaId()); // 默认添加机构是上级为area
        org.setManagerId(user.getUserId());
        org.setDelTag(0);

        int effect = organizationMapper.insertSelective(org);
//        if (effect > 0) {
            // 更新省市区树状图
//            dataTreeService.insertOrg(orgId);
//        }
        return effect;
    }

    public boolean isExist(int orgId) {
        return selectOrganizationByOrgId(orgId) != null;
    }

    public Organization selectOrganizationByOrgId(int orgId) {
        Organization org = organizationMapper.selectByPrimaryKey(orgId);
        return org.getDelTag() == 1 ? null : org;
    }

    public List<Organization> fuzzySearchByOrgName(String name) {
        OrganizationExample example = new OrganizationExample();
        example.or()
                .andDelTagEqualTo(0)
                .andNameLike("%" + name + "%");
        return organizationMapper.selectByExample(example);
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
//        Organization originalOrg = selectOrganizationByOrgId(org.getOrgId());
        // 设置禁止修改项
        org.setDelTag(null);
        return organizationMapper.updateByPrimaryKeySelective(org);
    }

    public List<Organization> selectOrganizationsByArea(int areaId) {
        OrganizationExample example = new OrganizationExample();
        example.or().andDelTagEqualTo(0).andAreaIdEqualTo(areaId);
        return organizationMapper.selectByExample(example);
    }

    public List<Organization> selectOrganizationsByParent(int parentId, int page, int limit) {
        if (page < 0 || limit < 0) {
            return new ArrayList<>();
        }
        int offset = (page - 1) * limit;
        OrganizationExample example = new OrganizationExample();
        example.setLimit(limit);
        example.setOffset(offset);
        example.or().andDelTagEqualTo(0).andParentIdEqualTo(parentId);
        return organizationMapper.selectByExample(example);
    }

    public long getTotalRowsByParentId(int parentId) {
        OrganizationExample example = new OrganizationExample();
        example.or().andDelTagEqualTo(0).andParentIdEqualTo(parentId);
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

    public boolean isValidInfo(Organization org) {
        return isValidName(org.getName())
                && isValidAddress(org.getAddress())
                && isValidEmail(org.getEmail())
                && isValidPhone(org.getPhone())
                && isValidDescription(org.getDescription());
    }
}
