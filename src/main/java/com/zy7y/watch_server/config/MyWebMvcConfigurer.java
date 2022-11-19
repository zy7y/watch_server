package com.zy7y.watch_server.config;

import com.zy7y.watch_server.interceptor.JWTInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {
    // https://blog.csdn.net/abc_123456___/article/details/94647705
    @Bean
    public JWTInterceptor jwtInterceptor(){
        return new JWTInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .excludePathPatterns("/login") // 登录接口不用于token验证
                .excludePathPatterns("/register")
                .addPathPatterns("/**"); // 其他非登录接口都需要进行token验证
    }
}
