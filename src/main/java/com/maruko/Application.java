package com.maruko;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 〈一句话功能简述〉<br>
 * 〈启动类〉
 *
 * @author maruko
 * @date 2021/7/7 14:31
 * @since 1.0.0
 */
@ComponentScan(basePackages= {"com.maruko.*"})
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
