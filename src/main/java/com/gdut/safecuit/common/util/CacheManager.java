package com.gdut.safecuit.common.util;

import java.util.HashMap;

/**
 * Created by Garson in 9:11 2018/1/25
 * Description : 自定义缓存类
 */
public class CacheManager {

	//public static HashMap<String ,HashMap<String ,?>> cacheMap = new HashMap<>();
	public static HashMap<Object ,Object> cacheMap = new HashMap<>();

	//获取缓存
	public static Object getCache(Object key){
		return cacheMap.get(key);
	}

	//添加缓存
	public static void putCache(Object key ,Object object){
		cacheMap.put(key ,object);
	}

	//判断是否存在一个缓存
	public synchronized static boolean hasCache(Object key) {
		return cacheMap.containsKey(key);
	}

	//清除所有缓存
	public synchronized static void clearAll() {
		cacheMap.clear();
	}

	//清除指定的缓存
	public synchronized static void clearOnly(Object key) {
		cacheMap.remove(key);
	}


	//获取缓存中的大小
	public static int getCacheSize() {
		return cacheMap.size();
	}

}
