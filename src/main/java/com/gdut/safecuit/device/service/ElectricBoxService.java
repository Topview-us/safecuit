package com.gdut.safecuit.device.service;

import com.gdut.safecuit.common.Page;
import com.gdut.safecuit.common.base.BaseDao;
import com.gdut.safecuit.common.base.BaseServiceImpl;
import com.gdut.safecuit.device.common.po.ElectricBox;
import com.gdut.safecuit.device.common.vo.ElectricBoxVO;
import com.gdut.safecuit.device.dao.ElectricBoxMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gdut.safecuit.common.util.StringUtil.getUUID;
import static com.gdut.safecuit.device.common.util.DataCache.*;
import static com.gdut.safecuit.device.common.DataTreeTypeCode.ELECTRIC_BOX_TYPE;
/**
 * Created by Garson in 15:35 2018/1/19
 * Description :
 */
@Service
public class ElectricBoxService extends BaseServiceImpl<ElectricBox> {

	@Resource
	private ElectricBoxMapper electricBoxMapper;
	@Resource
	private DataTreeService dataTreeService;

	/**
	 * 添加电箱
	 * @param electricBox 添加的电箱对象，属性包括电箱名称name、地址address、电箱所属机构的id、经度longitude、纬度latitude
	 * @return 添加数据的条数
	 */
	@Transactional
	public int insert(ElectricBox electricBox){
		int insert;
		electricBox.setId(getUUID());
		electricBox.setDelTag(0);
		//添加电箱
		insert = electricBoxMapper.insertSelective(electricBox);

		//添加数据树
		if (insert == 1)
			dataTreeService.insertElectricBoxOrDevice(electricBox.getId() ,electricBox.getName()
					,String.valueOf(electricBox.getOrgId()) ,ELECTRIC_BOX_TYPE);

		ELECTRIC_BOX_TOTAL++;//全局变量缓存数加一
		return insert;
	}

	public int deleteById(String id){
		int delete = electricBoxMapper.deleteByPrimaryKey(id);
		ELECTRIC_BOX_TOTAL--;//全局变量缓存数减一
		return delete;
	}

	/**
	 * 假删除
	 * @param id 电箱id
	 * @return 修改数据条数
	 */
	@Transactional
	public int fakeDelete(String id){
		int delete = electricBoxMapper.fakeDelete(id);
		if(delete == 1)
			dataTreeService.deleteData(id ,ELECTRIC_BOX_TYPE);

		ELECTRIC_BOX_TOTAL--;//全局变量缓存数减一
		return delete;
	}

	/**
	 * 分页搜索、显示某机构下的电箱
	 * @param pageNo 当前页数
	 * @param pageSize 页数数据量
	 * @param orgId 电箱所属的机构id
	 * @param name 电箱的名称
	 * @return ElectricBoxVO集合
	 */
	public List<ElectricBoxVO> selectByPage(int pageNo , int pageSize , int orgId ,String name){
		Page page;
		Map<String ,Object> map;

		if(ELECTRIC_BOX_TOTAL == 0)
			ELECTRIC_BOX_TOTAL = electricBoxMapper.getTotal();

		page = new Page(pageSize,pageNo,ELECTRIC_BOX_TOTAL);
		map = new HashMap<>();
		map.put("page" ,page);
		map.put("orgId" ,orgId);
		map.put("name" ,name);
		//搜索对应机构的电箱
		List<ElectricBox> electricBoxes = electricBoxMapper.selectByPage(map);

		List<ElectricBoxVO> electricBoxVOS = new ArrayList<>();

		for (ElectricBox electricBox:electricBoxes) {

			if(electricBox.getDelTag() == 1)
				continue;

			//搜索电箱对应机构的信息
			Map<String ,String> map1 = electricBoxMapper.searchOrgInfo(electricBox.getOrgId());

			ElectricBoxVO electricBoxVO = new ElectricBoxVO(electricBox.getName() ,map1.get("address")+electricBox.getAddress()
					,electricBox.getLongitude() ,electricBox.getLatitude() ,map1.get("orgName") ,map1.get("personName")
					,map1.get("phone"));

			electricBoxVOS.add(electricBoxVO);
		}

		return electricBoxVOS;
	}

	/**
	 * 添加数据树或设备时下拉框显示的对应机构的电箱名称
	 * @param orgId 机构id
	 * @return 电箱名称集合
	 */
	public List<String> selectNameByOrgId(Integer orgId){
		return electricBoxMapper.selectNameByOrgId(orgId);
	}

	/**
	 * 修改电箱
	 * @param electricBox 修改的电箱对象，属性包括：电箱名称name、地址address、电箱所属机构的id、经度longitude、纬度latitude
	 * @return 修改数据条数
	 */
	@Transactional
	public int update(ElectricBox electricBox){

		Integer update;

		// 如果该设备假删除标识为1，则返回0
		if(electricBoxMapper.selectDelTag(electricBox.getId()) == 1)
			return 0;
		else {
			update = electricBoxMapper.updateByPrimaryKeySelective(electricBox);
			if (update == 1)
				dataTreeService.updateData(electricBox.getId() ,electricBox.getId() ,
						String.valueOf(electricBox.getOrgId()) ,ELECTRIC_BOX_TYPE);
		}
		return update;
	}

	@Override
	public BaseDao<ElectricBox> getDao() {
		return electricBoxMapper;
	}
}
