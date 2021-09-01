package com.loper.mine.controller.bases;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.loper.mine.utils.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zgq7 on 2019/7/9.
 */
public class BaseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exception(HttpServletRequest request, Exception e) {
        logger.error("请求异常：url->{} , 参数->{}", request.getRequestURL(), JSON.toJSONString(request.getParameterMap()));
        ResponseResult<Object> responseResult = new ResponseResult<>();
        if (e instanceof ServiceException) {
            ServiceException serviceException = (ServiceException) e;
            responseResult.setCode(serviceException.getCode());
            responseResult.setMsg(serviceException.getMsg());
            logger.error("异常信息：{}", JSONObject.toJSON(responseResult));
            return response(responseResult);
        }
        e.printStackTrace();
        responseResult.setCode(ServiceException.Problems.EXCEPTION.getCode());
        responseResult.setMsg(ServiceException.Problems.EXCEPTION.getMsg());
        return response(responseResult);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        ResponseResult<Void> result = ResponseResult.build();
        String msg = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        result.fail(msg);
        logger.error("异常信息：{}", msg);
        return response(result);
    }

    /**
     * 不过滤
     **/
    public <T> ResponseEntity<String> response(ResponseResult<T> result) {
        return response(result, null);
    }

    /**
     * 按选择过滤
     **/
    public <T> ResponseEntity<String> response(ResponseResult<T> result, SerializeFilter[] serializeFilters) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(JSON.toJSONString(result, serializeFilters));
    }

}
