package com.gdut.safecuit.monitor.web.webservice.service;

import com.gdut.safecuit.common.Result;
import com.gdut.safecuit.monitor.common.dto.WarningDTO;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

/**
 * Created by Garson in 14:58 2018/1/24
 * Description :
 */
@WebService(targetNamespace = "http://controller.web.monitor.safecuit.gdut.com")
public interface InteractiveWarningService {

	@WebMethod
	Result<Object> getWarningInfoFromDevice(@WebParam(name = "warningDTO") WarningDTO warningDTO);
}
