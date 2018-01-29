package com.gdut.safecuit.monitor.common.properties;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * Created by Garson in 13:41 2018/1/26
 * Description :
 */
@Configuration
//通过EnableWebSocketMessageBroker 开启使用STOMP协议来传输基于代理(message broker)的消息,此时浏览器支持使用@MessageMapping 就像支持@RequestMapping一样。
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
		// 允许使用socketJs方式访问，访问点为webSocketServer，允许跨域
		// 在网页上我们就可以通过这个链接：http://localhost:8080/warningSocket 来和服务器的WebSocket连接
		stompEndpointRegistry.addEndpoint("/warningSocket").setAllowedOrigins("*").withSockJS();
	}


}
