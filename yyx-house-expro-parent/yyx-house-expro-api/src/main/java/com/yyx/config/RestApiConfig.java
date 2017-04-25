package com.yyx.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by zhangya on 2017/4/25.
 */
/*
 * Restful API 访问路径:
 * http://IP:port/{context-path}/swagger-ui.html
 * eg:http://localhost:8080/jd-config-web/swagger-ui.html
 */
@EnableWebMvc
@EnableSwagger2
@ComponentScan
@Configuration
public class RestApiConfig {



    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build().apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo("Rest API",
                "Rest API Example",
                "1",
                "",
                new Contact("Edwin", "http://edwin.baculsoft.con", "edwin@baculsoft.com"),
                "Apache License",
                "");
        return apiInfo;
//        return new ApiInfoBuilder()
//                .title("Spring 中使用Swagger2构建RESTful APIs")
//                .termsOfServiceUrl("http://blog.csdn.net/he90227")
//                .contact("")
//                .version("1.1")
//                .build();
    }
}