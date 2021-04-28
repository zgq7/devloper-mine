package com.loper.mine.controller.bases;

import org.springframework.http.HttpStatus;

/**
 * Created on 2019-07-31 14:02.
 *
 * @author zgq7
 */
public class ResponseResult<T> {

    private int code;

    private T data;

    private String msg;

    public ResponseResult<T> emptys() {
        this.code = HttpStatus.OK.value();
        this.data = null;
        this.msg = HttpStatus.OK.getReasonPhrase();
        return this;
    }

    public ResponseResult<T> success(T data) {
        this.code = HttpStatus.OK.value();
        this.data = data;
        this.msg = HttpStatus.OK.getReasonPhrase();
        return this;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
