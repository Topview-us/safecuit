package com.gdut.safecuit.user.web;

import com.gdut.safecuit.common.Result;
import com.gdut.safecuit.common.util.MatchUtil;
import com.gdut.safecuit.common.util.StringUtil;
import com.gdut.safecuit.user.common.po.User;
import com.gdut.safecuit.user.common.util.UserConstant;
import com.gdut.safecuit.user.service.UserService;
import org.springframework.web.bind.annotation.RequestBody;
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

    @RequestMapping("/add")
    public Result<Integer> addUser(@RequestBody User user) {
        if (user.getUsername() == null || user.getRealName() == null || user.getPassword() == null
                || user.getUserId() == null) {
            return new Result<>(0, "信息不能为空", false, 500);
        }
        //
        // 检验数据合法性
        //
        int effect = userService.insert(user);
        return new Result<>(effect, "添加成功", true, 200);
    }

    @RequestMapping("/delete")
    public Result<Integer> deleteUser(@RequestParam("username") String username) {
        if (!StringUtil.isString(username, UserConstant.getUsernameMinLength(), UserConstant.getUsernameMaxLength())) {
            return new Result<>(0, "用户名不正确", false, 500);
        }
        int effect = userService.fakeDeleteByUsername(username);
        return new Result<>(effect, "删除成功", true, 200);
    }

    @RequestMapping("/selectUsersByPage")
    public Result<List<User>> selectUsersByPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit) {
        List<User> users;
        if (offset == null || limit == null || offset <= 0 || limit <= 0) {
            users = new ArrayList<>();
            return new Result<>(users, "获取用户列表失败", false, 500);
        }
        users = userService.selectUsersByPage(offset, limit);
        return new Result<>(users, "获取用户列表成功", true, 200);
    }

    @RequestMapping("/update")
    public Result<Integer> updateUser(@RequestBody User user) {
        if (user.getUserId() == null) {
            return new Result<>(0, "用户id无效", false, 500);
        }
        if (!userService.isExist(user.getUsername())) {
            return new Result<>(0, "用户不存在", false, 500);
        }
        //
        // 检验数据合法性
        //
        int effect = userService.updateUserByUserId(user);
        return new Result<>(effect, "修改成功", true, 200);
    }
}
