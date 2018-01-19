package com.gdut.safecuit.device.service;

import com.gdut.safecuit.BaseTest;
import com.gdut.safecuit.device.dao.CityMapper;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Garson in 9:24 2018/1/19
 * Description :
 */
public class CityService extends BaseTest {

	@Resource
	private CityMapper cityMapper;

	@Test
	public void test(){

		System.out.println(cityMapper.select(1));
	}

}
