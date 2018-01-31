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
import javax.jws.WebMethod;
import javax.jws.WebService;

import static com.gdut.safecuit.monitor.common.EventCode.*;

/**
 * Created by Garson in 14:54 2018/1/24
 * Description : 硬件软件端交互的webservice类
 */
@Component("interactiveWarning")
@WebService(targetNamespace = "http://dto.common.monitor.safecuit.gdut.com/")//参数类所在包位置倒转
public class InteractiveWarning {

	@Resource
	private DeviceService deviceService;
	@Resource
	private ElectricBoxService electricBoxService;
	@Resource
	private WarningSocketController warningSocketController;

	@WebMethod
	public Result<Object> getWarningInfoFromDevice(WarningDTO warningDTO) {
		System.out.println("111");
		if(warningDTO.getEventCode() == EQPT_ONLINE)
			//将设备修改为上线状态
			deviceService.updateIsOnline(warningDTO.getDeviceId() ,EQPT_ONLINE);
		else if (warningDTO.getEventCode() == EQPT_OFFLINE)
			//将设备修改为下线状态
			deviceService.updateIsOnline(warningDTO.getDeviceId() ,EQPT_OFFLINE);
		else {
			//语音提醒
			try {
				warningSocketController.PushWarningAudio(BaiduString2AudioUtil.getAudioByte(getMessage(warningDTO)));
			} catch (JSONException e) {
				e.printStackTrace();
				LogUtil.error(this.getClass() ,"语音转换抛异常\n" ,e);
				return new Result<>(null ,"语音转换异常" ,false ,500);

			}
		}

		return new Result<>(null ,"报警信息接受成功" ,true ,200);

	}

	private String getMessage(WarningDTO warningDTO){
		Device device = deviceService.selectByPrimaryKey(warningDTO.getDeviceId());
		ElectricBox electricBox = electricBoxService.selectByPrimaryKey(device.getElectricBoxId());
		String deviceInfo = "有一则报警信息: 报警设备编号:" + device.getCode() + ",电箱地址:" + electricBox.getAddress();

		String warningInfo = "报警原因：";

		if (warningDTO.getEventCode() == CIRCUIT_WARNING)
			warningInfo += "回路序号" + warningDTO.getCircuitNo() + CIRCUIT_WARNING_AUDIO;
		else if (warningDTO.getEventCode() == CIRCUIT_FAULT)
			warningInfo += "回路序号" + warningDTO.getCircuitNo() + CIRCUIT_FAULT_AUDIO;
		else if (warningDTO.getEventCode() == CIRCUIT_UNCONNECTED)
			warningInfo += "回路序号" + warningDTO.getCircuitNo() + CIRCUIT_UNCONNECTED_AUDIO;
		else if (warningDTO.getEventCode() == CURRENT_EXCESS)
			warningInfo += "设备电流超阈值报警";
		else if (warningDTO.getEventCode() == TEMPERATURE_EXCESS)
			warningInfo += "设备温度超阈值报警";
		else
			warningInfo += "设备漏电流超阈值报警;";
		return deviceInfo + warningInfo;
	}
}
