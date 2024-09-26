package com.loper.mine.thirdpart.temu;

import com.loper.mine.thirdpart.temu.configuration.TemuApiConfiguration;
import com.loper.mine.thirdpart.temu.request.GoodsBrandGetRequest;
import com.loper.mine.thirdpart.temu.response.GoodsBrandGetResponse;
import com.loper.mine.thirdpart.temu.response.ResponseBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        url = "${temuApi.url:https://kj-openapi.temudemo.com/openapi/router}",
        contextId = "temuOpenApi",
        name = "temuOpenApi",
        configuration = {TemuApiConfiguration.class}
)
public interface TemuOpenApi {

    @PostMapping(value = "")
    ResponseBody<GoodsBrandGetResponse> goodsBrandsGet(@RequestBody GoodsBrandGetRequest request);


}
