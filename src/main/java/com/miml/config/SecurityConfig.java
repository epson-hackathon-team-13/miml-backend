package com.miml.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;

import com.miml.security.CustomLoginAuthenticationEntryPoint;
import com.miml.security.filter.AjaxAuthenticationFilter;
import com.miml.security.handler.CustomAccessDeniedHandler;
import com.miml.security.handler.CustomAuthenticationFailureHandler;
import com.miml.security.handler.CustomAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    private final CustomLoginAuthenticationEntryPoint authenticationEntryPoint;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    public SecurityConfig(
    		CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler,
			CustomAuthenticationFailureHandler customAuthenticationFailureHandler,
			CustomLoginAuthenticationEntryPoint authenticationEntryPoint,
			AuthenticationConfiguration authenticationConfiguration, 
			CustomAccessDeniedHandler accessDeniedHandler
			) {
		this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
		this.customAuthenticationFailureHandler = customAuthenticationFailureHandler;
		this.authenticationEntryPoint = authenticationEntryPoint;
		this.authenticationConfiguration = authenticationConfiguration;
		this.accessDeniedHandler = accessDeniedHandler;
	}

	@Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
//                        .requestMatchers("/api/user/sign-up", "/api/login").permitAll() // 특정 URL에 permit 설정
//                        .requestMatchers("/api/**").authenticated() // 다른 모든 URL은 인증 필요
                        .anyRequest().permitAll()) // 나머지 요청은 모두 permit
                .addFilterBefore(ajaxAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(config -> config
						.authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler));

        return http.build();
    }

    @Bean
    public AjaxAuthenticationFilter ajaxAuthenticationFilter() throws Exception {
    	AjaxAuthenticationFilter ajaxAuthenticationFilter = new AjaxAuthenticationFilter();
    	ajaxAuthenticationFilter.setAuthenticationManager(authenticationManager());
        ajaxAuthenticationFilter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
        ajaxAuthenticationFilter.setAuthenticationFailureHandler(customAuthenticationFailureHandler);
       
        ajaxAuthenticationFilter.setSecurityContextRepository(
                new DelegatingSecurityContextRepository(
                        new RequestAttributeSecurityContextRepository(),
                        new HttpSessionSecurityContextRepository()
                )
        );

        return ajaxAuthenticationFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}