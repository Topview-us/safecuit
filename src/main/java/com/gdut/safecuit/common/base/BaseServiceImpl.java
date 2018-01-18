package com.gdut.safecuit.common.base;

import java.util.List;

/**
 * Created by Garson in 10:08 2018/1/12
 * Description : BaseService实现类
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {

	public abstract BaseDao<T> getDao();

	@Override
	public int insert(T t) {
		return getDao().insert(t);
	}

	@Override
	public int deleteById(Long id) {
		return getDao().deleteById(id);
	}

	@Override
	public int update(T t) {
		return getDao().update(t);
	}

	@Override
	public List<T> select(T t) {
		return getDao().select(t);
	}

	@Override
	public T selectOneById(Long id) {
		return getDao().selectOneById(id);
	}

	@Override
	public List<T> selectPage(T t) {
		return getDao().selectPage(t);
	}

	@Override
	public int getTotal(T t) {
		return getDao().getTotal(t);
	}
}
