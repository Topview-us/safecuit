package com.gdut.safecuit.common.base;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Garson in 10:07 2018/1/12
 * Description : BaseService
 */
public interface BaseService<T> {

	int insert(T t);

	int deleteById(Long id);

	int update(T t);

	List<T> select(T t);

	T selectOneById(Long id);

	List<T> selectPage(@Param("t") T t);

	int getTotal(T t);
}
