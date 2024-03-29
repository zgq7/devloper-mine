package com.loper.mine;

import com.loper.mine.model.Aopi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.context.annotation.Bean;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author Leethea
 * @Description @NacosPropertySource(dataId = "devloper-mine", autoRefreshed = true) 动态刷新
 * @Date 2019/12/31 16:30
 **/
@SpringBootApplication(exclude = QuartzAutoConfiguration.class)
@MapperScan(value = "com.loper.mine.mapper.mappers")
//@NacosPropertySource(dataId = "devloper-mine", autoRefreshed = true)
public class DevloperMineApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevloperMineApplication.class, args);

    }

    @Bean
    public Aopi aopi() {
        return new Aopi("32432423", 23);
    }
}
