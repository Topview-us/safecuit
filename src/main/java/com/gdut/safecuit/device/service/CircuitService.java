package com.gdut.safecuit.device.service;

import com.gdut.safecuit.common.Page;
import com.gdut.safecuit.common.base.BaseDao;
import com.gdut.safecuit.common.base.BaseServiceImpl;
import com.gdut.safecuit.device.common.po.Circuit;
import com.gdut.safecuit.device.common.po.Device;
import com.gdut.safecuit.device.common.vo.CircuitVO;
import com.gdut.safecuit.device.dao.CircuitMapper;
import com.gdut.safecuit.device.dao.DeviceMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import static com.gdut.safecuit.common.util.CacheManager.cacheMap;
/**
 * Created by Garson in 11:43 2018/1/24
 * Description :
 */
@Service
public class CircuitService extends BaseServiceImpl<Circuit> {

	@Resource
	private CircuitMapper circuitMapper;
	@Resource
	private DeviceMapper deviceMapper;

	public int insertSelective(Circuit circuit){
		if(circuitMapper.selectByNameAndDeviceId(circuit.getName() ,circuit.getDeviceId()) != null)
			return -2;
		Integer insert = circuitMapper.insertSelective(circuit);
		Integer circuit_total = (Integer) cacheMap.get("CIRCUIT_TOTAL");

		if(cacheMap.get("CIRCUIT_TOTAL") == null)
			cacheMap.put("CIRCUIT_TOTAL" ,circuitMapper.getTotal());
		else
			cacheMap.put("CIRCUIT_TOTAL" ,++circuit_total);
		return insert;
	}

	public int deleteByPrimaryKey(Integer id){
		Integer delete = circuitMapper.deleteByPrimaryKey(id);
		Integer circuit_total = (Integer) cacheMap.get("CIRCUIT_TOTAL");

		if(cacheMap.get("CIRCUIT_TOTAL") == null)
			cacheMap.put("CIRCUIT_TOTAL" ,circuitMapper.getTotal());
		else
			cacheMap.put("CIRCUIT_TOTAL" ,--circuit_total);

		return delete;
	}

	public List<CircuitVO> select(Page page ,Integer deviceId){
		List<CircuitVO> circuitVOS = new ArrayList<>();

		/*if(cacheMap.get("CIRCUIT_TOTAL") == null)
			cacheMap.put("CIRCUIT_TOTAL" ,circuitMapper.selectCircuitCountByDeviceId(deviceId));*/

		List<Circuit> circuits = circuitMapper.selectAllByPageAndDeviceId(page ,deviceId);

		for (Circuit circuit:circuits) {
			Device device = deviceMapper.selectByPrimaryKey(circuit.getDeviceId());

			CircuitVO circuitVO = new CircuitVO(circuit.getId() ,circuit.getName() ,circuit.getAddress()
					,circuit.getCircuitNo() ,device.getCode() ,device.getName() ,device.getTypeId());
			circuitVOS.add(circuitVO);
		}

		return circuitVOS;
	}

	public Integer getTotalByElectricBoxId(Integer deviceId){
		return circuitMapper.selectCircuitCountByDeviceId(deviceId);
	}

	@Override
	public BaseDao<Circuit> getDao() {
		return circuitMapper;
	}
}
