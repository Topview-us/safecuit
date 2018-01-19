package com.gdut.safecuit.device.common;

/**
 * Created by Garson in 20:43 2018/1/19
 * Description : 设备各种状态编码
 */
public class DeviceStatus {

	//设备的状态
	public static final int EQPT_OFFLINE = 0;//设备下线
	public static final int EQPT_ONLINE = 1;//设备上线

	//回路的状态
	public final static int WARNING = 101;//报警
	public final static int FAULT = 102;//故障
	public final static int NORMAL = 103;//正常
	public final static int UNCONNECTED = 104;//未连接

	//报警类型的编码
	public final static int CURRENT_EXCESS = 201;//电流超阈值
	public final static int TEMPERATURE_EXCESS = 202;//温度超阈值
	public static final int MILI_CURRENT_EXCESS = 203;  //漏电流超阈值
	public final static int SMOKE_WARNING = 204;//烟雾敏感

	//事件处理状态
	public final static int UNDEALT = 301;//未处理

}
