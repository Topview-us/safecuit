package com.gdut.safecuit.device.web.controller;

import com.gdut.safecuit.common.Result;

/**
 * Created by Garson in 10:19 2018/1/22
 * Description :
 */
public class BaseController {

	public Result<Integer> getResult(Integer i){
		String message;
		int status;
		Boolean isSuccess;

		if(i == -1){
			message = "参数缺失";
			status = 400;
			isSuccess = false;
		} else if(i == -2){
			message = "数据已存在";
			status = 400;
			isSuccess = false;
		} else if(i == 0){
			message = "操作失败，请重试";
			status = 500;
			isSuccess = false;
		} else {
			message = "操作成功";
			status = 200;
			isSuccess = true;
		}

		return new Result<>(i ,message ,isSuccess ,status);
	}

}
