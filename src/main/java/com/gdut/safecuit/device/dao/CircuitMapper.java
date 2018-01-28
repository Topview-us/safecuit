package com.gdut.safecuit.device.dao;

import com.gdut.safecuit.common.Page;
import com.gdut.safecuit.common.base.BaseDao;
import com.gdut.safecuit.device.common.po.Circuit;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CircuitMapper extends BaseDao<Circuit> {

    @Select("select id from circuit where name = #{name} and device_Id = #{deviceId}")
    Integer selectByNameAndDeviceId(@Param("name")String name , @Param("deviceId")Integer deviceId);

    List<Circuit> selectAllByPageAndDeviceId(@Param("page")Page page , @Param("deviceId")Integer deviceId);

    @Select("select count(*) from circuit where device_id = #{deviceId}")
    Integer selectCircuitCountByDeviceId(@Param("deviceId")Integer deviceId);

    /*@Select("select count()")
    int getTotal();*/
}