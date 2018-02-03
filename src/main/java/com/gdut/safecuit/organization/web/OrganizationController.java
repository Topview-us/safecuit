package com.gdut.safecuit.organization.web;

import com.gdut.safecuit.common.Result;
import com.gdut.safecuit.organization.common.po.Area;
import com.gdut.safecuit.organization.common.po.City;
import com.gdut.safecuit.organization.common.po.Organization;
import com.gdut.safecuit.organization.common.po.Province;
import com.gdut.safecuit.organization.common.vo.OrgAddVO;
import com.gdut.safecuit.organization.common.vo.OrgEditVO;
import com.gdut.safecuit.organization.common.vo.OrgVO;
import com.gdut.safecuit.organization.service.OrganizationService;
import com.gdut.safecuit.organization.service.ProvinceCityAreaService;
import com.gdut.safecuit.user.common.po.User;
import com.gdut.safecuit.user.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/org")
public class OrganizationController {

    @Resource
    private OrganizationService organizationService;

    @Resource
    private UserService userService;

    @Resource
    private ProvinceCityAreaService provinceCityAreaService;

    @RequestMapping("/checkDuplicateOrgName")
    public Result<Integer> checkDuplicateOrgName(@RequestParam("orgName") String orgName) {
        if (!organizationService.isValidName(orgName)) {
            return new Result<>(0, "机构名不合法", false, 500);
        }
        if (organizationService.isExist(orgName)) {
            return new Result<>(0, "机构名已被注册", false, 500);
        } else {
            return new Result<>(0, "机构名可以使用", true, 200);
        }
    }

    @RequestMapping("/add")
    public Result<Integer> add(OrgAddVO orgAddVO) {
        boolean pass = false;
        // 检验用户信息
        if (userService.isValidUsername(orgAddVO.getUsername())
                && userService.isValidRealName(orgAddVO.getUserRealName())
                && userService.isValidPassword(orgAddVO.getUserPassword(), orgAddVO.getUserRePassword())
                && userService.isValidPhone(orgAddVO.getUserPhone())
                && userService.isValidQQ(orgAddVO.getUserQQ())
                && userService.isValidDescription(orgAddVO.getUserDescription())) {
            pass = true;
        }
        // 检验机构信息
        if (pass && organizationService.isValidName(orgAddVO.getOrgName())
                && organizationService.isValidAddress(orgAddVO.getOrgAddress())
                && organizationService.isValidEmail(orgAddVO.getOrgEmail())
                && organizationService.isValidPhone(orgAddVO.getOrgPhone())
                && organizationService.isValidDescription(orgAddVO.getOrgDescription())
                && provinceCityAreaService.getArea(orgAddVO.getOrgAreaId() + "") != null) {
            pass = true;
        }

        if (!pass) {
            return new Result<>(0, "信息输入有误", false, 500);
        } else {
            User user = new User();
            user.setUsername(orgAddVO.getUsername());
            user.setRealName(orgAddVO.getUserRealName());
            user.setPassword(orgAddVO.getUserPassword());
            user.setPhone(orgAddVO.getUserPhone());
            user.setQq(orgAddVO.getUserQQ());
            user.setDescription(orgAddVO.getUserDescription());

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
    }

    @RequestMapping("/delete")
    public Result<Integer> deleteOrganizations(@RequestParam("ids") List<Integer> ids) {
        if (ids == null) {
            return new Result<>(0, "参数有误", false, 500);
        }
        int effect = organizationService.fakeDelete(ids);
        if (effect > 0) {
            return new Result<>(effect, "删除成功", true, 200);
        } else {
            return new Result<>(effect, "删除失败", false, 500);
        }
    }


    @RequestMapping("/selectOrganizationByOrgId")
    public Result<OrgVO> selectOrganizationByOrgId(@RequestParam("orgId") Integer orgId) {
        if (orgId == null) {
            return new Result<>(null, "orgId无效", false, 500);
        }
        Organization org = organizationService.selectByOrgId(orgId);
        return new Result<>(getOrgVO(org), "获取成功", true, 200);
    }

    @RequestMapping("/listByAreaId")
    public Result<List<OrgVO>> selectOrganizationsByAreaId(@RequestParam("areaId") Integer areaId) {
        List<Organization> orgs = organizationService.selectOrganizationsByArea(areaId);
        if (orgs == null) {
            return new Result<>(null, "获取机构列表失败", false, 500);
        } else {
            return new Result<>(getOrgVOs(orgs), "获取机构列表成功", true, 200, orgs.size());
        }
    }

    @RequestMapping("/list")
    public Result<List<OrgVO>> selectOrganizationsByParentIdByPage(@RequestParam("parentId") Integer parentId,
                                                                   @RequestParam("page") Integer page,
                                                                   @RequestParam("limit") Integer limit) {
        if (parentId == null || page == null || page < 0 || limit == null || limit < 0) {
            return new Result<>(null, "获取机构列表失败", false, 500);
        }
        long rows = organizationService.getTotalRowsByParentId(parentId);

        List<Organization> orgs = organizationService.selectOrganizationsByParent(parentId, page, limit);

        return new Result<>(getOrgVOs(orgs), "获取列表成功", true, 200, (int) rows);
    }

    @RequestMapping("getOrgEditInfo")
    public Result<OrgEditVO> getOrgEditInfo(@RequestParam("orgId") Integer orgId) {
        if (orgId == null) {
            return new Result<>(null, "orgId无效", false, 500);
        }
        Organization org = organizationService.selectByOrgId(orgId);
        if (org == null) {
            return new Result<>(null, "获取机构失败", false, 500);
        } else {
            return new Result<>(getOrgEditVO(org), "获取成功", true, 200);
        }
    }

    @RequestMapping("/update")
    public Result<Integer> update(Organization org) {
        if (org.getOrgId() == null) {
            return new Result<>(0, "orgId无效", false, 500);
        }
        if (!organizationService.isExist(org.getOrgId())) {
            return new Result<>(0, "机构不存在", false, 500);
        }

        boolean pass = true;
        if (org.getName() != null && !organizationService.isValidName(org.getName())) {
            pass = false;
        }
        if (org.getEmail() != null && !organizationService.isValidEmail(org.getEmail())) {
            pass = false;
        }
        if (org.getAddress() != null && !organizationService.isValidAddress(org.getAddress())) {
            pass = false;
        }
        if (org.getDescription() != null && !organizationService.isValidDescription(org.getDescription())) {
            pass = false;
        }
        if (org.getPhone() != null && !organizationService.isValidPhone(org.getPhone())) {
            pass = false;
        }

        if (pass) {
            int effect = organizationService.update(org);
            return new Result<>(effect, "修改成功", true, 200);
        } else {
            return new Result<>(0, "信息有误", false, 500);
        }
    }

    private List<OrgVO> getOrgVOs(List<Organization> orgs) {
        List<OrgVO> orgVOS = new ArrayList<>();
        for (Organization org : orgs) {
            orgVOS.add(getOrgVO(org));
        }
        return orgVOS;
    }

    private OrgEditVO getOrgEditVO(Organization org) {
        System.out.println("OrganizationController getOrgEditVO ------------------------------------------");
        System.out.println("封装 " + org);
        System.out.println("OrganizationController getOrgEditVO ------------------------------------------");
        OrgEditVO orgEditVO = new OrgEditVO();
        orgEditVO.setOrgId(org.getOrgId());
        orgEditVO.setName(org.getName());
        orgEditVO.setEmail(org.getEmail());
        orgEditVO.setPhone(org.getPhone());
        orgEditVO.setDescription(org.getDescription());
        orgEditVO.setAddress(org.getAddress());

        Map<String, Object> map = provinceCityAreaService.getProvinceCityArea(org.getAreaId());
        Province province = (Province) map.get("province");
        City city = (City) map.get("city");
        Area area = (Area) map.get("area");

        orgEditVO.setProvince(province.getProvince());
        orgEditVO.setCity(city.getCity());
        orgEditVO.setArea(area.getArea());

        orgEditVO.setProvinceId(Integer.valueOf(province.getProvinceId()));
        orgEditVO.setCityId(Integer.valueOf(city.getCityId()));
        orgEditVO.setAreaId(Integer.valueOf(area.getAreaId()));

        // 获取管理员用户名
        User user = userService.selectByUserId(org.getManagerId());
        if (user != null) {
            orgEditVO.setAdmin(user.getUsername());
        }

        return orgEditVO;
    }

    /**
     * 封装 orgVO 对象
     *
     * @param org 原始对象
     * @return 封装后的对象
     */
    private OrgVO getOrgVO(Organization org) {
        System.out.println("OrganizationController getOrgVO ------------------------------------------");
        System.out.println("封装 " + org);
        System.out.println("OrganizationController getOrgVO ------------------------------------------");
        OrgVO orgVO = new OrgVO();
        orgVO.setOrgId(org.getOrgId());
        orgVO.setName(org.getName());
        orgVO.setEmail(org.getEmail());
        orgVO.setPhone(org.getPhone());
        orgVO.setDescription(org.getDescription());
        orgVO.setAddress(org.getAddress());

        // 获取省市区名字
//        Area area = provinceCityAreaService.getArea("" + org.getAreaId());
//        City city = provinceCityAreaService.getCity(area.getFather());
//        Province province = provinceCityAreaService.getProvince(city.getFather());
//        orgVO.setProvince(province.getProvince());
//        orgVO.setCity(city.getCity());
//        orgVO.setArea(area.getArea());
        Map<String, Object> map = provinceCityAreaService.getProvinceCityArea(org.getAreaId());
        Province province = (Province) map.get("province");
        City city = (City) map.get("city");
        Area area = (Area) map.get("area");
        orgVO.setProvince(province.getProvince());
        orgVO.setCity(city.getCity());
        orgVO.setArea(area.getArea());

        // 获取管理员用户名
        User user = userService.selectByUserId(org.getManagerId());
        if (user != null) {
            orgVO.setAdmin(user.getUsername());
        }

        return orgVO;
    }
}
