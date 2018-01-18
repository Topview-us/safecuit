package com.gdut.safecuit.device.dao;

import com.gdut.safecuit.common.Page;
import com.gdut.safecuit.common.base.BaseDao;
import com.gdut.safecuit.device.po.Device;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Garson in 9:43 2018/1/18
 * Description : 设备映射
 */
@Repository("deviceMapper")
public interface DeviceMapper extends BaseDao<Device> {

	@Results({
			@Result(property = "electricBoxId" ,column = "electric_box_id"),
			@Result(property = "typeId",column = "type_id")
	})
	@Select("select * from device where id = #{id}")
	Device selectOneById(String id);

	//分页
	@Select("select * from device limit #{page.index},#{page.pageSize}")
	List<Device> selectByPage(@Param("page") Page page);

	@Insert("insert into device(id,code,name,address,electric_box_id,type_id,del_tag) " +
			"values(#{id},#{code},#{name},#{address},#{electricBoxId},#{typeId},#{delTag})")
	int insert(Device device);

	@Update("update device set code=#{code},name=#{name},address=#{address},electric_box_id=#{electricBoxId}," +
			"type_id=#{typeId},del_tag=#{delTag} where id=#{id}")
	int update(Device device);

	@Delete("delete from device where id = #{id}")
	int deleteById(String id);

	//假删除操作
	@Update("update device set del_tag=1 where id = #{id}")
	int fakeDelete(String id);

	@Select("select count(*) from device")
	int getDeviceTotal();

	//获取某设备的假删除标识
	@Results({
			@Result(property = "delTag" ,column = "del_tag")
	})
	@Select("select del_tag from device where id = #{id}")
	int selectDelTag(String id);
}
