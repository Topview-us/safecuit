package com.gdut.safecuit.device.dao;

import com.gdut.safecuit.common.base.BaseDao;
import com.gdut.safecuit.device.po.Area;
import com.gdut.safecuit.device.po.City;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Garson in 21:04 2018/1/18
 * Description :
 */
@Repository
public interface AreaMapper extends BaseDao<Area> {

	@Insert("insert into area(id ,name ,city_id) values(#{id} ,#{name} ,#{cityId})")
	int insert(Area area);

	@Update("update area set name = #{name}, city_id = #{cityId} where id = #{id}")
	int update(Area area);

	@Delete("delete from area where id = #{id}")
	int deleteById(Long id);

	@Results({
			@Result(property = "cityId" ,column = "city_id")
	})
	@SelectProvider(type = AreaMapperProvider.class ,method = "select")
	List<Area> select(int cityId);

	//基于注解的动态sql内部类，使用@SelectProvider、@InsertProvider、@UpdateProvider、@DeleteProvider等注解
	class AreaMapperProvider{
		public String select(int cityId){
			String sql = "select * from area";
			if(cityId != 0)
				sql += "where city_id = #{cityId}";
			return sql;
		}
	}
}
