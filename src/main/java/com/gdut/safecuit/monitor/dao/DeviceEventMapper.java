package com.gdut.safecuit.monitor.dao;

import com.gdut.safecuit.common.Page;
import com.gdut.safecuit.common.base.BaseDao;
import com.gdut.safecuit.monitor.common.po.DeviceEvent;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceEventMapper extends BaseDao<DeviceEvent> {

    int updateByPrimaryKeyWithBLOBs(DeviceEvent record);

    /**
     * 根据处理状态搜索对应的设备id
     * @param type 处理状态
     * @return 设备id集合
     */
    @Select("select device_id from device_event where type = #{type}")
    List<Integer> selectDeviceIdByType(Integer type);

    List<DeviceEvent> selectAllByPage(@Param("page")Page page ,@Param("electricBoxId")Integer electricBoxId);

    /**
     * 获取电箱下设备的报警信息的总数
     * @param electricBoxId 电箱id
     * @return 返回电箱下设备的报警信息的总数
     */
    @Select("SELECT COUNT(*) FROM device_event de ,device d WHERE de.`device_id` = d.`id` AND d.`electric_box_id` = #{electricBoxId}  ")
    int getTotalByEelectricBoxId(@Param("electricBoxId")Integer electricBoxId);


}