package com.gdut.safecuit.device.service;

import com.gdut.safecuit.BaseTest;
import com.gdut.safecuit.device.dao.DataTreeMapper;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by Garson in 9:59 2018/1/21
 * Description :
 */
public class DataTreeTest extends BaseTest {

	@Resource
	private DataTreeMapper dataTreeMapper;
	@Resource
	private DataTreeService dataTreeService;

	@Test
	public void dataTreeSelectTest(){
		System.out.println(dataTreeService.getDataTreeList(-1 ,0 ,6));
	}



	@Test
	public void hasChild(){
		System.out.println(dataTreeMapper.hasChild(10));
	}

}
