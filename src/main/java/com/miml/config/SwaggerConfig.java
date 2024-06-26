package com.miml.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

/**
 * 스웨거 기본정보 설정
 */
@Configuration
public class SwaggerConfig {

	@Bean(name = "openAPI")
	public OpenAPI openAPI() {

		Server server = new Server();
		server.setUrl("https://api.ballagain.win/"); // https://에 접근 가능하게 설정

		return new OpenAPI()
				.info(new Info().title("Ballagain-API definition").version("v1"))
				.servers(List.of(server));
	}
}