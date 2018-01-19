package com.gdut.safecuit.device.dao;

import com.gdut.safecuit.common.Page;
import com.gdut.safecuit.common.base.BaseDao;
import com.gdut.safecuit.device.common.po.Device;
import com.gdut.safecuit.device.common.po.ElectricBox;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Garson in 15:23 2018/1/19
 * Description :
 */
@Repository
public interface ElectricBoxMapper extends BaseDao<ElectricBox> {

	@Results({
			@Result(property = "orgId",column = "org_id"),
			@Result(property = "delTag" ,column = "del_tag")
	})
	@Select("select * from electric_box where id = #{id}")
	ElectricBox selectOneById(String id);

	@Results({
			@Result(property = "orgId" ,column = "org_id"),
			@Result(property = "delTag" ,column = "del_tag")
	})
	@Select("select * from device where name = #{name}")
	ElectricBox selectOneByName(String name);

	@Insert("insert into electric_box(id,name,address,org_id,longitude ,latitude ,del_tag) " +
			"values(#{id},#{name},#{address},#{orgId},#{longitude} ,#{latitude},#{delTag})")
	int insert(Device device);

	@Update("update electric_box set name=#{name},address=#{address},org_id=#{orgId},longitude=#{longitude}," +
			"latitude=#{latitude},del_tag=#{delTag} where id=#{id}")
	int update(Device device);

	@Delete("delete from electric_box where id = #{id}")
	int deleteById(String id);

	//假删除操作
	@Update("update electric_box set del_tag=1 where id = #{id}")
	int fakeDelete(String id);

	//获取某设备的假删除标识
	@Results({
			@Result(property = "delTag" ,column = "del_tag")
	})
	@Select("select del_tag from electric_box where id = #{id}")
	int selectDelTag(String id);

	@Results({
			@Result(property = "orgId" ,column = "org_id"),
			@Result(property = "delTag" ,column = "del_tag")
	})
	@SelectProvider(type = ElectricBoxMapperProvider.class ,method = "selectByPage")
	List<ElectricBox> selectByPage(Map<String ,Object> map);

	@Select("select count(*) from electric_box")
	int getElectricBoxTotal();

	//基于注解的动态sql内部类，使用@SelectProvider、@InsertProvider、@UpdateProvider、@DeleteProvider等注解
	class ElectricBoxMapperProvider{
		public String selectByPage(@Param("page") Page page , @Param("orgId") int orgId){
			String sql = "select * from electric_box";
			if(orgId != 0)
				sql += " where org_id = "+orgId;
			sql+= " limit " + page.getIndex() +","+page.getPageSize();
			return sql;
		}
	}

	

}
