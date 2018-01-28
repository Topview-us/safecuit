package com.gdut.safecuit.device.common.util;

import com.gdut.safecuit.device.service.DataTreeService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static com.gdut.safecuit.common.DataTreeTypeCode.*;
import static com.gdut.safecuit.common.util.CacheManager.cacheMap;


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
	 * @param typeId 类型id
	 */
	@Async
	public void updateDataTreeCache(int typeId){

		//依据类型更新数据树，
		/*if(typeId == DEVICE_TYPE)
			cacheMap.put("DEVICE_DATA_TREE" ,dataTreeService.get(-1 ,0 ,ELECTRIC_BOX_TYPE));

		else if(typeId == ELECTRIC_BOX_TYPE){

			cacheMap.put("ELECTRIC_BOX_DATA_TREE" ,dataTreeService.get(-1 ,0 ,ORG_TYPE));
			cacheMap.put("DEVICE_DATA_TREE" ,dataTreeService.get(-1 ,0 ,ELECTRIC_BOX_TYPE));

		}else {
			cacheMap.put("ORG_DATA_TREE" ,dataTreeService.get(-1 ,0 ,AREA_TYPE));
			cacheMap.put("ELECTRIC_BOX_DATA_TREE" ,dataTreeService.get(-1 ,0 ,ORG_TYPE));
			cacheMap.put("DEVICE_DATA_TREE" ,dataTreeService.get(-1 ,0 ,ELECTRIC_BOX_TYPE));
		}*/
	}
}
