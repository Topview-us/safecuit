package com.gdut.safecuit.monitor.common.properties;

import com.gdut.safecuit.monitor.web.webservice.service.InteractiveWarning;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.xml.ws.Endpoint;

/**
 * Created by Garson in 14:52 2018/1/24
 * Description :
 */
@Configuration
@Component
public class CxfConfig {

	@Resource
	private Bus bus;

	@Resource
	private InteractiveWarning interactiveWarning;

	@Bean
	public Endpoint endpoint(){
		EndpointImpl endpoint = new EndpointImpl(bus , interactiveWarning);
		endpoint.publish("/safecuit-website/ws");//客户端接受的接口，此处为ip:端口/services/safecuit-website/ws?wsdl
		return endpoint;
	}

}
