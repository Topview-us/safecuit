package com.gdut.safecuit.monitor.web.webservice.service;

import com.gdut.safecuit.common.Result;
import com.gdut.safecuit.common.util.LogUtil;
import com.gdut.safecuit.device.common.po.Device;
import com.gdut.safecuit.device.common.po.ElectricBox;
import com.gdut.safecuit.device.service.DeviceService;
import com.gdut.safecuit.device.service.ElectricBoxService;
import com.gdut.safecuit.monitor.common.dto.WarningDTO;
import com.gdut.safecuit.monitor.common.util.BaiduString2AudioUtil;
import com.gdut.safecuit.monitor.web.websocket.WarningSocketController;
import org.json.JSONException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jws.WebService;
import java.util.Map;

import static com.gdut.safecuit.monitor.common.EventCode.*;

/**
 * Created by Garson in 14:54 2018/1/24
 * Description : 硬件软件端交互的webservice类
 */
@Component
@WebService(serviceName = "InteractiveServiceImpl"
		,targetNamespace = "http://controller.web.monitor.safecuit.gdut.com"
		,endpointInterface = "com.gdut.safecuit.monitor.web.webservice.service.InteractiveWarningService")
public class InteractiveWarningServiceImpl implements InteractiveWarningService {

	@Resource
	private DeviceService deviceService;
	@Resource
	private ElectricBoxService electricBoxService;
	@Resource
	private WarningSocketController warningSocketController;

	@Override
	public Result<Integer> getWarningInfoFromDevice(WarningDTO warningDTO) {
		if(warningDTO.getEventCode() == EQPT_ONLINE)
			//将设备修改为上线状态
			deviceService.updateIsOnline(warningDTO.getDeviceId() ,EQPT_ONLINE);
		else if (warningDTO.getEventCode() == EQPT_OFFLINE)
			//将设备修改为下线状态
			deviceService.updateIsOnline(warningDTO.getDeviceId() ,EQPT_OFFLINE);
		else {

			Device device = deviceService.selectByPrimaryKey(warningDTO.getDeviceId());
			ElectricBox electricBox = electricBoxService.selectByPrimaryKey(device.getElectricBoxId());
			Map<String ,String> orgInfo = electricBoxService.searchOrgInfo(electricBox.getOrgId());
			//语音提醒
			try {
				String message = "有一则报警信息: 报警设备编号:" + device.getCode() + ",电箱地址:" + orgInfo.get("address")
						+ ",所属机构:" + orgInfo.get("orgName");

				warningSocketController.PushWarningAudio(BaiduString2AudioUtil.getAudioByte(message));


			} catch (JSONException e) {
				e.printStackTrace();
				LogUtil.error(this.getClass() ,"语音转换抛异常\n" ,e);
				return new Result<>(-1 ,"语音转换异常" ,false ,500);

			}
		}

		return new Result<>(1 ,"报警信息接受成功" ,true ,200);

	}
}
