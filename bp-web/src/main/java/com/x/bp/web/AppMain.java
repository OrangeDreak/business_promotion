package com.x.bp.web;

import cn.hutool.extra.spring.EnableSpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.x", "cn.hutool.extra"})
@MapperScan({"com.x.**.mapper"})
@EnableSpringUtil
@EnableAsync
@EnableScheduling
@Slf4j
public class AppMain {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(AppMain.class);
        application.addListeners(new ApplicationPidFileWriter());
        try {
            application.run(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("Application 启动成功");
    }
}