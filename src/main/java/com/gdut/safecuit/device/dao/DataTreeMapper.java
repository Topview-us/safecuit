package com.gdut.safecuit.device.dao;

import com.gdut.safecuit.common.base.BaseDao;
import com.gdut.safecuit.device.common.po.DataTree;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataTreeMapper extends BaseDao<DataTree> {

    /**
     * 查询某个结点的父母id，用于添加结点操作时
     * @param typeInfo 结点的信息，包括电箱id和设备id
     * @return 结点父母id
     */
    @Select("select parent_id from data_tree where type_info = #{typeInfo}")
    int selectParentIdByTypeInfo(String typeInfo);

    /**
     * 查询某个结点的id，用于添加结点操作时
     * @param typeInfo 结点的信息，包括电箱id和设备id
     * @return 结点父母id
     */
    @Select("select id from data_tree where type_info = #{typeInfo}")
    int selectIdByTypeInfo(String typeInfo);


    /**
     * 通过父母结点id查询相关的数据，用于显示树的操作中
     * @param parentId 父母结点id
     * @return 数据树集合
     */
    @Results({
            @Result(column = "type_id" ,property = "typeId"),
            @Result(column = "type_info" ,property = "typeInfo"),
            @Result(column = "state_id" ,property = "stateId"),
            @Result(column = "parent_id" ,property = "parentId"),
            @Result(column = "role_id" ,property = "roleId")
    })
    @Select("select * from data_tree where parent_id = #{parentId} and role_id = #{roleId}")
    List<DataTree> selectByParentId(@Param("parentId") Integer parentId ,@Param("roleId") Integer roleId);

    @Select("select id from data_tree where name = #{name}")
    Integer selectIdByName(String name);

    /**
     * 判断添加的分组数据是否在所选分组下已有相同的分组名字，如果是则返回1，否则返回0，用于添加树操作中
     * @param name 分组名
     * @param parentId 父母结点id
     * @return 数据量
     */
    @Select("select count(*) from data_tree where name = #{name} and parentId = #{parentId}")
    int selectByNameAndParentId(@Param("name")String name ,@Param("parentId")Integer parentId);

    /**
     * 查询数据树表中的最后一条数据的主键id。在需要添加省市区分组后方便设置要添加的设备的父母id；用于添加“机构”结点时
     * @return 数据树表中的最后一条数据的主键id
     */
    @Select("SELECT id FROM data_tree ORDER BY id DESC LIMIT 1")
    int selectFinalDataId();

    /**
     * 用于删除某个分组时执行的update操作，将被删除的分组下属结点置于该分组的父母结点下属
     * @param id 被删除的分组id
     * @param parentId 分组的父母结点id
     * @return 修改数据量
     */
  /*  @Update("update data_tree set parent_id = #{parentId} where parent_id = #{id} ")
    int updateDataParent(@Param("id") Integer id ,@Param("parentId")Integer parentId);*/

    /**
     * 修改某结点的父母结点
     * @param id 结点id
     * @param parentId 父母结点id
     * @return 修改数
     */
   /* @Update("update data_tree set parent_id = #{parentId} where id = #{id}")
    int updateParentById(@Param("id") Integer id ,@Param("parentId")Integer parentId);

    @Update("update data_tree set parent_id = #{parentId} where type_Info = #{typeInfo}")
    int updateParentByTypeInfo(@Param("typeInfo") String typeInfo ,@Param("parentId")Integer parentId);*/
    /**
     * 判断父母结点是否有孩子，有则返回数据总数，用于添加结点操作中
     * @param parentId 父母结点id
     * @return 数据数
     */
    @Select("select count(*) from data_tree where parent_id = #{parentId}")
    int hasChild(Integer parentId);

    /**
     * 修改父母结点的展开状态，用于该父母结点无孩子的情况下，用于添加结点操作中
     * @param parentId 父母结点id
     * @return 修改数
     */
    @Update("update data_tree set state_id = 1 where id = #{parentId}")
    int updateParentState(Integer parentId);

    @Delete("delete from data_tree where type_info = #{typeInfo}")
    int deleteByTypeInfo(String typeInfo);

    /**
     * 显示分组的信息
     * @param typeId “分组”的类型id
     * @return “分组”的对象集合
     */
    @Results({
            @Result(column = "type_id" ,property = "typeId"),
            @Result(column = "type_info" ,property = "typeInfo"),
            @Result(column = "state_id" ,property = "stateId"),
            @Result(column = "parent_id" ,property = "parentId"),
            @Result(column = "role_id" ,property = "roleId")
    })
    @Select("select * from data_tree where type_id = #{typeId}")
    List<DataTree> selectGroup(int typeId);

    /**
     * 显示所有“机构”和“分组”的结点信息
     * @return “机构”和“分组”的结点信息集合
     */
    @Results({
            @Result(column = "type_id" ,property = "typeId"),
            @Result(column = "type_info" ,property = "typeInfo"),
            @Result(column = "state_id" ,property = "stateId"),
            @Result(column = "parent_id" ,property = "parentId"),
            @Result(column = "role_id" ,property = "roleId")
    })
    @Select("select * from data_tree where type_id = 4 or type_id = -1")
    List<DataTree> selectOrgOrGroup();

    /*int updateByTypeInfoSelective(DataTree dataTree);*/
}