package com.gdut.safecuit.monitor;

import com.gdut.safecuit.BaseTest;
import com.gdut.safecuit.monitor.dao.DataLogMapper;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by Garson in 13:24 2018/1/31
 * Description :
 */
public class test extends BaseTest {

	@Resource
	private DataLogMapper dataLogMapper;

	@Test
	public void test(){
		System.out.println(dataLogMapper.selectByDeviceIdAndCircuitNo(1,1,203));
	}
}
