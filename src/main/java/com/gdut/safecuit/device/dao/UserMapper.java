package com.gdut.safecuit.device.dao;

import com.gdut.safecuit.common.base.BaseDao;
import com.gdut.safecuit.device.po.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * Created by Garson in 9:44 2018/1/12
 * Description :
 */
@Repository
public interface UserMapper extends BaseDao<User> {

	@Select("select * from t_student where id = 2")
	public User select();

	String selectUser(int id);

}
