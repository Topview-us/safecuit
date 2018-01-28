package com.gdut.safecuit.device.service;

import com.gdut.safecuit.BaseTest;
import com.gdut.safecuit.device.common.po.ElectricBox;
import com.gdut.safecuit.device.dao.DataTreeMapper;
import org.junit.Test;

import javax.annotation.Resource;
import static com.gdut.safecuit.common.util.StringUtil.*;
/**
 * Created by Garson in 13:44 2018/1/23
 * Description :
 */
public class ElectricBoxServiceTest extends BaseTest {

	@Resource
	private ElectricBoxService electricBoxService;
	@Resource
	private DataTreeMapper dataTreeMapper;

	@Test
	public void test1(){
	/*	System.out.println(electricBoxService.insert(new ElectricBox(getUUID() ,"444" ,"xx路62号"
				,1 ,100F ,100F ,0)));*/
	}

	/*@Test
	public void test2(){
		System.out.println(dataTreeMapper.selectByParentId(-1 ,0));
	}*/


}
