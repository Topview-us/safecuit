package com.gdut.safecuit.device.common.util;

import com.gdut.safecuit.device.service.DataTreeService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static com.gdut.safecuit.common.util.CacheManager.cacheMap;
import static com.gdut.safecuit.device.common.DataTreeType.ORG_TREE_TYPE;


/**
 * Created by Garson in 23:44 2018/1/24
 * Description : 异步任务类
 */
@Component
public class AsyncTask {

	@Resource
	private DataTreeService dataTreeService;

	/**
	 * 异步更新缓存
	 * @param treeType 树类型
	 */
	@Async
	public void updateDataTreeCache(int treeType){

		//依据类型更新数据树
		if (treeType == ORG_TREE_TYPE)
			cacheMap.put("ORG_TREE_TYPE" ,dataTreeService.showTree(-1 ,treeType));
		else
			cacheMap.put("ELECTRIC_BOX_TREE_TYPE" ,dataTreeService.showTree(-1 ,treeType));

	}
}
