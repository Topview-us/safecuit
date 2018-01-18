package com.gdut.safecuit.common.util;

import java.util.UUID;

/**
 * Created by Garson in 19:26 2018/1/18
 * Description :
 */
public class StringUtil {

	public static boolean isEmpty(Object...objects){
		Boolean result = false ;
		for (Object object : objects) {
			if(null == object || "".equals(object.toString().trim())
					|| "null".equals(object.toString().trim())){
				result = true ;
				break ;
			}
		}
		return result ;
	}

	//获取UUID字符串
	public static String getUUID(){
		return UUID.randomUUID().toString().replaceAll("-","");
	}

}
