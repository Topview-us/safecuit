package com.gdut.safecuit.common.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by Garson in 19:26 2018/1/18
 * Description :
 */
public class StringUtil {

    /**
     * 验证字符串是否处于长度 a 至 b 中
     * @param str 字符串
     * @param a 最小长度
     * @param b 最大长度
     * @return 符合true, 不符合false
     */
	public static boolean isString(String str, int a, int b) {
	    if (str != null) {
	        int length = str.length();
	        if (length >= a && length <= b) {
	            return true;
            }
        }
        return false;
    }

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

	public static String encoderByBase64(byte[] data){
		if (data == null)
			return null;
		return new BASE64Encoder().encode(data);
	}

	public static byte[] deCodeByBase64(String str) throws IOException {
		if (str == null)
			return null;
		return new BASE64Decoder().decodeBuffer(str);
	}

}
