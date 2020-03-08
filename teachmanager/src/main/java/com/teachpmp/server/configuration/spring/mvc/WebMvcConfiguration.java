package com.teachpmp.server.configuration.spring.mvc;

import com.teachpmp.server.configuration.property.SystemConfig;
import com.teachpmp.server.configuration.spring.interceptor.WebContextInterceptor;
import com.teachpmp.server.configuration.spring.wx.TokenHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;


@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    @Autowired
    private WebContextInterceptor webContextInterceptor;
    @Autowired
    private TokenHandlerInterceptor tokenHandlerInterceptor;
    @Autowired
    private SystemConfig systemConfig;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/index.html");
        registry.addRedirectViewController("/student", "/index.html");
        registry.addRedirectViewController("/admin", "/index.html");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(webContextInterceptor);

        List<String> securityIgnoreUrls = systemConfig.getWx().getSecurityIgnoreUrls();
        String[] ignores = new String[securityIgnoreUrls.size()];
        registry.addInterceptor(tokenHandlerInterceptor)
                .addPathPatterns("/api/wx/**")
                .excludePathPatterns(securityIgnoreUrls.toArray(ignores));
        super.addInterceptors(registry);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedMethods("*")
                .allowedOrigins("*")
                .allowedHeaders("*");
        super.addCorsMappings(registry);
    }

}
