package com.gdut.safecuit.device.dao;

import com.gdut.safecuit.common.Page;
import com.gdut.safecuit.common.base.BaseDao;
import com.gdut.safecuit.device.common.po.ElectricUser;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ElectricUserMapper extends BaseDao<ElectricUser> {

    int relateUser(@Param("electricBoxId")Integer electricBoxId
            , @Param("userIds")List<Integer> userId
            , @Param("date")Date date);

    @Delete("delete from electric_user where electric_box_id = #{electricBoxId} and user_id = #{userId}")
    int deleteRelateUser(@Param("electricBoxId")Integer electricBoxId ,@Param("userId")Integer userId);

    @Results({
            @Result(property = "userId" ,column = "user_id")
    })
    @Select("select user_id from electric_user where electric_box_id = #{electricBoxId} and user_id = #{userId}")
    Integer hasRelatedUser(@Param("electricBoxId")Integer electricBoxId ,@Param("userId")Integer userId);

    @Select("select count(*) from electric_user where electric_box_id = #{electricBoxId}")
    Integer getRelatedUserTotal(@Param("electricBoxId")Integer electricBoxId);

    List<ElectricUser> selectByElectricBoxId(@Param("electricBoxId")Integer electricBoxId
            ,@Param("page")Page page);

}