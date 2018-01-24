package com.gdut.safecuit.monitor.common.util;

import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import com.gdut.safecuit.common.util.LogUtil;
import com.gdut.safecuit.common.util.StringUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Garson in 15:28 2018/1/22
 * Description : 百度语音：字符串合成语音二进制数据
 * 				 参考教程网站：http://yuyin.baidu.com/docs/tts/193
 */
public class BaiduString2AudioUtil {

	//百度语音应用获得
	private static final String APP_ID = "10728055";
	private static final String API_KEY = "4FcYM186IxNbs1r5HUcDyzaZ";
	private static final String SECRET_KEY = "1c3074b24462b48188a549624915bb56";

	public static String getAudioByte(String str) throws JSONException {
		HashMap<String, Object> options = new HashMap<>();

		AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);

		client.setConnectionTimeoutInMillis(2000);//建立连接的超时时间
		client.setSocketTimeoutInMillis(30000);//通过打开的连接传输数据的超时时间

		options.put("spd", "5");//语速，0~9
		options.put("pit", "5");//音调，0~9
		options.put("per", "0");//发声人，0为女，1为男
		options.put("vol", "9");//音量，0~15

		TtsResponse res = client.synthesis(str, "zh", 1, options);
		JSONObject result = res.getResult();
		if(result != null){
			LogUtil.info(BaiduString2AudioUtil.class ,"err_no: "+String.valueOf(result.get("error_code"))
					+ "\n" + "err_msg: " + String.valueOf(result.get("error_msg")));
			return "error";
		} else {
			byte[] data = res.getData();
			return StringUtil.encoderByBase64(data);
		}
	}

}
