package com.loper.mine.utils.exception;

import java.io.Serializable;

/**
 * Created on 2019-07-31 14:35.
 *
 * @author zgq7
 */
public class ServiceException extends RuntimeException implements Serializable {

    private static final long serializableUID = 1L;

    private int code;

    private String msg;

    public ServiceException() {
        super();
    }

    public ServiceException(String msg) {
        this.msg = msg;
    }

    public ServiceException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ServiceException(String message, int code, String msg) {
        super(message);
        this.code = code;
        this.msg = msg;
    }

    public static long getSerializableUID() {
        return serializableUID;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    //------------------------------------------

    /**
     * 业务枚举
     **/
    public enum Problems {
        EXCEPTION(3000, "RuntimeException 服务异常"),
        NOT_KNOWN(3001, "Not known error");

        int code;
        String msg;

        Problems(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return this.code;
        }

        public String getMsg() {
            return this.msg;
        }
    }
}
