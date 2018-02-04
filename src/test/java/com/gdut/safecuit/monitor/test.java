package com.gdut.safecuit.monitor;

import com.gdut.safecuit.BaseTest;
import com.gdut.safecuit.monitor.dao.DataLogMapper;
import com.gdut.safecuit.monitor.service.DataLogService;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by Garson in 13:24 2018/1/31
 * Description :
 */
public class test extends BaseTest {

	@Resource
	private DataLogMapper dataLogMapper;
	@Resource
	private DataLogService dataLogService;

	@Test
	public void test(){
		System.out.println(dataLogMapper.selectByDeviceIdAndCircuitNo(1,1,203));
	}

	/*@Test
	public void test1(){
		System.out.println(dataLogService.getTotalByTypeIdAndDeviceId(201,50));
	}*/
}
