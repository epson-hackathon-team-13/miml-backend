package com.miml.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 스웨거 기본정보 설정
 */
@Configuration
public class SwaggerConfig {

    @Bean(name="openAPI")
    public OpenAPI openAPI() {

        return new OpenAPI().info(new Info()
                .title("Ballagain-API definition")
                .version("v1")
        );
    }
}