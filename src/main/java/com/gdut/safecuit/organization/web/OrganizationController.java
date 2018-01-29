package com.gdut.safecuit.organization.web;

import com.gdut.safecuit.common.Result;
import com.gdut.safecuit.organization.common.po.Area;
import com.gdut.safecuit.organization.common.po.City;
import com.gdut.safecuit.organization.common.po.Organization;
import com.gdut.safecuit.organization.common.po.Province;
import com.gdut.safecuit.organization.common.vo.OrgAddVO;
import com.gdut.safecuit.organization.common.vo.OrgVO;
import com.gdut.safecuit.organization.service.OrganizationService;
import com.gdut.safecuit.organization.service.ProvinceCityAreaService;
import com.gdut.safecuit.user.common.po.User;
import com.gdut.safecuit.user.service.UserService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/org")
public class OrganizationController {

    @Resource
    private OrganizationService organizationService;

    @Resource
    private UserService userService;

    @Resource
    private ProvinceCityAreaService provinceCityAreaService;

    @RequestMapping("/add")
    public Result<Integer> add(@RequestBody OrgAddVO orgAddVO) {
        if (orgAddVO.getOrgName() == null || orgAddVO.getOrgAddress() == null || orgAddVO.getOrgAreaId() == null
                || orgAddVO.getUsername() == null || orgAddVO.getUserRealName() == null || orgAddVO.getUserPassword() == null
                || orgAddVO.getUserRePassword() == null) {
            return new Result<>(0, "信息不能为空", false, 500);
        }
        //
        // 检验 user 数据合法性
        //
        User user = new User();
        user.setUsername(orgAddVO.getUsername());
        user.setRealName(orgAddVO.getUserRealName());
        user.setPassword(orgAddVO.getUserPassword());
        user.setPhone(orgAddVO.getUserPhone());
        user.setQq(orgAddVO.getUserQQ());
        user.setDescription(orgAddVO.getUserDescription());
        //
        // 检验 organization 数据合法性
        //
        Organization org = new Organization();
        org.setName(orgAddVO.getOrgName());
        org.setAddress(orgAddVO.getOrgAddress());
        org.setAreaId(orgAddVO.getOrgAreaId());
        org.setEmail(orgAddVO.getOrgEmail());
        org.setPhone(orgAddVO.getOrgPhone());
        org.setDescription(orgAddVO.getOrgDescription());

        int effect = organizationService.insert(org, user);
        return new Result<>(effect, "添加成功", true, 200);
    }

    @RequestMapping("/delete")
    public Result<Integer> delete(@RequestParam("orgId") Integer orgId) {
        if (orgId == null) {
            return new Result<>(0, "ordId无效", false, 500);
        }
        int effect = organizationService.fakeDelete(orgId);
        return new Result<>(effect, "删除成功", true, 200);
    }

    @RequestMapping("/update")
    public Result<Integer> update(Organization org) {
        if (org.getOrgId() == null) {
            return new Result<>(0, "orgId无效", false, 500);
        }
        if (!organizationService.isExist(org.getOrgId())) {
            return new Result<>(0, "机构不存在", false, 500);
        }
        //
        // 检验数据合法性
        //
        int effect = organizationService.update(org);
        return new Result<>(effect, "修改成功", true, 200);
    }

    @RequestMapping("/list")
    public Result<List<OrgVO>> selectByPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit) {
        if (offset == null || limit == null || offset < 0 || limit < 0) {
            return new Result<>(null, "无效请求", false, 500);
        }
        List<Organization> orgs = organizationService.selectOrganizationsByPage(offset, limit);
        List<OrgVO> orgVOS = new ArrayList<>();
        for (Organization org : orgs) {
            orgVOS.add(getOrgVo(org));
        }
        return new Result<>(orgVOS, "获取列表成功", true, 200);
    }

    @RequestMapping("/selectByOrgId")
    public Result<OrgVO> selectByOrgId(@RequestParam("orgId") Integer orgId) {
        if (orgId == null) {
            return new Result<>(null, "orgId无效", false, 500);
        }
        Organization org = organizationService.selectOrganizationByOrgId(orgId);
        return new Result<>(getOrgVo(org), "获取成功", true, 200);
    }

    /**
     * 封装 orgVO 对象
     *
     * @param org 原始对象
     * @return 封装后的对象
     */
    private OrgVO getOrgVo(Organization org) {
        OrgVO orgVO = new OrgVO();
        orgVO.setOrgId(org.getOrgId());
        orgVO.setName(org.getName());
        orgVO.setEmail(org.getEmail());
        orgVO.setPhone(org.getPhone());
        orgVO.setDescription(org.getDescription());

        // 获取省市区名字
        Area area = provinceCityAreaService.getArea("" + org.getAreaId());
        City city = provinceCityAreaService.getCity(area.getFather());
        Province province = provinceCityAreaService.getProvince(city.getFather());
        orgVO.setProvince(province.getProvince());
        orgVO.setCity(city.getCity());
        orgVO.setArea(area.getArea());

        // 获取管理员用户名
        User user = userService.selectUserByUserId(org.getManagerId());
        orgVO.setAdmin(user.getUsername());

        return orgVO;
    }
}
