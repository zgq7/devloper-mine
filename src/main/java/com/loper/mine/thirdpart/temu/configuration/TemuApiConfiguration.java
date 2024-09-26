package com.loper.mine.thirdpart.temu.configuration;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import feign.FeignException;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Response;
import feign.Util;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

@Slf4j
public class TemuApiConfiguration implements Decoder, ErrorDecoder, RequestInterceptor {

    private static final ParserConfig CONFIG = new ParserConfig();

    public Exception decode(String methodKey, Response response) {
        return new RuntimeException("temu api error");
    }

    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
        String stringValue = Util.toString(response.body().asReader(StandardCharsets.UTF_8));
        return JSONObject.parseObject(stringValue, type, CONFIG);
    }

    public void apply(RequestTemplate template) {
        String url = template.feignTarget().url();

        log.info("调用temu 地址:{} \n body: {}", url, new String(template.body()));
    }

}