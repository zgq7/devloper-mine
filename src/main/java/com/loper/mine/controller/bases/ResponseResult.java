package com.loper.mine.controller.bases;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * Created on 2019-07-31 14:02.
 *
 * @author zgq7
 */
@Data
public class ResponseResult<T> {

    private int code;

    private T data;

    private String msg;

    public void empty() {
        this.code = 3000;
        this.msg = "查询为空";
    }

    public void fail() {
        fail("操作失败");
    }


    public void fail(String msg) {
        this.code = 3000;
        this.msg = msg;
    }

    public void success() {
        this.code = HttpStatus.OK.value();
        this.msg = HttpStatus.OK.getReasonPhrase();
    }

    public static <T> ResponseResult<T> build() {
        return new ResponseResult<>();
    }

    public static <T> ResponseResult<T> success(T data) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setCode(HttpStatus.OK.value());
        result.setData(data);
        result.setMsg(HttpStatus.OK.getReasonPhrase());
        return result;
    }

    public static ResponseResult<Void> voidSuccess() {
        ResponseResult<Void> result = build();
        result.success();
        return result;
    }


}