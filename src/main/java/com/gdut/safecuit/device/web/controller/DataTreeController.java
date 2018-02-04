package com.gdut.safecuit.device.web.controller;

import com.gdut.safecuit.common.Result;
import com.gdut.safecuit.device.common.util.AsyncTask;
import com.gdut.safecuit.device.common.vo.DataTreeVO;
import com.gdut.safecuit.device.service.DataTreeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.gdut.safecuit.common.util.CacheManager.cacheMap;
import static com.gdut.safecuit.common.util.StringUtil.isEmpty;
import static com.gdut.safecuit.device.common.DataTreeType.ELECTRIC_BOX_TREE_TYPE;
import static com.gdut.safecuit.device.common.DataTreeType.ORG_TREE_TYPE;

/**
 * Created by Garson in 11:16 2018/1/21
 * Description : 数据树Controller
 */
@RestController
@RequestMapping("/dataTree")
public class DataTreeController {

	@Resource
	private DataTreeService dataTreeService;
	@Resource
	private AsyncTask asyncTask;

	@RequestMapping("/addGroup")
	public Result<Integer> insertGroup(@RequestParam("name") String name
			,@RequestParam("parentId") Integer parentId
			,@RequestParam("orgId") Integer orgId){
		if (isEmpty(name ,parentId ,orgId))
			return new Result<>(null ,"请求参数不能为空" ,false,400);
		Integer insert = dataTreeService.insertGroup(name ,parentId ,orgId);
		//if(insert == null)
		//return new Result<>(insert ,"抱歉，您权限不足，不能添加机构外的分组" ,false,400);

		if(insert == 0)
			return new Result<>(insert ,"操作失败，请重试" ,false,500);
		else
			return new Result<>(insert ,"操作成功" ,true,200);
	}

	@RequestMapping("/deleteGroup")
	public Result<Integer> deleteGroup(@RequestParam("groupId") Integer groupId){
		if (isEmpty(groupId))
			return new Result<>(null ,"请求参数不能为空" ,false,400);

		Integer delete = dataTreeService.deleteData(groupId);
		if(delete == 0)
			return new Result<>(delete ,"操作失败，请重试" ,false,500);
		else if (delete == 1)
			return new Result<>(delete ,"操作成功" ,true,200);
		else
			return new Result<>(delete ,"分组下仍有分组，请删除孩子分组再删除该分组" ,true,400);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/list")
	public Result<List<DataTreeVO>> list(@RequestParam(value = "treeType" ,required = false)Integer treeType){

		List<DataTreeVO> dataTreeVOS;
		if (isEmpty(treeType))
			return new Result<>(null ,"请求参数不为空",false ,400);

		/*if(treeType == ORG_TREE_TYPE && cacheMap.get("ORG_TREE_TYPE") != null)
			dataTreeVOS = (List<DataTreeVO>) cacheMap.get("ORG_TREE_TYPE");
		else if(treeType == ELECTRIC_BOX_TREE_TYPE && cacheMap.get("ELECTRIC_BOX_TREE_TYPE") != null)
			dataTreeVOS = (List<DataTreeVO>) cacheMap.get("ELECTRIC_BOX_TREE_TYPE");
		else {
			asyncTask.updateDataTreeCache(treeType);
			//parentId待权限改变
			dataTreeVOS = dataTreeService.showTree(-1 ,treeType);
		}*/

		dataTreeVOS = dataTreeService.showTree(-1 ,treeType);

		if (dataTreeVOS == null || dataTreeVOS.size() == 0)
			return new Result<>(dataTreeVOS ,"暂无数据",true ,200);
		else
			return new Result<>(dataTreeVOS ,"显示成功",true ,200);

	}

	@RequestMapping("/update")
	public Result<Integer> update(@RequestParam(value = "name" ,required = false)String name
			,@RequestParam(value = "id" ,required = false)Integer id
			,@RequestParam(value = "parentId" ,required = false)Integer parentId
			,@RequestParam(value = "orgId" ,required = false)Integer orgId
			,@RequestParam(value = "parentOrgId",required = false)Integer parentOrgId
			,@RequestParam(value = "typeId" ,required = false)Integer typeId){
		if (isEmpty(id ,parentId ,typeId ,name))
			return new Result<>(-1 ,"请求参数不能为空", false ,400);
		System.out.println(orgId);
		Integer update = dataTreeService.update(id ,name ,parentId ,parentOrgId ,orgId ,typeId);
		if(update == 0)
			return new Result<>(update ,"修改失败，请重试" ,false ,400);
		else if (update == -2)
			return new Result<>(-2 ,"请勿将机构下的结点移到机构外" ,true ,400);
		else if (update == -3)
			return new Result<>(update ,"请勿将结点移动到其父结点下", true ,200);
		else
			return new Result<>(update ,"修改成功", true ,200);

	}

	/*@RequestMapping("/listByName")
		public Result<List<DataTreeVO>> listByFuzzyQuery(@RequestParam(value = "name" ,required = false)String name){
			List<DataTreeVO> dataTreeVOS;
			if (isEmpty(name))
				return new Result<>(null ,"请求参数不为空",false ,400);
			dataTreeVOS = dataTreeService.selectByFuzzyQuery(name);
			if (dataTreeVOS.size() == 0)
				return new Result<>(dataTreeVOS ,"暂无数据",true ,200);
			else
				return new Result<>(dataTreeVOS ,"显示成功",true ,200);
		}*/
}
