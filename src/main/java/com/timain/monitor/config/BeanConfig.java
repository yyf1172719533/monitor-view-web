package com.timain.monitor.config;

import com.linkage.system.utils.corba.CorbaMsgSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/4 16:19
 */
@Configuration
public class BeanConfig {

    @Bean
    public CorbaMsgSupport corbaMsgSupport() {
        return new CorbaMsgSupport();
    }
}
