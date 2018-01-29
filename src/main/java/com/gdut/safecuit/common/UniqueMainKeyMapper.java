package com.gdut.safecuit.common;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * Created by Garson in 14:32 2018/1/27
 * Description : 维护一个全局主键，保证分组表、机构表、电箱表id唯一
 */
@Repository
public interface UniqueMainKeyMapper {

	@Select("select id from main_key")
	int getMainKey();

	@Update("update main_key set id = #{newId} where id = #{oldId}")
	int updateMainKey(@Param("newId")Integer newId ,@Param("oldId")Integer oldId);

}
