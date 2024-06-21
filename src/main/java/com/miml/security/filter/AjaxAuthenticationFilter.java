package com.miml.security.filter;

import java.io.IOException;

import com.miml.security.CustomAuthenticationToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miml.security.dto.SiginUpDto;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AjaxAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	
//	@Value("${login-url}")
	private static final String URL = "/api/login";
	
    private ObjectMapper objectMapper = new ObjectMapper();
    
    public AjaxAuthenticationFilter() {
    	// url과 일치하면 필더 동작
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

        // POST 이면 body 를 AccountDto( 로그인 정보 DTO ) 에 매핑
    	SiginUpDto siginUpDto = objectMapper.readValue(request.getReader(), SiginUpDto.class);

        // ID, PASSWORD 가 있는지 확인
        if(!StringUtils.hasLength(siginUpDto.getEmail()) 
                || !StringUtils.hasLength(siginUpDto.getPassword())) {
            throw new IllegalArgumentException("username or password is empty");
        }

        // 처음에는 인증 되지 않은 토큰 생성
        CustomAuthenticationToken token = new CustomAuthenticationToken(
        		siginUpDto.getEmail(),
        		siginUpDto.getPassword()
        );

        // Manager 에게 인증 처리
        Authentication authenticate = getAuthenticationManager().authenticate(token);

        return authenticate;
    }

}