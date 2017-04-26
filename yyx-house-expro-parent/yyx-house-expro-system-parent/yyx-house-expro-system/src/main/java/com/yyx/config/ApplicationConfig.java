package com.yyx.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by zhangya on 2017/4/24.
 */
@Configuration
@ComponentScan(basePackages = "com.yyx.*", includeFilters = @ComponentScan.Filter(classes = org.springframework.stereotype.Controller.class, type = FilterType.ANNOTATION))
@EnableAutoConfiguration
@ImportResource({ "classpath:spring-config.xml","classpath:spring-dubbo.xml"})
public class ApplicationConfig {
    public void customize(ConfigurableEmbeddedServletContainer container) {
        container.setPort(8186);

    }
}
