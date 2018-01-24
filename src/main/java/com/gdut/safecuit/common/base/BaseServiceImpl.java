package com.gdut.safecuit.common.base;

import java.util.List;

/**
 * Created by Garson in 10:08 2018/1/12
 * Description : BaseService实现类
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {

	public abstract BaseDao<T> getDao();

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return getDao().deleteByPrimaryKey(id);
	}

	@Override
	public int insert(T t) {
		return getDao().insert(t);
	}

	@Override
	public int insertSelective(T t) {
		return getDao().insert(t);
	}

	@Override
	public T selectByPrimaryKey(Integer id) {
		return getDao().selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(T t) {
		return getDao().updateByPrimaryKeySelective(t);
	}

	@Override
	public int updateByPrimaryKey(T t) {
		return getDao().updateByPrimaryKey(t);
	}

	@Override
	public int insertList(List<T> t) {
		return getDao().insertList(t);
	}

	@Override
	public List<T> selectPage(T t) {
		return getDao().selectPage(t);
	}

	@Override
	public List<T> selectAll() {
		return getDao().selectAll();
	}

	@Override
	public int getTotal() {
		return getDao().getTotal();
	}
}
