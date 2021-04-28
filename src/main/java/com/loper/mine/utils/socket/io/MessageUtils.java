package com.loper.mine.utils.socket.io;

import com.alibaba.fastjson.JSON;
import com.loper.mine.config.LocalThreadPool;
import com.loper.mine.utils.socket.MsgModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created on 2019-08-14 16:57.
 *
 * @author zgq7
 */
public class MessageUtils {

	private static final Logger logger = LoggerFactory.getLogger(MessageUtils.class);

	/**
	 * 接收客户端字符串消息
	 * 带写字板
	 **/
	protected static void recieveTextMsg(Socket socket) throws IOException {
		InputStream inputStream = socket.getInputStream();
		DataInputStream dataInputStream = new DataInputStream(inputStream);
		while (true) {
			String msg = dataInputStream.readUTF();
			logger.info("来自：{} 的新消息：{}", JSON.parseObject(msg, MsgModel.class).getIp(), JSON.parseObject(msg, MsgModel.class).getMsg());
		}
	}

	/**
	 * 接收客户端字符串消息
	 * 带写字板
	 **/
	protected static void recieveSendTextMsg(Socket socket) throws IOException {
		InputStream inputStream = socket.getInputStream();
		DataInputStream dataInputStream = new DataInputStream(inputStream);

		while (true) {
			String msg = dataInputStream.readUTF();
			logger.info("新消息：{}", msg);

			if (msg != null && isMsgJson(msg)) {
				MsgModel msgModel = JSON.parseObject(msg, MsgModel.class);
				LocalThreadPool.getInstance().execute(() -> {
					try {
						if (IOSocketServer.socketMap.get(msgModel.getIp()) != null) {
							msgModel.setStatu(true);
							sendTextMsg(IOSocketServer.socketMap.get(msgModel.getIp()), JSON.toJSONString(msgModel));
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			}
		}
	}

	/**
	 * 向客户端发送字符串消息
	 **/
	protected static void sendTextMsg(Socket socket) throws IOException {
		OutputStream outputStream = socket.getOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
		while (true) {
			String msg = new Scanner(System.in).nextLine();
			dataOutputStream.writeUTF(msg);
		}
	}

	/**
	 * 向客户端发送字符串消息
	 * 外部传入消息
	 **/
	protected static void sendTextMsg(Socket socket, String msg) throws IOException {
		OutputStream outputStream = socket.getOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
		dataOutputStream.writeUTF(msg);
	}

	private static boolean isMsgJson(String jsonStr) {
		try {
			if (JSON.parseObject(jsonStr, Object.class) instanceof MsgModel)
				return true;
		} catch (Exception e) {
			return false;
		}
		return false;
	}

}
