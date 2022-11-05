package com.timain.monitor.config;

import com.linkage.system.corbabus._CorbaBusAdpaterStub;
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
        CorbaMsgSupport corbaMsgSupport = new CorbaMsgSupport();
        corbaMsgSupport.setCorbaBusAdpater(new _CorbaBusAdpaterStub());
        corbaMsgSupport.setCharset("ISO8859-1");
        return corbaMsgSupport;
    }
}
