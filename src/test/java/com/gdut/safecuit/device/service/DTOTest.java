package com.gdut.safecuit.device.service;

import com.gdut.safecuit.BaseTest;
import com.gdut.safecuit.common.util.LogUtil;
import com.gdut.safecuit.monitor.common.dto.WarningDTO;
import com.gdut.safecuit.monitor.web.webservice.service.InteractiveWarning;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;

import static com.gdut.safecuit.monitor.common.EventCode.CIRCUIT_WARNING;

/**
 * Created by Garson in 14:13 2018/1/30
 * Description :
 */
public class DTOTest extends BaseTest{

	@Resource
	private InteractiveWarning interactiveWarning;

	@Test
	public void test(){
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		Client client = dcf.createClient("http://10.21.23.141:8080/services/safecuit-website/ws?wsdl");
		WarningDTO warningDTO = new WarningDTO();
		warningDTO.setId(1);
		warningDTO.setDeviceId(1);
		warningDTO.setCircuitNo(1);
		warningDTO.setDate(new Date());
		warningDTO.setEventCode(CIRCUIT_WARNING);
		try {

			for (int i = 0 ;i < 5 ;i++){
				Object object = client.invoke("getWarningInfoFromDevice" ,warningDTO);
			}

		//	client.invoke("getWarningInfoFromDevice" ,warningDTO);
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.error(this.getClass() ,"调用硬件端接口出异常" ,e);
		}
	}

	@Test
	public void test1(){
		WarningDTO warningDTO = new WarningDTO();
		warningDTO.setId(1);
		warningDTO.setDeviceId(1);
		warningDTO.setCircuitNo(1);
		warningDTO.setDate(new Date());
		warningDTO.setEventCode(CIRCUIT_WARNING);

		interactiveWarning.getWarningInfoFromDevice(warningDTO);
	}
}
