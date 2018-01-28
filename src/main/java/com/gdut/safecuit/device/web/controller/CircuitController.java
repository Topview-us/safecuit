package com.gdut.safecuit.device.web.controller;

import com.gdut.safecuit.common.Page;
import com.gdut.safecuit.common.Result;
import com.gdut.safecuit.device.common.po.Circuit;
import com.gdut.safecuit.device.common.vo.CircuitVO;
import com.gdut.safecuit.device.service.CircuitService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.gdut.safecuit.common.util.CacheManager.cacheMap;
import static com.gdut.safecuit.common.util.MatchUtil.isPositiveInteger;
import static com.gdut.safecuit.common.util.StringUtil.*;
/**
 * Created by Garson in 11:46 2018/1/24
 * Description :
 */
@RestController
@RequestMapping("/circuit")
public class CircuitController extends BaseController {

	@Resource
	private CircuitService circuitService;

	@RequestMapping("/add")
	public Result<Integer> insertCircuit(Circuit circuit){

		Integer result;
		if(isEmpty(circuit.getAddress() ,circuit.getCircuitNo() ,circuit.getDeviceId() ,circuit.getName()))
			result = -1;
		else
			result = circuitService.insertSelective(circuit);
		return getResult(result);
	}

	@RequestMapping("/delete")
	public Result<Integer> deleteCircuit(@RequestParam(value = "circuitId" ,required = false)Integer circuitId){
		if (circuitId == null)
			return getResult(-1);
		else
			return getResult(circuitService.deleteByPrimaryKey(circuitId));
	}

	@RequestMapping("/list")
	public Result<List<CircuitVO>> selectCircuit(@RequestParam(value = "pageNo",required = false ,defaultValue = "0") String pageNo
			, @RequestParam(value = "pageSize" ,required = false ,defaultValue = "10") String pageSize
			, @RequestParam(value = "deviceId" ,required = false) Integer deviceId){

		List<CircuitVO> list;
		String message;

		if(!isPositiveInteger(pageNo) || !isPositiveInteger(pageSize)){
			return new Result<>(null, "url参数有误", true, 400);

		} else if (isEmpty(deviceId)){
			return new Result<>(null, "设备id不能为空", false, 400);
		} else {
			Integer circuit_total = (Integer) cacheMap.get("CIRCUIT_TOTAL");
			if(circuit_total == null){
				circuit_total = circuitService.getTotalByElectricBoxId(deviceId);
				cacheMap.put("DEVICE_TOTAL" ,circuit_total);
			}

			Page page = new Page(Integer.valueOf(pageSize), Integer.valueOf(pageNo) ,circuit_total);
			list = circuitService.select(page ,deviceId);
			if (list.size() == 0)
				message = "暂无设备";
			else
				message = "列举成功";
			return new Result<>(list, message, true, 200 ,page.getTotalPages());
		}


	}

	@RequestMapping("/update")
	public Result<Integer> update(Circuit circuit){
		Integer result;
		if(isEmpty(circuit.getAddress() ,circuit.getCircuitNo() ,circuit.getDeviceId() ,circuit.getName()))
			result = -1;
		else
			result = circuitService.updateByPrimaryKeySelective(circuit);
		return getResult(result);
	}
}
