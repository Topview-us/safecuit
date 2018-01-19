package com.gdut.safecuit.device.service;

import com.gdut.safecuit.common.base.BaseDao;
import com.gdut.safecuit.common.base.BaseServiceImpl;
import com.gdut.safecuit.device.dao.CityMapper;
import com.gdut.safecuit.device.common.po.City;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Garson in 21:01 2018/1/18
 * Description :
 */
@Service
public class CityService extends BaseServiceImpl<City> {

	@Resource
	private CityMapper cityMapper;

	public List<City> select(int provinceId){
		return cityMapper.select(provinceId);
	}

	@Override
	public BaseDao<City> getDao() {
		return cityMapper;
	}
}
