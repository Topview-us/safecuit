package com.gdut.safecuit.user.web;

import com.gdut.safecuit.common.Result;
import com.gdut.safecuit.organization.common.po.Area;
import com.gdut.safecuit.organization.common.po.City;
import com.gdut.safecuit.organization.common.po.Organization;
import com.gdut.safecuit.organization.common.po.Province;
import com.gdut.safecuit.organization.service.OrganizationService;
import com.gdut.safecuit.organization.service.ProvinceCityAreaService;
import com.gdut.safecuit.user.common.po.User;
import com.gdut.safecuit.user.common.vo.LoginVO;
import com.gdut.safecuit.user.common.vo.UserEditVO;
import com.gdut.safecuit.user.common.vo.UserVO;
import com.gdut.safecuit.user.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private OrganizationService organizationService;

    @Resource
    private ProvinceCityAreaService provinceCityAreaService;

    @RequestMapping("/login")
    public Result<Integer> checkLogin(LoginVO loginVO) {
//        if (loginVO.getUsername() == null || loginVO.getPassword() == null) {
//            return new Result<>(0, "用户名或密码不能为空", false, 500);
//        }
        if (loginVO == null) {
            return new Result<>(0, "登录失败", false, 500);
        }
        User user = new User();
        user.setUsername(loginVO.getUsername());
        user.setPassword(loginVO.getPassword());
        boolean pass = userService.checkLogin(user);
        if (pass) {
            return new Result<>(1, "登陆成功", true, 200);
        } else {
            return new Result<>(0, "用户名或密码错误", false, 500);
        }
    }

    @RequestMapping("/add")
    public Result<Integer> addUser(User user, @RequestParam("rePassword") String rePassword) {
        // 校验数据正确性
        boolean pass = true;
        // 检验用户名
        if (!userService.isValidUsername(user.getUsername()) || userService.isExist(user.getUsername())) {
            pass = false;
        }
        // 检验真实姓名
        if (pass && !userService.isValidRealName(user.getRealName())) {
            pass = false;
        }
        // 检验密码
        if (pass && !userService.isValidPassword(user.getPassword(), rePassword)) {
            pass = false;
        }
        // 若填写, 检验qq
        if (pass && user.getQQ() != null && !userService.isValidQQ(user.getQQ())) {
            pass = false;
        }
        // 若填写, 检验描述
        if (pass && user.getDescription() != null && !userService.isValidDescription(user.getDescription())) {
            pass = false;
        }

        if (pass) {
            int effect = userService.insert(user);
            return new Result<>(effect, "添加成功", true, 200);
        } else {
            return new Result<>(0, "信息有误", false, 500);
        }
    }

    @RequestMapping("/checkDuplicateUsername")
    public Result<Integer> checkDuplicateUsername(@RequestParam("username") String username) {
        if (!userService.isValidUsername(username)) {
            return new Result<>(0, "用户名不合法", false, 500);
        }
        if (userService.isExist(username)) {
            return new Result<>(0, "username已存在", false, 500);
        } else {
            return new Result<>(1, "username可以注册", true, 200);
        }
    }

    @RequestMapping("/delete")
    public Result<Integer> deleteUsers(@RequestParam("ids") List<Integer> ids) {
        if (ids == null) {
            return new Result<>(0, "参数不合法", false, 500);
        }
        int effect = userService.fakeDeleteByUserId(ids);
        if (effect > 0) {
            return new Result<>(effect, "删除成功", true, 200);
        } else {
            return new Result<>(effect, "删除失败", true, 200);
        }
    }

    @RequestMapping("/getUserEditInfo")
    public Result<UserEditVO> getUserEditInfo(@RequestParam("userId") Integer userId) {
        if (userId == null) {
            return new Result<>(null, "userId不合法", false, 500);
        }
        User user = userService.selectByUserId(userId);
        if (user == null) {
            return new Result<>(null, "无法获取用户信息", false, 500);
        } else {
            return new Result<>(getUserEditVO(user), "获取成功", true, 200);
        }
    }

    @RequestMapping("/list")
    public Result<List<UserVO>> selectUsersByPage(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit) {
        List<User> users = userService.selectByPage(page, limit);
        if (users == null) {
            return new Result<>(null, "获取用户列表失败", false, 500);

        }
        long rows = userService.getTotalRows();

        return new Result<>(getUserVOs(users), "获取用户列表成功", true, 200, (int) rows);
    }

    /**
     * 获取机构下所有员工
     *
     * @param orgId 机构id
     * @return 员工VO数组
     */
    @RequestMapping("/listByOrg")
    public Result<List<UserVO>> selectUsersByOrgId(@RequestParam("orgId") Integer orgId) {
        List<User> users = userService.selectByOrgId(orgId);
        if (users == null) {
            return new Result<>(null, "获取用户列表失败", false, 500);
        }
        return new Result<>(getUserVOs(users), "获取用户列表成功", true, 200, users.size());
    }

    @RequestMapping("/listByOrgByPage")
    public Result<List<UserVO>> selectUsersByOrgIdByPage(@RequestParam("orgId") Integer orgId,
                                                         @RequestParam("page") Integer page,
                                                         @RequestParam("limit") Integer limit) {
        List<User> users = userService.selectByOrgIdByPage(orgId, page, limit);
        if (users == null) {
            return new Result<>(null, "获取用户列表失败", false, 500);
        }
        long rows = userService.getTotalRows(orgId);
        return new Result<>(getUserVOs(users), "获取用户列表成功", true, 200, (int) rows);
    }

    @RequestMapping("/update")
    public Result<Integer> updateUser(User user, @RequestParam("rePassword") String rePassword) {
        if (user == null) {
            return new Result<>(0, "获取参数失败", false, 500);
        }
        if (user.getUserId() == null) {
            return new Result<>(0, "无法获取用户id", false, 500);
        }
        if (!userService.isExist(user.getUsername())) {
            return new Result<>(0, "用户不存在", false, 500);
        }

        // 检验数据合法性
        boolean pass = true;
        if (user.getRealName() != null && !userService.isValidRealName(user.getRealName())) { // 如果修改真实姓名, 检验之
            pass = false;
        }
        if (pass && user.getPassword() != null && !userService.isValidPassword(user.getPassword(), rePassword)) { // 如果修改密码, 检验之
            pass = false;
        }
        if (pass && user.getOrgId() != null && !organizationService.isExist(user.getOrgId())) { // 如果修改机构, 检验机构是否存在
            pass = false;
        }
        if (pass && user.getPhone() != null && !userService.isValidPhone(user.getPhone())) { // 如果修改手机号, 检验之
            pass = false;
        }
        if (pass && user.getQQ() != null && !userService.isValidQQ(user.getQQ())) { // 如果修改QQ, 检验之
            pass = false;
        }
        if (pass && user.getDescription() != null && !userService.isValidDescription((user.getDescription()))) { // 如果修改描述, 检验之
            pass = false;
        }

        if (pass) {
            int effect = userService.updateUserByUserId(user);
            return new Result<>(effect, "修改成功", true, 200);
        } else {
            return new Result<>(0, "修改失败", true, 200);
        }
    }

    private UserEditVO getUserEditVO(User user) {
        UserEditVO userEditVO = new UserEditVO();
        userEditVO.setUserId(user.getUserId());
        userEditVO.setUsername(user.getUsername());
        userEditVO.setRealName(user.getRealName());
        userEditVO.setPhone(user.getPhone());
        userEditVO.setQq(user.getQQ());
        userEditVO.setDescription(user.getDescription());

        // 获取机构信息
        Organization org = organizationService.selectByOrgId(user.getOrgId());
        userEditVO.setOrgName(org.getName());

        // 获取省市区
        Map<String, Object> map = provinceCityAreaService.getProvinceCityArea(org.getAreaId());
        Province province = (Province) map.get("province");
        City city = (City) map.get("city");
        Area area = (Area) map.get("area");

        userEditVO.setOrgProvinceId(Integer.valueOf(province.getProvinceId()));
        userEditVO.setOrgCityId(Integer.valueOf(city.getCityId()));
        userEditVO.setOrgAreaId(Integer.valueOf(area.getAreaId()));

        userEditVO.setOrgProvince(province.getProvince());
        userEditVO.setOrgCity(city.getCity());
        userEditVO.setOrgArea(area.getArea());

        return userEditVO;
    }

    private UserVO getUserVO(User user) {
        UserVO userVO = new UserVO();
        userVO.setUserId(user.getUserId());
        userVO.setUsername(user.getUsername());
        userVO.setRealName(user.getRealName());
        userVO.setPhone(user.getPhone());
        userVO.setQq(user.getQQ());
        userVO.setDescription(user.getDescription());

        // 获取机构信息
        Organization org = organizationService.selectByOrgId(user.getOrgId());
        userVO.setOrgName(org.getName());

        return userVO;
    }

    private List<UserVO> getUserVOs(List<User> users) {
        List<UserVO> userVOS = new ArrayList<>();
        for (User user : users) {
            userVOS.add(getUserVO(user));
        }
        return userVOS;
    }

}
