package com.gdut.safecuit.monitor.common.properties;

import com.gdut.safecuit.monitor.web.webservice.service.InteractiveWarningService;
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
	private InteractiveWarningService interactiveWarningService;

	@Bean
	public Endpoint endpoint(){
		EndpointImpl endpoint = new EndpointImpl(bus , interactiveWarningService);
		endpoint.publish("/InteractiveWarningService");
		return endpoint;
	}

}
