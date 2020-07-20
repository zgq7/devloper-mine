package com.dev.service;

/**
 * @author leethea
 * @version 1.0
 * @date 2020-07-08 10:01:03
 */
public class TestServiceLamdba {


	public String sayname(String name, TestService testService) {
		if (name.equals("贺江立")) {
			return testService.sayname(name);
		} else {
			return testService.saygoodbye(name);
		}
	}

}
