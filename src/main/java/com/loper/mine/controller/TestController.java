package com.loper.mine.controller;

import com.loper.mine.controller.bases.BaseController;
import com.loper.mine.controller.bases.ResponseResult;
import com.loper.mine.controller.dto.TestDto;
import com.loper.mine.model.Aopi;
import com.loper.mine.service.AopiService;
import com.loper.mine.utils.websocket.MessageModel;
import com.loper.mine.utils.websocket.SocketManager;
import com.loper.mine.utils.websocket.SocketServer;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by zgq7 on 2019/6/6.
 */
@RestController
@RequestMapping(value = "/dev")
@Slf4j
public class TestController extends BaseController {

    private static Map<String, Integer> data = new HashMap<>();

    @Resource(name = AopiService.PACKAGE_BEAN_NAME)
    private AopiService aopiService;

    @Resource
    private SocketManager socketManager;
    @Resource
    private SocketServer socketServer;
    @Resource
    private Aopi aopi;

    /**
     * @author Leethea
     * @apiNote 运行过程中修改bean
     * @date 2020/3/6 19:04
     **/
    @PostMapping("/updateBean")
    public void updateBean(@RequestParam String name) {
        aopi.setName(name);
    }


    /**
     * 异常  测试路径
     * Email 测试路径
     **/
    @GetMapping(value = "")
    public Map<Object, Object> get(String name, String p) {
       /* if (1 == 1)
            throw new NullPointerException();*/
        //return ImmutableMap.of("code", aopiService.getAopList());
        return ImmutableMap.of("code", aopi);
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
     * JSR330 接口校验测试
     **/
    @PostMapping(value = "s")
    public Map<Object, Object> s(@Validated({TestDto.add.class}) @RequestBody TestDto testDto) {
        System.out.println(testDto);
        return ImmutableMap.of("code", "post");
    }

    @GetMapping(value = "st")
    public ResponseEntity<String> st() {
        for (int i = 0; i < 10000000; i++) {
            data.put(new Random().toString() + i, i);
        }
        Aopi aopi = new Aopi("st", 1);
        return response(ResponseResult.success(aopi));
    }


}

