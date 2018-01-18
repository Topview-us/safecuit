package com.gdut.safecuit.device.service;

import com.gdut.safecuit.BaseTest;
import com.gdut.safecuit.common.util.StringUtil;
import com.gdut.safecuit.device.dao.DeviceMapper;
import com.gdut.safecuit.device.po.Device;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by Garson in 14:24 2018/1/18
 * Description : 设备service测试
 */
public class DeviceServiceTest extends BaseTest {

	@Resource
	private DeviceService deviceService;

	@Test
	public void selectAllByIdTest(){
		System.out.println(deviceService.selectOneById(1L));
	}

	@Test
	public void selectByPageTest(){
		System.out.println(deviceService.selectByPage(1,2));
	}

	@Test
	public void insertTest(){
		System.out.println(deviceService.insert(new Device("code1","name1","address1",1,1,0)));
	}

	@Test
	public void updateTest(){
		System.out.println(deviceService.update(new Device(StringUtil.getUUID(),"code","name1","address1",1,1,0)));
	}

	@Test
	public void deleteTest(){
		System.out.println(deviceService.deleteById(2L));
	}

	@Test
	public void fakeDeleteTest(){
		System.out.println(deviceService.fakeDelete("aaa"));
	}
}
