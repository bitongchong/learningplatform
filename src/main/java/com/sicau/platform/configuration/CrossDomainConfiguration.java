package com.sicau.platform.configuration;

/**
 * @author liuyuehe
 * @description 跨域请求
 * @date 2019/8/14
 */
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author liuyuehe
 */
@Configuration
public class CrossDomainConfiguration implements WebMvcConfigurer {
    static final String[] ORIGINS = new String[] { "GET", "POST", "PUT", "DELETE" };
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowCredentials(true).allowedMethods(ORIGINS)
                .maxAge(3600);
    }
}

