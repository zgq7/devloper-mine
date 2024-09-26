package com.loper.mine;

import com.loper.mine.thirdpart.temu.TemuOpenApi;
import com.loper.mine.thirdpart.temu.helper.TemuSignHelper;
import com.loper.mine.thirdpart.temu.request.GoodsBrandGetRequest;
import com.loper.mine.thirdpart.temu.response.GoodsBrandGetResponse;
import com.loper.mine.thirdpart.temu.response.ResponseBody;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ApplicationTest {

    @Autowired
    private TemuOpenApi temuOpenApi;

    @Test
    public void test() {
        String appKey = "47bb4bb7769e12d9f7aa93cf029fe529";
        String appSecret = "ac0a3e952eaaa5b19c0e615c2ef497f50afa6e49";
        String accessToken = "07didlyk1vy5byxdcut5fvztxer1u1gl77o4qcgpaogx8yu61u1qpmhx";

        GoodsBrandGetRequest request = new GoodsBrandGetRequest();
        request.setAppKey(appKey);
        request.setAccessToken(accessToken);
        request.setTimestamp(String.valueOf(LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond()));
        request.setPage(1);
        request.setPageSize(20);

        String sign = TemuSignHelper.getSign(request, appSecret);
        request.setSign(sign);

        ResponseBody<GoodsBrandGetResponse> responseBody = temuOpenApi.goodsBrandsGet(request);

        System.out.println(responseBody);
    }

}
