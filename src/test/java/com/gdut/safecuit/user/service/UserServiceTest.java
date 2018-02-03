package com.gdut.safecuit.user.service;

import com.gdut.safecuit.BaseTest;
import com.gdut.safecuit.user.common.po.User;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

public class UserServiceTest extends BaseTest {

    @Resource
    private UserService userService;

    @Test
    public void deleteUserByUserId() {
        userService.deleteByUserId(5);
    }

    @Test
    public void deleteUserByUsername() {
        userService.deleteByUsername("username8");
    }

    @Test
    public void insert() {
        for (int i = 0; i < 13; i++) {
            User user = new User();
            user.setUsername("red_tea" + i);
            user.setRealName("真实姓名" + i);
            user.setPassword("123456");
            user.setOrgId(90);
            userService.insert(user);
        }
    }

    @Test
    public void selectUsersByPage() {
        System.out.println();
        List<User> users = userService.selectByPage(0, 10);
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void selectUserByUserId() {
        System.out.println(userService.selectByUserId(1));
    }

    @Test
    public void selectUserByUsername() {
        System.out.println(userService.selectByUsername("cccccc12"));
    }

    @Test
    public void isExist() {
        System.out.println(userService.isExist("zkyyo"));
        System.out.println(userService.isExist("username3"));
    }

    @Test
    public void checkLogin() {
        User user = new User();
        user.setUsername("zkyyo");
        user.setPassword("qaws");
//        System.out.println(userService.checkLogin(user));
        user.setPassword("zzzzz");
//        System.out.println(userService.checkLogin(user));
    }

    @Test
    public void fakeDeleteByUserId() {
        userService.fakeDeleteByUserId(7);
    }

    @Test
    public void fakeDeleteByUsername() {
        userService.fakeDeleteByUsername("username10");
    }

    @Test
    public void updateUserByUserId() {
        User user = new User();
        user.setUserId(2);
        user.setUsername("aaaa2222");
        user.setRealName("bbbb2222");
        user.setPassword("aaaaa");
        user.setPhone("123456675");
        user.setQq(34553435);
        user.setDescription("desc2222");
        user.setDelTag(8);
        userService.updateUserByUserId(user);
    }

    @Test
    public void nothing() {
        System.out.println(Long.MAX_VALUE);
    }

}