package com.loper.mine.utils.reptile;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Leethea_廖南洲
 * @version 1.0
 * @date 2020/4/6 11:40
 **/
public abstract class BaseReptile {


	/**
	 * 内部类
	 **/
	public static class InnerClass {

		/**
		 * OkHttp3 客户端
		 **/
		public static final OkHttpClient CLIENT = new OkHttpClient.Builder()
				// java.net.ProtocolException: Too many follow-up requests: 21
				.followSslRedirects(false)
				.followRedirects(false)
				// 连接池配置
				.connectTimeout(5, TimeUnit.MINUTES)
				.writeTimeout(5, TimeUnit.MINUTES)
				.readTimeout(5, TimeUnit.MINUTES)
				.connectionPool(new ConnectionPool(0, 30, TimeUnit.MINUTES))
				.build();

		/**
		 * 关闭连接池中所有的连接
		 **/
		public static void shutdownOkHttp() {
			CLIENT.connectionPool().evictAll();
		}

	}

}
