package com.gdut.safecuit.device.service;

import com.gdut.safecuit.common.base.BaseDao;
import com.gdut.safecuit.common.base.BaseServiceImpl;
import com.gdut.safecuit.device.dao.ProvinceMapper;
import com.gdut.safecuit.device.common.po.Province;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Garson in 20:49 2018/1/18
 * Description :
 */
@Service
public class ProvinceService extends BaseServiceImpl<Province> {

	@Resource
	private ProvinceMapper provinceMapper;

	public List<Province> select(){
		return provinceMapper.select();
	}

	@Override
	public BaseDao<Province> getDao() {
		return provinceMapper;
	}
}
