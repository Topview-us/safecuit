package com.gdut.safecuit.monitor.common.po;

/**
 * Created by Garson in 15:23 2018/1/25
 * Description : 含某条回路的温度、漏电流、电流值的对象
 */
public class CircuitDataLog {

	private int circuitNo;//回路序号
	private String value;//值
	private String temperatureValue;//温度值
	private String miliCurrentValue;//漏电流值
	private String currentValue;//电流值

	public int getCircuitNo() {
		return circuitNo;
	}

	public void setCircuitNo(int circuitNo) {
		this.circuitNo = circuitNo;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getTemperatureValue() {
		return temperatureValue;
	}

	public void setTemperatureValue(String temperatureValue) {
		this.temperatureValue = temperatureValue;
	}

	public String getMiliCurrentValue() {
		return miliCurrentValue;
	}

	public void setMiliCurrentValue(String miliCurrentValue) {
		this.miliCurrentValue = miliCurrentValue;
	}

	public String getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(String currentValue) {
		this.currentValue = currentValue;
	}

	public CircuitDataLog(int circuitNo, String value) {
		this.circuitNo = circuitNo;
		this.value = value;
	}

	public CircuitDataLog(int circuitNo, String temperatureValue, String miliCurrentValue, String currentValue) {
		this.circuitNo = circuitNo;
		this.temperatureValue = temperatureValue;
		this.miliCurrentValue = miliCurrentValue;
		this.currentValue = currentValue;
	}
}
