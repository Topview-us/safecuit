package com.gdut.safecuit.monitor.web.webservice.client;

import com.gdut.safecuit.common.util.LogUtil;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

/**
 * Created by Garson in 13:32 2018/1/26
 * Description :
 */
public class HandleDeviceExceptionClient {


	public Object handleDeviceException() {
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		Client client = dcf.createClient("");
		Object object = null;

		try {
			object = client.invoke("" ,"");
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.error(this.getClass() ,"调用硬件端接口出异常" ,e);
		}
		return object;
	}
}
