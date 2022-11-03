package com.ecjtu.onlinemusic.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

/*
 * @description: web请求映射，跨域等
 * @author: jklhlil
 * @create: 2022-06-15
 * @version: 1.0
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${spring.uploadUrl}")
    private String uploadUrl;
    //    路径映射
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //这样所有以files开头的请求都被视为请求上传的文件,映射到真正的路径上去
        registry.addResourceHandler("/files/**").addResourceLocations("file:"+uploadUrl);
    }


//第二种跨域处理方法
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //添加映射
        registry.addMapping("/**")
                .allowCredentials(true)//是否发现cookie
                .allowedOriginPatterns("*")//设置放行哪些原始域
                .allowedMethods(new String[]{"GET", "POST","PUT","DELETE"})//设置放行哪些请求方式
                .allowedHeaders("*")//设置放行哪些原始请求头信息
                .exposedHeaders("*");//暴露哪些原始请求头信息
    }

}

