package com.gdut.safecuit.device.web.controller;

import com.gdut.safecuit.common.Page;
import com.gdut.safecuit.common.Result;
import com.gdut.safecuit.device.common.po.Circuit;
import com.gdut.safecuit.device.common.vo.CircuitVO;
import com.gdut.safecuit.device.service.CircuitService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.gdut.safecuit.common.util.CacheManager.cacheMap;
import static com.gdut.safecuit.common.util.StringUtil.isEmpty;
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
		if(isEmpty(circuit.getAddress() ,circuit.getDeviceId() ,circuit.getName()))
			result = -1;
		else
			result = circuitService.insertCircuit(circuit);
		return getResult(result);
	}

	@RequestMapping("/delete")
	public Result<Integer> deleteCircuit(@RequestParam(value = "id" ,required = false)Integer circuitId
								,@RequestParam(value = "deviceId" ,required = false)Integer deviceId){
		if (circuitId == null || deviceId == null)
			return getResult(-1);
		else
			return getResult(circuitService.deleteCircuit(circuitId ,deviceId));
	}

	@RequestMapping("/list")
	public Result<List<CircuitVO>> selectCircuit(@RequestParam(value = "pageNo",required = false ,defaultValue = "1") Integer pageNo
			, @RequestParam(value = "pageSize" ,required = false ,defaultValue = "10") Integer pageSize
			, @RequestParam(value = "deviceId" ,required = false) Integer deviceId){

		List<CircuitVO> list;
		String message;

		if (isEmpty(deviceId)){
			return new Result<>(null, "设备id不能为空", false, 400);
		} else {

			Page page = new Page(pageSize, pageNo ,circuitService.getTotalByElectricBoxId(deviceId));
			list = circuitService.selectCircuitByPage(page ,deviceId);
			if (list.size() == 0)
				message = "暂无设备";
			else
				message = "列举成功";
			return new Result<>(list, message, true, 200 ,page.getTotal());
		}

	}

	@RequestMapping("/update")
	public Result<Integer> update(Circuit circuit){
		Integer result;
		if(isEmpty(circuit.getId() ,circuit.getAddress()
				,circuit.getDeviceId() ,circuit.getName()))
			result = -1;
		else
			result = circuitService.updateByPrimaryKeySelective(circuit);
		return getResult(result);
	}
}
