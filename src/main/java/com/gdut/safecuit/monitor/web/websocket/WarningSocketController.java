package com.gdut.safecuit.monitor.web.websocket;

import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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

	@SendTo("/warningAudio")
	public void PushWarningAudio(String warningMessageBase64){
		simpMessagingTemplate.convertAndSend("/warningAudio" ,warningMessageBase64);
	}
}
