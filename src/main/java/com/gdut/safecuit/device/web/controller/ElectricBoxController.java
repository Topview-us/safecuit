package com.gdut.safecuit.device.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gdut.safecuit.common.Page;
import com.gdut.safecuit.common.Result;
import com.gdut.safecuit.device.common.po.ElectricBox;
import com.gdut.safecuit.device.common.vo.ElectricBoxRelateUserVO;
import com.gdut.safecuit.device.service.ElectricBoxService;
import netscape.javascript.JSObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
	 * 关联电箱管理员接口
	 * @param electricBoxId 电箱id
	 * @param userId 人员id
	 * @return 结果集
	 */
	@RequestMapping("/relateUser")
	public Result<Integer> relateUser(@RequestParam(value = "electricBoxId" ,required = false)Integer electricBoxId
							,@RequestParam(value = "userId" ,required = false)String userIdJson){

		
		Integer i;
		if (isEmpty(electricBoxId ,userIdJson))
			i = -1;
		else {
			JSONArray jsonArray = JSON.parseArray(userIdJson);
			List<Integer> userIds = new ArrayList<>();
			for (Object object : jsonArray) {
				JSONObject jsonObject = (JSONObject) object;
				userIds.add((Integer) jsonObject.get("userId"));
			}
			i = electricBoxService.relateUser(electricBoxId ,userIds);
		}

		return getResult(i);
	}

	/**
	 * 显示电箱管理员
	 * @param electricBoxId 电箱id
	 * @return 结果集
	 */
	@RequestMapping("/listRelatedUser")
	public Result<List<ElectricBoxRelateUserVO>> showRelatedUser(@RequestParam(value = "electricBoxId" ,required = false)Integer electricBoxId
							, @RequestParam(value = "pageNo" ,required = false ,defaultValue = "1")Integer pageNo
							, @RequestParam(value = "pageSize" ,required = false ,defaultValue = "10")Integer pageSize){
		if (isEmpty(electricBoxId))
			return new Result<>(null ,"请求参数不能为空" ,false ,400);
		Integer total = electricBoxService.getRelatedUserTotal(electricBoxId);
		Page page = new Page(pageSize ,pageNo ,total);
		List<ElectricBoxRelateUserVO> userVOS = electricBoxService.showRelatedUser(page ,electricBoxId);
		if (userVOS.size() == 0)
			return new Result<>(null ,"暂无管理员" ,true ,200);
		else
			return new Result<>(userVOS ,"列举成功" ,true ,200 ,page.getTotal());
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
