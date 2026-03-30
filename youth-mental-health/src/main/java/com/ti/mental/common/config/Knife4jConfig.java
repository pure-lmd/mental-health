package com.ti.mental.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Knife4j API文档配置
 *
 * @author Ti
 * @date 2026-02-03
 */
@Configuration
public class Knife4jConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("青少年心理健康管理系统 API")
                        .description("青少年心理健康管理系统接口文档")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Ti")
                                .email("ti@example.com")));
    }
}
