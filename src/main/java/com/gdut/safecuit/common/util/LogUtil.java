package com.gdut.safecuit.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Garson in 13:24 2018/1/12
 * Description :
 */
public class LogUtil {

	//判断debug是否开启
	private static boolean isDebug = LoggerFactory.getLogger(LogUtil.class).isDebugEnabled();

	/**
	 * info
	 * @param clazz class
	 * @param message 信息
	 */
	public static void info(Class<?> clazz,String message){
		Logger logger = LoggerFactory.getLogger(clazz);
		logger.info(message);
	}

	/**
	 * debug
	 * @param clazz class
	 * @param message message
	 */
	public static void debug(Class<?> clazz ,String message){
		if (isDebug){
			Logger logger = LoggerFactory.getLogger(clazz);
			logger.debug(message);
		}
	}

	/**
	 * error
	 * @param clazz class
	 * @param message message
	 * @param e e
	 */
	public static void error(Class<?> clazz ,String message ,Throwable e){
		Logger logger = LoggerFactory.getLogger(clazz);
		if(null == e)
			logger.error(message);
		else
			logger.error(message,e);
	}

}
