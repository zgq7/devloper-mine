package com.loper.mine.service;

/**
 * @author leethea
 * @version 1.0
 * @date 2020-07-08 09:57:29
 * @apiNote 函数式接口中只能有一个抽象方法，其他必须有默认实现
 */
public interface TestService {

	String sayname(String name);


	default String saygoodbye(String name) {
		return name + " goodbye";
	}

}
