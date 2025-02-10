package com.gmail.merikbest2015.ecommerce.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfiguration {

    /**
     * 定義全局 OpenAPI 信息
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Ecommerce API Documentation")
                        .version("1.0")
                        .description("Ecommerce API endpoints for managing the application")
                        .contact(new Contact().name("Support").email("support@example.com")))
                .servers(List.of(new Server().url("http://localhost:8080").description("Local Server")));
    }

    /**
     * 分組 API 配置
     */
    @Bean
    public GroupedOpenApi apiGroup() {
        return GroupedOpenApi.builder()
                .group("ecommerce")
                .pathsToMatch("/api/**") // 匹配所有 API 路徑
                .build();
    }
}
