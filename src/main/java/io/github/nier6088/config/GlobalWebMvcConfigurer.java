package io.github.nier6088.config;


import io.github.nier6088.interceptor.RequestHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program: ps-online
 * @ClassName PsOnlineWebMvcConfigurer
 * @description:
 * @author: yangjun
 * @create: 2022-04-07 15:05
 * @Version 1.0
 **/
@Component
public class GlobalWebMvcConfigurer implements WebMvcConfigurer {


    @Autowired
    private RequestHandlerInterceptor requestHandlerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(requestHandlerInterceptor).addPathPatterns("/**");

//                .addPathPatterns("/**")
//                .excludePathPatterns("/sys/admin/login", "/sys/admin/r2");

        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
