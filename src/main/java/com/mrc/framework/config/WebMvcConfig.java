package com.mrc.framework.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	 private static final String CLASSPATH_RESOURCE_LOCATIONS = "c://source/files/";
	 
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/files/**")
                .addResourceLocations("file:///" + CLASSPATH_RESOURCE_LOCATIONS).setCachePeriod(0); //리눅스 root에서 시작하는 폴더 경로
    }
}
