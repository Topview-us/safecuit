package com.gdut.safecuit.device.dao;

import com.gdut.safecuit.common.Page;
import com.gdut.safecuit.common.base.BaseDao;
import com.gdut.safecuit.device.common.po.Device;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DeviceMapper extends BaseDao<Device> {

    int deleteByPrimaryKey(String id);

    Device selectByPrimaryKey(String id);

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
    int getTotal();

    /**
     * 通过电箱id来分页显示相关设备编号或监控类型的设备
     * @param map map，“page”对应Page对象，“electricBoxId”对应电箱id,“code”对应设备编号，“typeId”对应设备监控类型
     * @return 设备对象集合
     */
    @Results({
            @Result(property = "electricBoxId" ,column = "electric_box_id"),
            @Result(property = "typeId",column = "type_id"),
            @Result(property = "delTag",column = "del_tag"),
            @Result(property = "temperatureValue",column = "temperature_value"),
            @Result(property = "isOnline",column = "is_online")
    })
    @SelectProvider(type = DeviceMapperProvider.class ,method = "selectByPage")
    List<Device> selectByPage(Map<String ,Object> map);

    //基于注解的动态sql内部类，使用@SelectProvider、@InsertProvider、@UpdateProvider、@DeleteProvider等注解
    class DeviceMapperProvider{

        public String selectByPage(@Param("code") String code , @Param("typeId") Integer typeId,
                                   @Param("page") Page page , @Param("electricBoxId") String electricBoxId){
            String sql = "select * from device where electric_box_id = '" + electricBoxId + "'";

            if(code != null)
                sql += " and code = " + code;

            if(typeId != 0)
                sql += " and type_id = " + typeId;

            sql += " limit " + page.getPageNo() + " ," + page.getPageSize();

            return sql;
        }

    }

    //获取设备对应电箱的地址与名称,机构表还未出，待改
    Map<String ,String> searchElectricBox(String electricBoxId);

}