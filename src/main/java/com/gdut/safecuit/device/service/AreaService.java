package com.gdut.safecuit.device.service;

import com.gdut.safecuit.common.base.BaseDao;
import com.gdut.safecuit.common.base.BaseServiceImpl;
import com.gdut.safecuit.device.dao.AreaMapper;
import com.gdut.safecuit.device.common.po.Area;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Garson in 21:09 2018/1/18
 * Description :
 */
@Service
public class AreaService extends BaseServiceImpl<Area> {

	@Resource
	private AreaMapper areaMapper;

	public List<Area> select(int cityId){
		return areaMapper.select(cityId);
	}

	@Override
	public BaseDao<Area> getDao() {
		return areaMapper;
	}
}
