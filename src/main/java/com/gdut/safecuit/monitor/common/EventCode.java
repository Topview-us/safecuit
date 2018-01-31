package com.gdut.safecuit.monitor.common;
/**
 * Created by Garson in 16:15 2018/1/24
 * Description :
 */
public class EventCode {

	//设备的状态
	public static final int EQPT_OFFLINE = 0;//设备下线
	public static final int EQPT_ONLINE = 1;//设备上线

	//回路的状态
	public final static int CIRCUIT_WARNING = 13;//报警
	public final static int CIRCUIT_FAULT = 12;//故障
	public final static int CIRCUIT_NORMAL = 10;//正常
	public final static int CIRCUIT_UNCONNECTED = 11;//未连接

	//报警类型的编码
	public final static int CURRENT_EXCESS = 201;//电流超阈值
	public final static int TEMPERATURE_EXCESS = 202;//温度超阈值
	public static final int MILI_CURRENT_EXCESS = 203;  //漏电流超阈值

	//报警语音信息
	public final static String CIRCUIT_WARNING_AUDIO = "回路报警";
	public final static String CIRCUIT_FAULT_AUDIO = "回路故障";
	public final static String CIRCUIT_UNCONNECTED_AUDIO = "回路未连接";

	//报警处理状态
	public static final int UNSOLVED = 0;
	public static final int IGNORE = 1;
	public static final int MISTAKE = 2;
	public static final int SOLVED = 3;

	//已报警
	public static final int IS_WARNING = 1;



}
