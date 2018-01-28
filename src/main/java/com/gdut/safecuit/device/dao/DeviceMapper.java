package com.gdut.safecuit.device.dao;

import com.gdut.safecuit.common.Page;
import com.gdut.safecuit.common.base.BaseDao;
import com.gdut.safecuit.device.common.po.Device;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceMapper extends BaseDao<Device> {
    //假删除操作
    @Update("update device set del_tag=1 where id = #{id}")
    int fakeDelete(Integer id);

    //获取某设备的假删除标识
    @Results({
            @Result(property = "delTag" ,column = "del_tag")
    })
    @Select("select del_tag from device where id = #{id}")
    int selectDelTag(Integer id);

    //分页查询
    List<Device> selectByPage(@Param("code") String code , @Param("typeId") Integer typeId,
                              @Param("page") Page page , @Param("electricBoxId") Integer electricBoxId);

    @Select("select count(*) from device where del_tag = 0 and electric_box_id = #{electricBoxId}")
    int getTotalByElectricBoxId(Integer electricBoxId);

    //修改设备在线状态
    @Update("update device set isOnline = #{isOnline} where id = #{id}")
    int updateIsOnline(@Param("id") Integer id , @Param("isOnline") int isOnline);

    List<Integer> selectDeviceIdByElectricBoxId(@Param("electricBoxId")Integer electricBoxId);

}