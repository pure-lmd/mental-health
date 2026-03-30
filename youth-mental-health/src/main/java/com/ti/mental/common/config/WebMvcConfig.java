package com.ti.mental.common.config;

import com.ti.mental.common.interceptor.AdminInterceptor;
import com.ti.mental.common.interceptor.UserInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.annotation.Resource;

/**
 * Web MVC 配置
 *
 * @author Ti
 * @date 2026-02-03
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private AdminInterceptor adminInterceptor;

    @Resource
    private UserInterceptor userInterceptor;

    @Value("${file.upload.path}")
    private String uploadPath;

    /**
     * 配置拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 管理员拦截器
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/api/admin/**")
                .excludePathPatterns("/api/admin/login");

        // 用户拦截器
        registry.addInterceptor(userInterceptor)
                .addPathPatterns("/api/front/**")
                .excludePathPatterns(
                        "/api/front/login",
                        "/api/front/register",
                        "/api/front/banner/list",
                        "/api/front/category/list",
                        "/api/front/knowledge/list",
                        "/api/front/knowledge/*",
                        "/api/front/publicity/list",
                        "/api/front/publicity/*",
                        "/api/front/news/list",
                        "/api/front/news/*",
                        "/api/front/notice/list",
                        "/api/front/notice/*",
                        "/api/front/activity/list",
                        "/api/front/activity/*",
                        "/api/front/test/list",
                        "/api/front/home/**"
                );
    }

    /**
     * 配置静态资源映射
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 上传文件的访问路径，将相对路径转为绝对路径
        String absolutePath = uploadPath;
        java.io.File pathFile = new java.io.File(absolutePath);
        if (!pathFile.isAbsolute()) {
            absolutePath = System.getProperty("user.dir") + java.io.File.separator + absolutePath;
        }
        if (!absolutePath.endsWith("/") && !absolutePath.endsWith("\\")) {
            absolutePath = absolutePath + java.io.File.separator;
        }
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + absolutePath);
    }
}
