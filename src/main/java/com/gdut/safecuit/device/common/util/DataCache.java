package com.gdut.safecuit.device.common.util;

import com.gdut.safecuit.device.common.vo.DataTreeVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Garson in 16:27 2018/1/18
 * Description : 全局变量本地缓存类
 */
public class DataCache {

	/**
	 * 	本地缓存表的数据条数总量分页查询时
	 * 	如果数据不为0则直接使用；如果为0则通过数据库查询
	 */
	public static int DEVICE_TOTAL;//设备总数
	public static int ELECTRIC_BOX_TOTAL;//电箱总数
	public static List<DataTreeVO> ORG_DATATREEVOS;//最下层为机构的数据树
	public static List<DataTreeVO> ELECTRIC_BOX_DATATREEVOS;//最下层为电箱的数据树
	public static List<DataTreeVO> DEVICE_DATATREEVOS;//最下层为设备的数据树



}
