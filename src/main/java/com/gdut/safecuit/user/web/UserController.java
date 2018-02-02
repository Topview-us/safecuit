package com.gdut.safecuit.user.web;

import com.gdut.safecuit.common.Result;
import com.gdut.safecuit.organization.common.po.Organization;
import com.gdut.safecuit.organization.service.OrganizationService;
import com.gdut.safecuit.user.common.po.User;
import com.gdut.safecuit.user.common.vo.LoginVO;
import com.gdut.safecuit.user.common.vo.UserVO;
import com.gdut.safecuit.user.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private OrganizationService organizationService;

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
//        if (user.getUsername() == null || user.getRealName() == null || user.getPassword() == null
//                || user.getUserId() == null || rePassword == null) {
//            return new Result<>(0, "信息不能为空", false, 500);
//        }

        // 校验数据正确性
        boolean pass = true;
        if (userService.isExist(user.getUsername())) { // 检验用户名是否重复
            pass = false;
        }
        if (pass && !userService.isValidPassword(user.getPassword(), rePassword)) { // 检查密码是否一致
            pass = false;
        }
        if (pass && !userService.isValidInfo(user)) { // 检查其他信息是否符合
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
        System.out.println("[检测] /user/checkDuplicateUsername: username " + username);
        if (userService.isExist(username)) {
            return new Result<>(0, "username已存在", false, 500);
        } else {
            return new Result<>(1, "username可以注册", true, 200);
        }
    }

//    @RequestMapping("/delete")
//    public Result<Integer> deleteUser(@RequestParam("username") String username) {
//        if (!StringUtil.isString(username, UserConstant.getUsernameMinLength(), UserConstant.getUsernameMaxLength())) {
//            return new Result<>(0, "用户名不正确", false, 500);
//        }
//        int effect = userService.fakeDeleteByUsername(username);
//        return new Result<>(effect, "删除成功", true, 200);
//    }

    @RequestMapping("/delete")
    public Result<Integer> deleteUsers(@RequestParam("ids") List<Integer> ids) {
        int effect = userService.fakeDeleteUsers(ids);
        if (effect > 0) {
            return new Result<>(effect, "删除成功", true, 200);
        } else {
            return new Result<>(effect, "删除失败", true, 200);
        }
    }

    @RequestMapping("/list")
    public Result<List<UserVO>> selectUsersByPage(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit) {
//        if (page == null || page < 0 || limit == null || limit < 0) {
//            return new Result<>(null, "获取用户列表失败", false, 500);
//        }
        List<User> users = userService.selectUsersByPage(page, limit);
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
//        if (orgId == null) {
//            return new Result<>(null, "orgId无效", false, 500);
//        }
        List<User> users = userService.selectUsersByOrgId(orgId);
        if (users == null) {
            return new Result<>(null, "获取用户列表失败", false, 500);
        }
        return new Result<>(getUserVOs(users), "获取用户列表成功", true, 200, users.size());
    }

    @RequestMapping("/listByOrgByPage")
    public Result<List<UserVO>> selectUsersByOrgIdByPage(@RequestParam("orgId") Integer orgId,
                                                         @RequestParam("page") Integer page,
                                                         @RequestParam("limit") Integer limit) {
//        if (orgId == null || page == null || page < 0 || limit == null || limit < 0) {
//            return new Result<>(null, "获取用户列表失败", false, 500);
//        }
        List<User> users = userService.selectUsersByOrgIdByPage(orgId, page, limit);
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
            return new Result<>(0, "用户id无效", false, 500);
        }
        if (!userService.isExist(user.getUsername())) {
            return new Result<>(0, "用户不存在", false, 500);
        }
        // 检验数据合法性
        boolean pass = true;
        if (user.getRealName() != null && !userService.isValidRealName(user.getRealName())) { // 检验真实姓名
            pass = false;
        }
        if (pass && user.getPassword() != null && !userService.isValidPassword(user.getPassword(), rePassword)) { // 检验密码
            pass = false;
        }
        if (pass && user.getOrgId() != null && !organizationService.isExist(user.getOrgId())) {
            pass = false;
        }
//        if (pass && user.getPhone() != null && !userService.isValidPhone(user.getPhone())) {
//            pass = false;
//        }
        if (pass && user.getQQ() != null && !userService.isValidQQ(user.getQQ())) {
            pass = false;
        }
        if (pass && user.getDescription() != null && !userService.isValidDescription((user.getDescription()))) {
            pass = false;
        }

        if (pass) {
            int effect = userService.updateUserByUserId(user);
            return new Result<>(effect, "修改成功", true, 200);
        } else {
            return new Result<>(0, "修改失败", true, 200);
        }
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
        Organization org = organizationService.selectOrganizationByOrgId(user.getOrgId());
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
