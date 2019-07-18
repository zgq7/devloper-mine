package com.dev.controller;

import com.google.common.collect.ImmutableMap;
import okhttp3.*;
import org.apache.commons.io.FileUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.Map;

/**
 * Created by zgq7 on 2019/6/6.
 */
@RestController
@RequestMapping(value = "/dev")
public class TestController extends BaseController {

    @GetMapping(value = "")
    public Map<Object, Object> get() {
        return ImmutableMap.of("code", "get");
    }

    @PutMapping(value = "t")
    public Map<Object, Object> t() {
        return ImmutableMap.of("code", "put");
    }

    @PostMapping(value = "s")
    public Map<Object, Object> s() {
        return ImmutableMap.of("code", "post");
    }

    public static void main(String[] args) throws IOException {


        String url = "https://img.lyiqk.cn/pure/1.jpg";

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().get()
                .url(url).build();

        Call call = client.newCall(request);

        Response response = client.newCall(request).execute();


        InputStream inputStream;
        DataOutputStream dataOutputStream;

        try {

            String path = new File("").getCanonicalPath() + File.separator + "images" + File.separator + "1.jpg";

            File file = new File(path);

            //FileUtils.touch(file);

            System.out.println(path);

            inputStream = response.body().byteStream();

            int len = inputStream.available();
            if (len == 0) {
                System.out.println("inputStream is zero !");
                return;
            }

            dataOutputStream = new DataOutputStream(new FileOutputStream(file));

            if (len <= 1024 * 1024) {
                byte[] bytes = new byte[len];
                inputStream.read(bytes);
                dataOutputStream.write(bytes);
            } else {
                int byteCount;
                byte[] bytes = new byte[1024 * 1024];
                while ((byteCount = inputStream.read(bytes)) != -1) {
                    dataOutputStream.write(bytes, 0, byteCount);
                }
            }

            dataOutputStream.flush();
            inputStream.close();
            dataOutputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}

