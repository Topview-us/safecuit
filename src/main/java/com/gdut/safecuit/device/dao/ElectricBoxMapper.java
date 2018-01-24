package com.gdut.safecuit.device.dao;

import com.gdut.safecuit.common.Page;
import com.gdut.safecuit.common.base.BaseDao;
import com.gdut.safecuit.device.common.po.ElectricBox;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ElectricBoxMapper extends BaseDao<ElectricBox> {

    @Select("select count(*) from electric_box")
    int getTotal();

    int deleteByPrimaryKey(String id);

    //假删除操作
    @Update("update electric_box set del_tag=1 where id = #{id}")
    int fakeDelete(String id);

    /**
     * 根据id查电箱信息
     * @param id 电箱id
     * @return 电箱对象
     */
    ElectricBox selectByPrimaryKey(String id);

    //获取某设备的假删除标识
    @Results({
            @Result(property = "delTag" ,column = "del_tag")
    })
    @Select("select del_tag from electric_box where id = #{id}")
    int selectDelTag(String id);

    /**
     * 搜索某机构下的对应电箱
     * @param map map，包括分页对象、机构id、电箱名称
     * @return 电箱集合
     */
    @Results({
            @Result(property = "orgId" ,column = "org_id"),
            @Result(property = "delTag" ,column = "del_tag")
    })
    @SelectProvider(type = ElectricBoxMapperProvider.class ,method = "selectByPage")
    List<ElectricBox> selectByPage(Map<String ,Object> map);

    //基于注解的动态sql内部类，使用@SelectProvider、@InsertProvider、@UpdateProvider、@DeleteProvider等注解
    class ElectricBoxMapperProvider{
        public String selectByPage(@Param("page") Page page ,@Param("orgId") int orgId ,@Param("name")String name){
            String sql = "select * from electric_box where org_id = " + orgId;
            if (name != null)
                sql += " and name = '" + name + "'";

            sql+= " limit " + page.getPageNo() +","+page.getPageSize();
            return sql;
        }
    }

    /**
     * 添加数据树或设备时下拉框显示的对应机构的电箱名称
     * @param orgId 机构id
     * @return 电箱名称集合
     */
    @Select("select name from electric_box where org_id = #{orgId}")
    List<String> selectNameByOrgId(Integer orgId);

    /**
     * 获取电箱对应机构的信息
     * @param orgId 机构id
     * @return 机构信息的map
     */
    Map<String ,String> searchOrgInfo(Integer orgId);
}