package com.sicau.platform.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;
/**
 * @author liuyuehe
 * @description multipart文件大小设置
 * @date 2019/9/12
 */
@Configuration
public class MultipartFileSizeConfiguration {
    @Bean
    public MultipartConfigElement multipartConfigElement(
            @Value("${multipart.maxFileSize}") Integer maxFileSize,
            @Value("${multipart.maxRequestSize}") Integer maxRequestSize) {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 单个文件最大
        factory.setMaxFileSize(DataSize.ofMegabytes(maxFileSize));
        // 设置总上传数据总大小
        factory.setMaxRequestSize(DataSize.ofMegabytes(maxRequestSize));
        return factory.createMultipartConfig();
    }
}
