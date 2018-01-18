package com.gdut.safecuit.device.po;

/**
 * Created by Garson in 9:56 2018/1/18
 * Description :回路po
 */
public class Circuit {

	private int id;
	private int circuitNo;//回路序号
	private int deviceId;//外键，设备id

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCircuitNo() {
		return circuitNo;
	}

	public void setCircuitNo(int circuitNo) {
		this.circuitNo = circuitNo;
	}

	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}
}
