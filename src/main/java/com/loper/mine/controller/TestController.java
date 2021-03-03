package com.loper.mine.controller;

import com.alibaba.fastjson.JSON;
import com.loper.mine.controller.bases.BaseController;
import com.loper.mine.controller.bases.ResponseResult;
import com.loper.mine.controller.dto.TestDto;
import com.loper.mine.model.Aopi;
import com.loper.mine.service.AopiService;
import com.loper.mine.utils.socket.MsgModel;
import com.loper.mine.utils.socket.io.IOSocketServer;
import com.loper.mine.utils.websocket.MessageModel;
import com.loper.mine.utils.websocket.SocketManager;
import com.loper.mine.utils.websocket.SocketServer;
import com.google.common.collect.ImmutableMap;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by zgq7 on 2019/6/6.
 */
@RestController
@RequestMapping(value = "/dev")
public class TestController extends BaseController {

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
		//return null;
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
		ResponseResult<Aopi> result = new ResponseResult<>();

		Aopi aopi = new Aopi("st", 1);
		return response(result.success(aopi));
	}


}

