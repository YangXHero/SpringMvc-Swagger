package com.yyx.house.expro.config;

import org.springframework.boot.SpringApplication;

/**
 * Created by zhangya on 2017/4/24.
 */
public class HouseExproApplication {
    public static void main(String[] args) {

        SpringApplication springApplication = new SpringApplication(ApplicationConfig.class);
        springApplication.setWebEnvironment(false);
        springApplication.run(args);
        com.alibaba.dubbo.container.Main.main(args);
    }
}
