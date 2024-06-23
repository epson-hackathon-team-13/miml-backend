package com.miml.security.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miml.security.CustomAuthenticationToken;
import com.miml.security.dto.SiginUpDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import java.util.stream.Collectors;

public class AjaxAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    // @Value("${login-url}")
    private static final String URL = "/api/login";
    
    private ObjectMapper objectMapper = new ObjectMapper();
    
    public AjaxAuthenticationFilter() {
        // URL과 일치하면 필터 동작
        super(new AntPathRequestMatcher(URL));
    }
    
    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request, 
            HttpServletResponse response
    ) throws AuthenticationException, IOException, ServletException {

        if (!"POST".equals(request.getMethod())) {
            throw new IllegalStateException("Authentication is not supported");
        }

        // 입력 스트림을 한 번 읽고 문자열로 저장
        String json = new BufferedReader(new InputStreamReader(request.getInputStream()))
                .lines().collect(Collectors.joining("\n"));

        // 저장된 문자열을 사용하여 SiginUpDto로 디시리얼라이즈
        SiginUpDto siginUpDto = objectMapper.readValue(json, SiginUpDto.class);

        // ID, PASSWORD가 있는지 확인
        if (!StringUtils.hasLength(siginUpDto.getEmail()) 
                || !StringUtils.hasLength(siginUpDto.getPassword())) {
            throw new IllegalArgumentException("username or password is empty");
        }

        // 처음에는 인증되지 않은 토큰 생성
        CustomAuthenticationToken token = new CustomAuthenticationToken(
                siginUpDto.getEmail(),
                siginUpDto.getPassword()
        );

        // Manager에게 인증 처리
        return getAuthenticationManager().authenticate(token);
    }
}
