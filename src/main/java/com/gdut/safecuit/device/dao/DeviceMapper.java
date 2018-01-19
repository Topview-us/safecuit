package com.gdut.safecuit.device.dao;

import com.gdut.safecuit.common.Page;
import com.gdut.safecuit.common.base.BaseDao;
import com.gdut.safecuit.device.common.po.Device;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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

	@Insert("insert into device(id,code,name,electric_box_id,type_id,del_tag) " +
			"values(#{id},#{code},#{name},#{electricBoxId},#{typeId},#{delTag})")
	int insert(Device device);

	@Update("update device set code=#{code},name=#{name},electric_box_id=#{electricBoxId}," +
			"type_id=#{typeId},del_tag=#{delTag} where id=#{id}")
	int update(Device device);

	@Delete("delete from device where id = #{id}")
	int deleteById(String id);

	//假删除操作
	@Update("update device set del_tag=1 where id = #{id}")
	int fakeDelete(String id);

	//获取某设备的假删除标识
	@Results({
			@Result(property = "delTag" ,column = "del_tag")
	})
	@Select("select del_tag from device where id = #{id}")
	int selectDelTag(String id);

	//获取设备数据总数
	@Select("select count(*) from device")
	int getDeviceTotal();

	/**
	 * 通过电箱id来分页显示设备的sql语句，如果electric_box_id为0，则显示所有设备；如果不为0，则显示对应电箱设备，设备编号与设备监控类型同理
	 * @param map map，“page”对应Page对象，“electricBoxId”对应电箱id,“code”对应设备编号，“typeId”对应设备监控类型
	 * @return 设备对象集合
	 */
	@Results({
			@Result(property = "electricBoxId" ,column = "electric_box_id"),
			@Result(property = "typeId",column = "type_id"),
			@Result(property = "delTag",column = "del_tag")
	})
	@SelectProvider(type = DeviceMapperProvider.class ,method = "selectByPage")
	List<Device> selectByPage(Map<String ,Object> map);

	//基于注解的动态sql内部类，使用@SelectProvider、@InsertProvider、@UpdateProvider、@DeleteProvider等注解
	class DeviceMapperProvider{

		public String selectByPage(@Param("code")String code ,@Param("typeId")Integer typeId,
								   @Param("page") Page page ,@Param("electricBoxId") Integer electricBoxId){
			Boolean flag = false;
			StringBuilder sql = new StringBuilder("select * from device ");

			if(code!=null || typeId!=null || electricBoxId!=null)
				sql.append("where ");

			if(code != null){
				sql.append("code = '" + code +"' ");
				flag = true;
			}

			if(typeId != null){
				if(flag) {
					sql.append(" and type_id = " + typeId);
				} else{
					sql.append(" type_id = " + typeId);
					flag = true;
				}
			}

			if(electricBoxId != null){
				if(flag) {
					sql.append(" and electric_box_id = " + electricBoxId);
				}else {
					sql.append("electric_box_id = " + electricBoxId);
				}
			}

			sql.append(" limit " + page.getPageNo() + " ," + page.getPageSize());

			return sql.toString();
		}

	}

	//获取设备对应电箱的地址与名称,机构表还未出，待改
	@Results({
			@Result(property = "address",column = "address"),
			@Result(property = "name",column = "name")
	})
	@Select("SELECT CONCAT(p.name,c.name,a.name,o.`name`,eb.address) as address ,eb.name as name " +
			"FROM province p ,city c ,AREA a ,orginazation o ,electric_box eb " +
			"WHERE p.id = c.province_id AND c.id = a.city_id AND a.id = o.`area_id` " +
			"AND o.`id` = eb.org_id AND eb.id = #{electricBoxId};")
	Map<String ,String> searchElectricBox(int electricBoxId);
}
