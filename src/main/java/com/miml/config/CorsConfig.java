package com.miml.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
	
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true); // 자격 증명(쿠키, 인증 헤더 등)을 허용
        corsConfiguration.addAllowedHeader("*"); // 모든 header 응답을 허용
        corsConfiguration.addAllowedMethod("*"); // 모든 HttpMethod 허용
        corsConfiguration.addAllowedOriginPattern("*"); // 모든 출처에 대해 요청을 허용
        source.registerCorsConfiguration("/**", corsConfiguration); // 모든 경로에 대해 위에서 설정한 CORS 설정을 등록
        return new CorsFilter(source);
    }
    
}