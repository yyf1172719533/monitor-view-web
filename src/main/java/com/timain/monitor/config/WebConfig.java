package com.timain.monitor.config;

import com.timain.monitor.interceptor.MonitorInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/3 19:44
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(monitorInterceptor())
                .addPathPatterns("/**");
    }

    @Bean
    public MonitorInterceptor monitorInterceptor() {
        return new MonitorInterceptor();
    }
}
