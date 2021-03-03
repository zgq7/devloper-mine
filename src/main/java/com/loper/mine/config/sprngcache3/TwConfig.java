package com.loper.mine.config.sprngcache3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liaonanzhou
 * @date 2020-11-20 09:40
 * @description 相当于config
 */
@Configuration
public class TwConfig {

    @Bean
    public TubunComponent tubunComponent(DenpaComponent denpaComponent) {
        return new TubunComponent(denpaComponent);
    }
}
