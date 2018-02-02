package com.gdut.safecuit.device.service;

import com.gdut.safecuit.BaseTest;
import com.gdut.safecuit.common.Page;
import com.gdut.safecuit.common.UniqueMainKeyMapper;
import com.gdut.safecuit.device.dao.DataTreeMapper;
import com.gdut.safecuit.organization.common.po.Organization;
import com.gdut.safecuit.organization.dao.AreaMapper;
import com.gdut.safecuit.organization.dao.OrganizationMapper;
import com.gdut.safecuit.organization.service.ProvinceCityAreaService;
import org.aspectj.weaver.ast.Or;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Garson in 9:59 2018/1/21
 * Description :
 */
public class DataTreeTest extends BaseTest {

	@Resource
	private DataTreeMapper dataTreeMapper;
	@Resource
	private ElectricBoxService electricBoxService;
	@Resource
	private DataTreeService dataTreeService;
	@Resource
	private AreaMapper areaMapper;
	@Resource
	private OrganizationMapper organizationMapper;
	@Resource
	private UniqueMainKeyMapper uniqueMainKeyMapper;
	@Resource
	private ProvinceCityAreaService provinceCityAreaService;

	@Test
	public void dataTreeSelectTest(){
		//System.out.println(dataTreeService.get(-1 ,0 ,6));
	}

	@Test
	public void insertOrgDataTree(){
		Integer id = uniqueMainKeyMapper.getMainKey();
		System.out.println(id);
		uniqueMainKeyMapper.updateMainKey(id+1 ,id);

		Organization organization = new Organization();
		organization.setOrgId(id);
		organization.setName("机构二");
		organization.setAddress("XXX");
		organization.setAreaId(37);
		organization.setParentId(1);
		organization.setManagerId(1);
		organization.setEmail("xxx");
		organization.setPhone("111");
		organization.setDelTag(0);
		organizationMapper.insertSelective(organization);
		dataTreeService.insertOrg(id);
	}

	@Test
	public void test(){
	/*	System.out.println(electricBoxService.getRelatedUserTotal(1));
		Page page = new Page(10 ,1 ,3);
		System.out.println(page.getIndex());
		System.out.println(page.getPageSize());
		System.out.println(electricBoxService.showRelatedUser(new Page(10 ,1,3) ,1));*/
		List<Integer> aaa = new ArrayList<>();
		aaa.add(1);
		aaa.add(2);
		aaa.add(3);
		System.out.println(electricBoxService.relateUser(1 ,aaa));
	}

	@Test
	public void test2(){
		System.out.println(dataTreeService.update(1,"广东省",20,null,5,2));
	}

	@Test
	public void test3(){
		System.out.println(areaMapper.selectByPrimaryKey(37));
	}

}
