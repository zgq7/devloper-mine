package com.dev.controller.bases;

import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

/**
 * Created on 2019-07-31 14:02.
 *
 * @author zgq7
 */
public class ResponseResult<T> {

    private int code;

    private List<T> data;

    private String msg;

    public ResponseResult<T> emptys() {
        this.code = HttpStatus.OK.value();
        this.data = Collections.EMPTY_LIST;
        this.msg = HttpStatus.OK.getReasonPhrase();
        return this;
    }

    public ResponseResult<T> success(List<T> data) {
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

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
