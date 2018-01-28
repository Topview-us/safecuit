package com.gdut.safecuit.device.dao;

import com.gdut.safecuit.common.base.BaseDao;
import com.gdut.safecuit.device.common.po.DataTree;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface DataTreeMapper extends BaseDao<DataTree> {

	@Select("select * from data_tree where id = #{parentId}")
	DataTree selectOrgIdAndParentIdByParentIdInGroup(Integer parentId);
	//int selectByNameAndParentId(@Param("name")String name ,@Param("parentId")Integer parentId);
}