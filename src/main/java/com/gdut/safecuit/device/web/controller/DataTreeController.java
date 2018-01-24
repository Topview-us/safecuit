package com.gdut.safecuit.device.web.controller;

import com.gdut.safecuit.common.Result;
import com.gdut.safecuit.device.common.po.DataTree;
import com.gdut.safecuit.device.common.vo.DataTreeVO;
import com.gdut.safecuit.device.service.DataTreeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static com.gdut.safecuit.common.DataTreeTypeCode.DEVICE_TYPE;
import static com.gdut.safecuit.common.DataTreeTypeCode.ELECTRIC_BOX_TYPE;
import static com.gdut.safecuit.common.DataTreeTypeCode.ORG_TYPE;
import static com.gdut.safecuit.device.common.util.DataCache.DEVICE_DATATREEVOS;
import static com.gdut.safecuit.device.common.util.DataCache.ELECTRIC_BOX_DATATREEVOS;
import static com.gdut.safecuit.device.common.util.DataCache.ORG_DATATREEVOS;
import static com.gdut.safecuit.common.util.StringUtil.*;
/**
 * Created by Garson in 11:16 2018/1/21
 * Description : 数据树Controller
 */
@RestController
@RequestMapping("/dataTree")
public class DataTreeController {

	@Resource
	private DataTreeService dataTreeService;

	@RequestMapping("/list")
	public Result<List<DataTreeVO>> list(@RequestParam(value = "typeId" ,required = false)Integer typeId){

		List<DataTreeVO> dataTreeVOS;
		if (isEmpty(typeId))
			return new Result<>(null ,"请求参数不为空",false ,400);

		//判断是否有本地缓存
		if(typeId == ORG_TYPE && ORG_DATATREEVOS != null)
			dataTreeVOS = ORG_DATATREEVOS;
		else if(typeId == ELECTRIC_BOX_TYPE && ELECTRIC_BOX_DATATREEVOS != null)
			dataTreeVOS = ELECTRIC_BOX_DATATREEVOS;
		else if(typeId == DEVICE_TYPE && DEVICE_DATATREEVOS != null)
			dataTreeVOS = DEVICE_DATATREEVOS;
		else {
			System.out.println("111");
			System.out.println(ELECTRIC_BOX_DATATREEVOS);
			dataTreeVOS = dataTreeService.getDataTreeList(-1 ,0 ,typeId);
		}

		return new Result<>(dataTreeVOS ,"显示成功",true ,200);
	}

	@RequestMapping("/addGroup")
	public Result<Integer> insertGroup(@RequestBody DataTree group){
		Integer insert = dataTreeService.insertGroup(group);
		if(insert == 0)
			return new Result<>(insert ,"添加分组失败，请重试" ,false,500);
		else if(insert == -1)
			return new Result<>(insert ,"该分组已存在" ,false,400);
		else
			return new Result<>(insert ,"添加分组成功" ,true,200);
	}

	/*@RequestMapping("/deleteGroup")
	public Result<Integer> deleteGroup(@RequestBody DataTree group){
		Integer delete = dataTreeService.deleteGroup(group);
		if(delete == 0)
			return new Result<>(delete ,"删除分组失败，请重试" ,false,500);
		else
			return new Result<>(delete ,"删除分组成功" ,true,200);
	}*/

	@RequestMapping("/listGroup")
	public Result<List<DataTree>> listGroup(){
		List<DataTree> dataTrees = dataTreeService.selectGroup();
		return new Result<>(dataTrees ,"列举'分组'对象",true ,200);
	}

	@RequestMapping("/listOrgAndGroup")
	public Result<List<DataTree>> listOrgAndGroup(){
		return new Result<>(dataTreeService.getOrgOrGroupList() ,"列举‘分组’和‘机构’对象" ,true ,200);
	}

	@RequestMapping("/update")
	public Result<Integer> updateParent(@RequestParam(value = "name" ,required = false)String name
										,@RequestParam(value = "id" ,required = false)Integer id
										,@RequestParam(value = "parentId" ,required = false)Integer parentId){
		if (isEmpty(id ,parentId))
			return new Result<>(null ,"请求参数不能为空", false ,400);

		Integer update = dataTreeService.update(name ,parentId ,id);
		if(update == 0)
			return new Result<>(update ,"修改失败，请重试" ,false ,400);
		else
			return new Result<>(update ,"修改成功", true ,200);
	}

	/*@RequestMapping("/update")
	public Result<Integer> update(@RequestBody DataTree dataTree){
		Integer update = dataTreeService.update(dataTree);
		if(update == 0)
			return new Result<>(update ,"修改失败" ,false ,500);
		else
			return new Result<>(update ,"修改成功" ,true ,200);
	}*/

}
