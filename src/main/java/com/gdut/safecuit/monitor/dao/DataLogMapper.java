package com.gdut.safecuit.monitor.dao;

import com.gdut.safecuit.common.Page;
import com.gdut.safecuit.common.base.BaseDao;
import com.gdut.safecuit.monitor.common.po.DataLog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DataLogMapper extends BaseDao<DataLog>{

    @Select("select * from data_log_current where device_id = #{deviceId} and circuit_no = #{circuitNo}")
    DataLog selectByDeviceIdInCurrentTable(@Param("deviceId") Integer deviceId
            ,@Param("circuitNo")Integer circuitNo);

    @Select("select * from data_log_mili_current where device_id = #{deviceId} and circuit_no = #{circuitNo}")
    DataLog selectByDeviceIdInMiliCurrentTable(@Param("deviceId") Integer deviceId
            ,@Param("circuitNo")Integer circuitNo);

    @Select("select * from data_log_temperature where device_id = #{deviceId} and circuit_no = #{circuitNo}")
    DataLog selectByDeviceIdInTemperatureTable(@Param("deviceId") Integer deviceId
            ,@Param("circuitNo")Integer circuitNo);

    //获取历史消息
    List<DataLog> selectHistoryData(@Param("deviceId")Integer deviceId , @Param("typeId")Integer typeId
            , @Param("startDate")Date startDate , @Param("endDate")Date endDate
            , @Param("page")Page page);


    int getTotalByTypeIdAndDeviceId(@Param("typeId")Integer typeId ,@Param("deviceId")Integer deviceId);
}