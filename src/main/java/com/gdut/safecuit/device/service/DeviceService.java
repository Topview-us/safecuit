package com.gdut.safecuit.device.service;

import com.gdut.safecuit.common.Page;
import com.gdut.safecuit.common.base.BaseDao;
import com.gdut.safecuit.common.base.BaseServiceImpl;
import com.gdut.safecuit.device.common.po.Circuit;
import com.gdut.safecuit.device.common.po.Device;
import com.gdut.safecuit.device.common.util.DataCache;
import com.gdut.safecuit.device.common.vo.DeviceVO;
import com.gdut.safecuit.device.dao.CircuitMapper;
import com.gdut.safecuit.device.dao.DeviceMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gdut.safecuit.common.util.StringUtil.getUUID;
import static com.gdut.safecuit.device.common.DataTreeTypeCode.DEVICE_TYPE;

/**
 * Created by Garson in 10:19 2018/1/12
 * Description : 设备service
 */
@Service
public  class DeviceService extends BaseServiceImpl<Device> {

	@Resource
	private DeviceMapper deviceMapper;
	@Resource
	private CircuitMapper circuitMapper;
	@Resource
	private DataTreeService dataTreeService;

	/**
	 * 添加设备对象
	 * @param device device对象，属性包括：设备编码code、设备名称name、所属电箱id、监控类型、温度阈值
	 * @return 添加数据条数
	 */
	@Transactional
	public int insert(Device device){
		int insert;
		List<Circuit> circuits = new ArrayList<>();
		device.setId(getUUID());
		device.setDelTag(0);
		//添加设备
		insert = deviceMapper.insertSelective(device);

		//添加设备相关回路
		for (int i = 1; i<=8; i++){
			Circuit circuit = new Circuit(i ,device.getId());
			circuits.add(circuit);
		}
		circuitMapper.insertCircuit(circuits);

		//添加数据树
		if (insert == 1)
			dataTreeService.insertElectricBoxOrDevice(device.getId() ,device.getName() ,device.getElectricBoxId() ,DEVICE_TYPE);

		DataCache.DEVICE_TOTAL++;//全局变量缓存数加一
		return insert;
	}

	public int deleteById(String id){
		int delete = deviceMapper.deleteByPrimaryKey(id);
		DataCache.DEVICE_TOTAL--;//全局变量缓存数减一
		return delete;
	}

	/**
	 * 假删除
	 * @param id 设备id
	 * @return 修改数据条数
	 */
	@Transactional
	public int fakeDelete(String id){
		int delete = deviceMapper.fakeDelete(id);
		if (delete == 1)
			dataTreeService.deleteData(id ,DEVICE_TYPE);

		DataCache.DEVICE_TOTAL--;//全局变量缓存数减一
		return delete;
	}

	/**
	 * 通过电箱id来分页显示相关设备编号或监控类型的设备
	 * @param pageNo 当前页数
	 * @param pageSize 每页数据量
	 * @param electricBoxId 下拉框选择的电箱id
	 * @param code 查询的设备编码
	 * @param typeId 下拉框选择的监控类型
	 * @return DeviceVO集合
	 */
	public List<DeviceVO> selectByPage(Integer pageNo ,Integer pageSize ,String electricBoxId ,String code ,Integer typeId){
		List<DeviceVO> DeviceVOList;
		Page page;
		Map<String ,Object> map;

		//如果全局变量值为0，则在数据库查找数量,如果不为0则直接使用
		if(DataCache.DEVICE_TOTAL == 0)
			DataCache.DEVICE_TOTAL = deviceMapper.getTotal();

		page = new Page(pageSize,pageNo,DataCache.DEVICE_TOTAL);
		map = new HashMap<>();
		DeviceVOList = new ArrayList<>();

		map.put("page",page);
		map.put("electricBoxId",electricBoxId);
		map.put("code" ,code);
		map.put("typeId" ,typeId);

		List<Device> devices = deviceMapper.selectByPage(map);

		for (Device device: devices) {
			//假删除标志为1
			if(device.getDelTag() == 1)
				continue;

			Map<String ,String> electricBoxInfo = deviceMapper.searchElectricBox(device.getElectricBoxId());

			DeviceVOList.add(new DeviceVO(device.getName() ,device.getCode() ,device.getTemperatureValue() ,
					device.getIsOnline(),electricBoxInfo.get("name"), electricBoxInfo.get("address"),device.getTypeId()));
		}

		return DeviceVOList;
	}

	/**
	 * 修改设备
	 * @param device 修改的设备对象，属性包括：设备编码code、设备名称name、设备所属电箱的id、监控类型typeId、温度阈值
	 * @return 修改数据条数
	 */
	public int update(Device device){

		Integer update;

		//如果该设备假删除标识为1，则返回0
		if(deviceMapper.selectDelTag(device.getId()) == 1)
			return 0;
		else {
			update = deviceMapper.updateByPrimaryKeySelective(device);
			//如果有修改，则修改数据树信息
			if (update == 1)
				dataTreeService.updateData(device.getId() ,device.getName() ,device.getElectricBoxId() ,DEVICE_TYPE);
		}
		return update;
	}

	@Override
	public BaseDao<Device> getDao() {
		return deviceMapper;
	}
}
