package com.jasong.yanfaq.config;

import com.jasong.yanfaq.config.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**")//拦截所有请求，通过判断token是否合法来决定是否需要登陆
                .excludePathPatterns("/user/login","/user/register","/questions","/swagger-resources/**"
                        ,"/webjars/**"
                        ,"/v2/**"
                        ,"/swagger-ui.html/**","/file/**","/files/**","/questions/getOne","/druid/**","/questions/bySubject");
    }

    @Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }
}
