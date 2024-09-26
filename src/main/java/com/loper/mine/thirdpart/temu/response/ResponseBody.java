package com.loper.mine.thirdpart.temu.response;

import lombok.Data;

@Data
public class ResponseBody<T> {

    private boolean success;

    private Integer errorCode;

    private String errorMsg;

    private T result;

    private String requestId;

}
