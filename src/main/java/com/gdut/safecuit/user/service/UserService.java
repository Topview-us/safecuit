package com.gdut.safecuit.user.service;

import com.gdut.safecuit.common.util.Pbkdf2Util;
import com.gdut.safecuit.user.common.po.User;
import com.gdut.safecuit.user.common.po.example.UserExample;
import com.gdut.safecuit.user.dao.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public boolean isExist(String username) {
        return selectUserByUsername(username) != null;
    }

    public boolean isExist(int userId) {
        return selectUserByUserId(userId) != null;
    }

//    public boolean checkLogin(User user) {
//        User correct = selectUserByUsername(user.getUsername());
//        if (correct == null) {
//            return false;
//        }
//        boolean pass = false;
//        try {
//            pass = Pbkdf2Util.validatePassword(user.getPassword(), correct.getPassword());
//        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
//            e.printStackTrace();
//        }
//        return pass;
//    }

    public int fakeDeleteByUserId(int userId) {
        User user = new User();
        user.setUserId(userId);
        user.setDelTag(1);
        return userMapper.updateByPrimaryKeySelective(user);
    }

    public int fakeDeleteByUsername(String username) {
        // 设定修改的信息
        User user = new User();
        user.setDelTag(1);
        // 设定修改对象
        UserExample userExample = new UserExample();
        userExample.or()
                .andUsernameEqualTo(username);
        return userMapper.updateByExampleSelective(user, userExample);
    }

    @Transactional
    public int fakeDeleteUsers(int[] userIds) {
        int effect = 0;
        for (int userId : userIds) {
            effect += fakeDeleteByUserId(userId);
        }
        return effect;
    }

    public int deleteByUserId(int userId) {
        return userMapper.deleteByPrimaryKey(userId);
    }

    public int deleteByUsername(String username) {
        UserExample userExample = new UserExample();
        userExample.or()
                .andUsernameEqualTo(username);
        return userMapper.deleteByExample(userExample);
    }

    /**
     * 添加 user
     *
     * @param user user 对象
     * @return user 的主键
     */
    public int insert(User user) {
        try {
            String hashPsw = Pbkdf2Util.createHash(user.getPassword());
            user.setDelTag(0);
            user.setPassword(hashPsw);
            userMapper.insertSelective(user);
            return user.getUserId();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return 0;
    }

//    public List<User> selectUsersByPage(int offset, int limit) {
//        UserExample userExample = new UserExample();
//        userExample.setLimit(limit);
//        userExample.setOffset(offset);
//        userExample.or().andDelTagEqualTo(0);
//        return userMapper.selectByExample(userExample);
//    }

    public List<User> selectUsersByPage(int page, int limit) {
        if (page < 0 || limit < 0) {
            return new ArrayList<>();
        }
        int offset = (page - 1) * limit;
        UserExample userExample = new UserExample();
        userExample.setLimit(limit);
        userExample.setOffset(offset);
        userExample.or()
                .andDelTagEqualTo(0);
        return userMapper.selectByExample(userExample);
    }

    public List<User> selectUsersByOrgId(int orgId) {
        UserExample example = new UserExample();
        example.or()
                .andOrgIdEqualTo(orgId)
                .andDelTagEqualTo(0);
        return userMapper.selectByExample(example);
    }

    public int getTotalPge(int limit) {
        UserExample userExample = new UserExample();
        userExample.or()
                .andDelTagEqualTo(0);
        long rows = userMapper.countByExample(userExample);
        int page = (int) rows / limit;
        return rows % limit == 0 ? page : page + 1;
    }

    public User selectUserByUserId(int userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        return user.getDelTag() == 1 ? null : user;
    }

    public User selectUserByUsername(String username) {
        UserExample userExample = new UserExample();
        userExample.or()
                .andUsernameEqualTo(username);
        List<User> users = userMapper.selectByExample(userExample);
//        return users.size() == 0 ? null : users.get(0);
        if (users.size() == 0) {
            return null;
        }
        return users.get(0).getDelTag() == 1 ? null : users.get(0);
    }

    public int updateUserByUserId(User user) {
        //设置禁止修改项
        user.setUsername(null);
        user.setDelTag(null);
        return userMapper.updateByPrimaryKeySelective(user);
    }
}
