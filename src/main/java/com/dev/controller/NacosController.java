package com.dev.controller;

import com.dev.controller.bases.BaseController;
import com.dev.controller.bases.ResponseResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Leethea_廖南洲
 * @date 2020/1/18 15:23
 * @vesion 1.0
 **/
@RestController
@RequestMapping(value = "/config")
public class NacosController extends BaseController {

	//@NacosValue("${useLocalCache:false}", autoRefreshed = true)
	private Boolean useLocalCache1;

	//@Value("${useLocalCache}")
	private String useLocalCache2;

	/**
	 * 功能：nacos 测试
	 *
	 * @author Leethea
	 * @date 2020/1/18 15:24
	 **/
	@GetMapping(value = "/nacos")
	public ResponseEntity<String> nacos() {
		ResponseResult responseResult = new ResponseResult();
		responseResult.success(useLocalCache1);
		return response(responseResult);
	}

	/**
	 * 功能：apollo测试
	 *
	 * @author Leethea
	 * @date 2020/1/18 15:25
	 **/
	@GetMapping(value = "/apollo")
	public ResponseEntity<String> apollo() {
		ResponseResult responseResult = new ResponseResult();
		responseResult.success(useLocalCache2);
		return response(responseResult);
	}


}
