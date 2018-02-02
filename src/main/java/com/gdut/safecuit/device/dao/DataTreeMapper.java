package com.gdut.safecuit.device.dao;

import com.gdut.safecuit.common.base.BaseDao;
import com.gdut.safecuit.device.common.po.DataTree;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataTreeMapper extends BaseDao<DataTree> {

	/**
	 * 向上查询结点，以判断orgId是否为空
	 * @param parentId 父母id
	 * @return 分组结点
	 */
	/*@Select("select * from data_tree where id = #{parentId}")
	DataTree selectByParentIdInGroup(Integer parentId);*/

	DataTree selectByNameAndParentId(@Param("name") String name ,@Param("parentId")Integer parentId);

 	/**
	 * 向下查找各结点
	 * @param parentId 父母结点id
	 * @return 返回下一层的结点
	 */
	List<DataTree> selectByParentId(@Param("parentId")Integer parentId);

	/**
	 * 根据id搜索父母结点id
	 * @param id 结点id
	 * @param typeId 类型
	 * @return 父母结点id
	 */
	Integer selectParentIdById(@Param("id")Integer id ,@Param("typeId")Integer typeId);

	/**
	 * 根据父母结点id查询是否存在孩子
	 * @param parentId 父母结点id
	 * @param type 判别查询表的标识，1为分组表，2为机构表，3为电箱表
	 * @return 返回其中一个孩子结点的id
	 */
	Integer hasChild(@Param("parentId")Integer parentId ,@Param("type")Integer type);

	//分组表模糊查询
	List<DataTree> selectGroupByFuzzyQuery(@Param("name")String name);

}