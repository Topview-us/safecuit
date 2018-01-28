package com.gdut.safecuit.monitor.web.websocket;

import com.gdut.safecuit.common.Result;
import com.gdut.safecuit.common.util.LogUtil;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by Garson in 14:30 2018/1/26
 * Description :
 */
@RestController
public class WarningSocketController {

	@Resource
	private SimpMessagingTemplate simpMessagingTemplate;

	@SubscribeMapping("/warningAudio")
	public Result<String> subscribeWarningPort(){
		LogUtil.info(this.getClass() ,"有订阅用户");
		return new Result<>("订阅成功" ,"订阅成功" ,true ,200);
	}

	public void PushWarningAudio(String warningMessageBase64){
		simpMessagingTemplate.convertAndSend("/warningAudio" ,warningMessageBase64);
	}
}
