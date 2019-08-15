package com.dev.controller;

import com.alibaba.fastjson.JSON;
import com.dev.config.LocalThreadPool;
import com.dev.controller.bases.BaseController;
import com.dev.service.AopiService;
import com.dev.utils.exception.ServiceException;
import com.dev.utils.socket.MsgModel;
import com.dev.utils.socket.io.IOSocketServer;
import com.dev.utils.webSocket.MessageModel;
import com.dev.utils.webSocket.SocketManager;
import com.dev.utils.webSocket.SocketServer;
import com.google.common.collect.ImmutableMap;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.io.*;
import java.util.Collections;
import java.util.Map;

/**
 * Created by zgq7 on 2019/6/6.
 */
@RestController
@RequestMapping(value = "/dev")
public class TestController extends BaseController {

    @Resource(name = AopiService.PACKAGE_BEAN_NAME)
    private AopiService aopiService;

    @Autowired
    private SocketManager socketManager;
    @Autowired
    private SocketServer socketServer;

    /**
     * 异常  测试路径
     * Email 测试路径
     **/
    @GetMapping(value = "")
    public Map<Object, Object> get() {
        if (1 == 1)
            throw new NullPointerException();
        return ImmutableMap.of("code", aopiService.getAopList());
    }

    /**
     * webSocket  测试路径
     **/
    @PostMapping(value = "ws/{id}")
    public void ws(@PathVariable(value = "id") String id, @RequestBody Map<Object, Object> body) {
        String msg = (String) body.get("msg");
        MessageModel messageModel = new MessageModel(id, 0, msg);
        SocketManager.singleCast(messageModel);
        SocketManager.boardCast(messageModel);
    }

    /**
     * socket测试路径
     **/
    @PostMapping(value = "sk")
    public void t(@RequestBody Map<Object, Object> body) {
        MsgModel msgModel = JSON.parseObject(JSON.toJSONString(body), MsgModel.class);
        IOSocketServer.openConnection(msgModel);
    }

    @PostMapping(value = "s")
    public Map<Object, Object> s() {
        return ImmutableMap.of("code", "post");
    }

    /**
     * 使用 OKhttp3 下载、爬取数据
     **/
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

