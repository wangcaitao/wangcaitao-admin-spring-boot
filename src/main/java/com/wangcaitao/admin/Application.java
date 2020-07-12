package com.wangcaitao.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wangcaitao
 */
@SpringBootApplication(scanBasePackages = {
        "com.wangcaitao.starter",
        "com.wangcaitao.admin"
})
@MapperScan(basePackages = {
        "com.wangcaitao.admin.**.mapper"
})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
