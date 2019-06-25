package com.dev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(value = "com.dev.mapper.repositry")
public class DevloperMineApplication {
    public static void main(String[] args) {
        SpringApplication.run(DevloperMineApplication.class, args);
    }

}
