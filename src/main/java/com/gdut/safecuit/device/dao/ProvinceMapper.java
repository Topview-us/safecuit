package com.gdut.safecuit.device.dao;

import com.gdut.safecuit.common.base.BaseDao;
import com.gdut.safecuit.device.po.Province;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Garson in 20:34 2018/1/18
 * Description :
 */
@Repository
public interface ProvinceMapper extends BaseDao<Province> {

	@Insert("insert into province(id ,name) values(#{id} ,#{name})")
	int insert(Province province);

	@Select("select * from province")
	List<Province> select();

	@Update("update province set name = #{name} where id = #{id}")
	int update(Province province);

	@Delete("delete from province where id = #{id}")
	int deleteById(Long id);
}
