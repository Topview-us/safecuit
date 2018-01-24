package com.gdut.safecuit.common.base;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Garson in 10:07 2018/1/12
 * Description : BaseService
 */
public interface BaseService<T> {

	int deleteByPrimaryKey(Integer id);

	int insert(T t);

	int insertSelective(T t);

	T selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(T t);

	int updateByPrimaryKey(T t);

	int insertList(List<T> t);

	List<T> selectPage(@Param("t") T t);

	List<T> selectAll();

	int getTotal();
}
