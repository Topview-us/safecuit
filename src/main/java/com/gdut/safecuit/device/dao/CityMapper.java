package com.gdut.safecuit.device.dao;

import com.gdut.safecuit.common.base.BaseDao;
import com.gdut.safecuit.device.po.City;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Garson in 20:55 2018/1/18
 * Description :
 */
@Repository
public interface CityMapper extends BaseDao<City> {

	@Insert("insert into city(id ,name ,province_id) values(#{id} ,#{name} ,#{provinceId})")
	int insert(City city);

	@Update("update city set name = #{name}, province_id = #{provinceId} where id = #{id}")
	int update(City city);

	@Delete("delete from city where id = #{id}")
	int deleteById(Long id);

	@Results({
			@Result(property = "provinceId" ,column = "province_id")
	})
	@SelectProvider(type = CityMapperProvider.class ,method = "select")
	List<City> select(int provinceId);

	//基于注解的动态sql内部类，使用@SelectProvider、@InsertProvider、@UpdateProvider、@DeleteProvider等注解
	class CityMapperProvider{
		public String select(int provinceId){
			String sql = "select * from city";
			if(provinceId != 0)
				sql += "where province_id = #{provinceId}";
			return sql;
		}
	}

}
