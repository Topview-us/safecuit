package com.gdut.safecuit.device.common.util;

import com.gdut.safecuit.common.util.CacheManager;
import com.gdut.safecuit.common.util.LogUtil;
import org.springframework.stereotype.Component;

import static com.gdut.safecuit.device.common.DataTreeType.ELECTRIC_BOX_TYPE;
import static com.gdut.safecuit.device.common.DataTreeType.ORG_TYPE;

/**
 * Created by Garson in 22:33 2018/1/24
 * Description : 开线程以定时清理缓存
 */
@Component
public class DataTreeCacheTask implements Runnable {

	@Override
	public void run() {
		try{
			while (true){

				Thread.sleep(3*60*1000);
				CacheManager.clearOnly(ORG_TYPE);//清理缓存

				CacheManager.clearOnly(ELECTRIC_BOX_TYPE);//清理缓存

				LogUtil.info(this.getClass() ,"清理一次树缓存");
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.error(this.getClass() ,"清理缓存抛异常：\nexception_info:\n" ,e);
		}
	}

}
