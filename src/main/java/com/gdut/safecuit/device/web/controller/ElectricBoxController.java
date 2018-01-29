package com.gdut.safecuit.device.web.controller;

import com.gdut.safecuit.common.Result;
import com.gdut.safecuit.device.common.po.ElectricBox;
import com.gdut.safecuit.device.common.vo.ElectricBoxVO;
import com.gdut.safecuit.device.service.ElectricBoxService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.gdut.safecuit.common.util.MatchUtil.isPositiveInteger;
import static com.gdut.safecuit.common.util.StringUtil.isEmpty;

/**
 * Created by Garson in 10:07 2018/1/22
 * Description :
 */
@RestController
@RequestMapping("/electricBox")
public class ElectricBoxController extends BaseController {

	@Resource
	private ElectricBoxService electricBoxService;

	/**
	 * 添加电箱接口
	 * url:	/electricBox/add
	 * @param electricBox 添加的电箱对象，属性包括电箱名称name、地址address、电箱所属机构的id、经度longitude、纬度latitude、父母结点id
	 * @return 结果集
	 */
	@RequestMapping("/add")
	public Result<Integer> insertElectricBox(ElectricBox electricBox){
		Integer i;

		if(isEmpty(electricBox.getAddress() ,electricBox.getName(),
				   electricBox.getOrgId(),electricBox.getLatitude(),
				   electricBox.getLongitude() ,electricBox.getParentId()))
			i = -1;
		else
			i = electricBoxService.insertElectricBox(electricBox);

		return getResult(i);
	}

	/**
	 * 假删除电箱接口
	 * url:/electricBox/delete?id=xxx
	 * @param id 电箱id
	 * @return 结果集
	 */
	@RequestMapping("/delete")
	public Result<Integer> deleteElectricBox(@RequestParam("id") Integer id){

		Integer i = electricBoxService.deleteElectricBoxById(id);
		return getResult(i);
	}

	/**
	 * 分页搜索、显示某机构下的电箱
	 * url:/electricBox/list?pageNo=xxx&&pageSize=xxx&&orgId=xxx&&name=xxx
	 * @param pageNo 当前页数
	 * @param pageSize 当前页数据量
	 * @param orgId 机构id
 	 * @param name 电箱名称
	 * @return 结果集
*/	/*@RequestMapping("/list")
	public Result<List<ElectricBoxVO>> selectElectricBoxByPage(@RequestParam(value = "pageNo" ,required = false,defaultValue = "0")String pageNo
			,@RequestParam(value = "pageSize" ,required = false ,defaultValue = "10")String pageSize
			,@RequestParam(value = "orgId" ,required = false)String orgId
			,@RequestParam(value = "name" ,required = false)String name){
		List<ElectricBoxVO> electricBoxVOS;
		String message;
		int status;
		Boolean isSuccess;

		if(!isPositiveInteger(pageNo) || !isPositiveInteger(pageSize)){
			message = "url参数有误";
			status = 400;
			isSuccess = false;
			electricBoxVOS = null;
		}else if(isEmpty(orgId)) {
			message = "机构id不能为空";
			status = 400;
			isSuccess = false;
			electricBoxVOS = null;
		}else {
			electricBoxVOS = electricBoxService.selectElectricBoxByPage(Integer.valueOf(pageNo) ,Integer.valueOf(pageSize)
					,Integer.valueOf(orgId) ,name);
			if (electricBoxVOS.size() == 0)
				message = "暂无电箱";
			else
				message = "列举成功";
			status = 200;
			isSuccess = true;
		}

		return new Result<>(electricBoxVOS ,message ,isSuccess ,status);
	}*/

	@RequestMapping("/updateSelect")
	public Result<ElectricBox> selectWhenUpdate(@RequestParam("id")Integer id){
		return new Result<>(electricBoxService.selectByPrimaryKey(id) ,"列举成功" ,true ,200);
	}

	/**
	 * 列举电箱
	 * url:/electricBox/listElectricBoxName?orgId=xxx
	 * @param orgId 机构id
	 * @return 结果集
	 */
	/*@RequestMapping("/listElectricBoxName")
	public Result<List<String>> selectElectricBoxNameByOrgId(@RequestParam("orgId")Integer orgId){
		return new Result<>(electricBoxService.selectElectricBoxNameByOrgId(orgId) ,"列举成功" ,true ,200);
	}*/

	/**
	 * 更改电箱信息接口
	 * url：/electricBox/update
	 * @param electricBox 修改的电箱对象，属性包括电箱名称name、地址address、电箱所属机构的id、经度longitude、纬度latitude
	 * @return 结果集
	 */
	@RequestMapping("/update")
	public Result<Integer> updateElectricBox(ElectricBox electricBox){

		Integer i;

		if(isEmpty(electricBox.getAddress() ,electricBox.getName(),
				electricBox.getOrgId(),electricBox.getLatitude(),electricBox.getLongitude()))
			i = -1;
		else
			i = electricBoxService.updateElectricBox(electricBox);

		return getResult(i);
	}

}
