package com.gdut.safecuit.user.service;

import com.gdut.safecuit.common.util.Pbkdf2Util;
import com.gdut.safecuit.common.util.StringUtil;
import com.gdut.safecuit.user.common.po.User;
import com.gdut.safecuit.user.common.po.example.UserExample;
import com.gdut.safecuit.user.dao.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import static com.gdut.safecuit.user.common.util.UserConstant.*;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public boolean isExist(String username) {
        return username != null && selectUserByUsername(username) != null;
    }

    public boolean isExist(Integer userId) {
        return userId != null && selectUserByUserId(userId) != null;
    }

    public boolean checkLogin(User user) {
        if (user == null || user.getUsername() == null || user.getPassword() == null) {
            return false;
        }
        User correct = selectUserByUsername(user.getUsername());
        if (correct == null) {
            return false;
        }
        boolean pass = false;
        try {
            pass = Pbkdf2Util.validatePassword(user.getPassword(), correct.getPassword());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return pass;
    }

    public int fakeDeleteByUserId(Integer userId) {
        if (userId == null) {
            return -1;
        }
        User user = new User();
        user.setUserId(userId);
        user.setDelTag(1);
        return userMapper.updateByPrimaryKeySelective(user);
    }

    public int fakeDeleteByUsername(String username) {
        if (username == null) {
            return -1;
        }
        // 设定修改的信息
        User user = new User();
        user.setDelTag(1);
        // 设定修改对象
        UserExample userExample = new UserExample();
        userExample.or()
                .andUsernameEqualTo(username);
        return userMapper.updateByExampleSelective(user, userExample);
    }

//    @Transactional
//    public int fakeDeleteUsers(int[] userIds) {
//        if (userIds == null) {
//            return -1;
//        }
//        int effect = 0;
//        for (int userId : userIds) {
//            effect += fakeDeleteByUserId(userId);
//        }
//        return effect;
//    }

    @Transactional
    public int fakeDeleteUsers(List<Integer> userIds) {
        if (userIds == null) {
            return -1;
        }
        int effect = 0;
        for (int userId : userIds) {
            effect += fakeDeleteByUserId(userId);
        }
        return effect;
    }

    public int deleteByUserId(Integer userId) {
        if (userId == null) {
            return -1;
        }
        return userMapper.deleteByPrimaryKey(userId);
    }

    public int deleteByUsername(String username) {
        if (username == null) {
            return -1;
        }
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
        if (user == null) {
            return -1;
        }
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

    public List<User> selectUsersByPage(Integer page, Integer limit) {
        if (page == null || page < 0 || limit == null || limit < 0) {
            return null;
        }
        int offset = (page - 1) * limit;
        UserExample userExample = new UserExample();
        userExample.setLimit(limit);
        userExample.setOffset(offset);
        userExample.or()
                .andDelTagEqualTo(0);
        return userMapper.selectByExample(userExample);
    }

    public List<User> selectUsersByOrgId(Integer orgId) {
        if (orgId == null) {
            return null;
        }
        UserExample example = new UserExample();
        example.or()
                .andOrgIdEqualTo(orgId)
                .andDelTagEqualTo(0);
        return userMapper.selectByExample(example);
    }

    public List<User> selectUsersByOrgIdByPage(Integer orgId, Integer page, Integer limit) {
        if (orgId == null || page == null || page < 0 || limit == null || limit < 0) {
            return null;
        }
        int offset = (page - 1) * limit;
        UserExample userExample = new UserExample();
        userExample.setLimit(limit);
        userExample.setOffset(offset);
        userExample.or()
                .andOrgIdEqualTo(orgId)
                .andDelTagEqualTo(0);
        return userMapper.selectByExample(userExample);
    }

    public long getTotalRows() {
        UserExample userExample = new UserExample();
        userExample.or()
                .andDelTagEqualTo(0);
        return userMapper.countByExample(userExample);
    }

    public long getTotalRows(Integer orgId) {
        if (orgId == null) {
            return 0;
        }
        UserExample userExample = new UserExample();
        userExample.or()
                .andOrgIdEqualTo(orgId)
                .andDelTagEqualTo(0);
        return userMapper.countByExample(userExample);
    }

    public User selectUserByUserId(Integer userId) {
        if (userId == null) {
            return null;
        }
        User user = userMapper.selectByPrimaryKey(userId);
        return user.getDelTag() == 1 ? null : user;
    }

    public User selectUserByUsername(String username) {
        if (username == null) {
            return null;
        }
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
        if (user == null) {
            return -1;
        }
        //设置禁止修改项
        user.setUsername(null);
        user.setDelTag(null);
        return userMapper.updateByPrimaryKeySelective(user);
    }

    public boolean isValidInfo(User user) {
        return user != null
                && isValidUsername(user.getUsername())
                && isValidRealName(user.getRealName())
                && isValidDescription(user.getDescription());
    }

    public boolean isValidUsername(String username) {
        return StringUtil.isString(username, getUsernameMinLength(), getUsernameMaxLength());
    }

    public boolean isValidRealName(String realName) {
        return StringUtil.isString(realName, getRealNameMinLength(), getRealNameMaxLength());
    }

    public boolean isValidDescription(String desc) {
        return StringUtil.isString(desc, getDescriptionMinLength(), getDescriptionMaxLength());
    }

    public boolean isValidPassword(String pass, String rePass) {
        return StringUtil.isString(pass, getPasswordMinLength(), getPasswordMaxLength()) && pass.equals(rePass);
    }

    public boolean isValidQQ(Integer qq) {
        return false;
    }
}
