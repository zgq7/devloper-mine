package com.loper.mine.thirdpart.temu.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RequestBody {

    /**
     * API接口名，形如：bg.*
     */
    private String type;

    /**
     * 已创建成功的应用标志app_key，联系对接运营颁发
     */
    @JsonProperty(value = "app_key")
    @JSONField(name = "app_key")
    private String appKey;

    /**
     * 时间戳，格式为UNIX时间（秒） ，长度10位，当前时间-300秒<=入参时间<=当前时间+300秒
     */
    private String timestamp;

    /**
     * API入参参数签名
     */
    private String sign;

    /**
     * 请求返回的数据格式，可选参数固定为JSON
     */
    @JsonProperty(value = "data_type")
    @JSONField(name = "data_type")
    private String dataType = "JSON";

    /**
     * 用户授权令牌access_token
     */
    @JsonProperty(value = "access_token")
    @JSONField(name = "access_token")
    private String accessToken;

    /**
     * API版本，默认为V1，无要求不传此参数
     */
    private String version = "V1";

}
