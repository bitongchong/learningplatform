package com.sicau.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author liuyuehe
 */
@SpringBootApplication
@EnableCaching
public class MainApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(MainApplicationRunner.class);
    }
}