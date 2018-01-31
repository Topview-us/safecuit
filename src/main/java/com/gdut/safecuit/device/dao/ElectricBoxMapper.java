package com.gdut.safecuit.device.dao;

import com.gdut.safecuit.common.Page;
import com.gdut.safecuit.common.base.BaseDao;
import com.gdut.safecuit.device.common.po.ElectricBox;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Repository
public interface ElectricBoxMapper extends BaseDao<ElectricBox> {

    @Insert("insert into electric_user(user_id ,electric_box_id) values(#{electricBoxId} ,#{userId})")
    int relateUser(@Param("electricBoxId")Integer electricBoxId ,@Param("userId")Integer userId);

    //假删除操作
    @Update("update electric_box set del_tag=1 where id = #{id}")
    int fakeDelete(Integer id);

    @Results({
            @Result(property = "userId" ,column = "user_id")
    })
    @Select("select user_id from electric_user where electric_box_id = #{electricBoxId}")
    List<Integer> selectUserIdByElectricBoxId(@Param("electricBoxId")Integer electricBoxId);

    //获取某设备的假删除标识
    @Results({
            @Result(property = "delTag" ,column = "del_tag")
    })
    @Select("select del_tag from electric_box where id = #{id}")
    int selectDelTag(Integer id);

    /**
     * 添加数据树或设备时下拉框显示的对应机构的电箱名称
     * @param orgId 机构id
     * @return 电箱名称集合
     */
    @Select("select name from electric_box where org_id = #{orgId}")
    List<String> selectNameByOrgId(Integer orgId);

    /**
     * 查找对应父母结点的电箱信息
     * @param parentId 父母结点id
     * @return 返回电箱信息
     */
    @Select("select * from electric_box where parent_id = #{parentId}")
    List<ElectricBox> selectByParentId(Integer parentId);

    /**
     * 搜索某电箱下的所有设备id
     * @param electricBoxId 电箱id
     * @return 设备id集合
     */
    @Select("select id from device where electric_box_id = #{electricBoxId}")
    List<Integer> selectAllDeviceByElectricBoxId(@Param("electricBoxId")Integer electricBoxId);

    /**
     * 分页查询
     * @param page
     * @param orgId
     * @param name
     * @return
     */
    List<ElectricBox> selectByPage(@Param("page") Page page , @Param("orgId") int orgId , @Param("name")String name);

    //电箱表模糊查询
    List<ElectricBox> selectElectricBoxByFuzzyQuery(@Param("name")String name);

    List<ElectricBox> selectByElectricId(@Param("electricBoxId")Integer electricBoxId);
}