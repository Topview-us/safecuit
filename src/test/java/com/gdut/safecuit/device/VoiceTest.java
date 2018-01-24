package com.gdut.safecuit.device;

import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import com.gdut.safecuit.monitor.common.util.BaiduString2AudioUtil;
import org.json.JSONException;
import org.junit.Test;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Garson in 14:58 2018/1/22
 * Description :
 */
public class VoiceTest {

	private static final String APP_ID = "10728055";
	private static final String API_KEY = "4FcYM186IxNbs1r5HUcDyzaZ";
	private static final String SECRET_KEY = "1c3074b24462b48188a549624915bb56";

	@Test
	public void test(){
		AipSpeech aipSpeech = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);
	//	aipSpeech.setHttpProxy("port" ,8080);
		HashMap<String, Object> options = new HashMap<>();
		options.put("spd", "5");
		options.put("pit", "5");
		options.put("per", "4");
		TtsResponse ttsResponse = aipSpeech.synthesis("你好百度", "zh", 1, options);
		byte[] aa = ttsResponse.getData();
		getFile(aa, "D://java", "1.mp3");
		System.out.println(ttsResponse);
	}

	private static void getFile(byte[] bfile, String filePath, String fileName) {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			File dir = new File(filePath);
			if (!dir.exists() && dir.isDirectory()) {// 判断文件目录是否存在
				dir.mkdirs();
			}
			file = new File(filePath + "\\" + fileName);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(bfile);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	@Test
	public void testData() throws JSONException {
		System.out.println(BaiduString2AudioUtil.getAudioByte("我真帅"));
	}

}
