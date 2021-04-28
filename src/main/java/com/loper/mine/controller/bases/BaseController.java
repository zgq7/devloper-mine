package com.loper.mine.controller.bases;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.loper.mine.utils.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zgq7 on 2019/7/9.
 */
public class BaseController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> exception(HttpServletRequest request, Exception e) {
		ResponseResult<Object> responseResult = new ResponseResult<>();
		if (e instanceof ServiceException) {
			ServiceException serviceException = (ServiceException) e;
			logger.error("请求异常：url->{} , 参数->{}", request.getRequestURL(), JSON.toJSONString(request.getParameterMap()));
			responseResult.setCode(serviceException.getCode());
			responseResult.setMsg(serviceException.getMsg());
			return response(responseResult);
		}
		logger.info("异常堆栈：{}", e);
		responseResult.setCode(ServiceException.Problems.EXCEPTION.getCode());
		responseResult.setMsg(ServiceException.Problems.EXCEPTION.getMsg());
		return response(responseResult);
	}

	/**
	 * 不过滤
	 **/
	public <T> ResponseEntity<String> response(ResponseResult<T> result) {
		return ResponseEntity.ok(JSON.toJSONString(result));
	}

	/**
	 * 按选择过滤
	 **/
	public <T> ResponseEntity<String> response(ResponseResult<T> result, SerializeFilter[] serializeFilters) {
		return ResponseEntity.ok(JSON.toJSONString(result, serializeFilters));
	}

}
