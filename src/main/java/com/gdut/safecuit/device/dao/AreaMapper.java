package com.gdut.safecuit.device.dao;

import com.gdut.safecuit.common.base.BaseDao;
import com.gdut.safecuit.device.common.po.Area;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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
	List<Area> select(@Param("cityId") int cityId);

	//基于注解的动态sql内部类，使用@SelectProvider、@InsertProvider、@UpdateProvider、@DeleteProvider等注解
	class AreaMapperProvider{
		public String select(Map<String ,Integer> map){
			String sql = "select * from area";
			if(map.get("cityId") != 0)
				sql += " where city_id = "+map.get("cityId");
			return sql;
		}
	}
}
