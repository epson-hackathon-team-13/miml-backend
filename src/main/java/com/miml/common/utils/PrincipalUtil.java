package com.miml.common.utils;

import java.util.Objects;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.miml.security.CustomUserDetails;


@Component
public class PrincipalUtil {
	public PrincipalUtil() {
	}
	
	public SecurityContext getContext() {
		return SecurityContextHolder.getContext();
	}
	
	/*
	 * UserDetails 형태로 return
	 */
	public UserDetails getPrincipal() {
		Authentication authentication = getContext().getAuthentication();
		return principalConvert(authentication);
	}
	
	/*
	 * SecurityContextHolder.getContext().getAuthentication() 형태로 값을 가져오는게 아닌 로직은 authentication를 파라미터로 넣어줌
	 */
	public CustomUserDetails getPrincipal(Authentication authentication) {
		return principalConvert(authentication);
	}
	
	private CustomUserDetails principalConvert(Authentication authentication) {
		Object principal =  authentication.getPrincipal();
    	
        CustomUserDetails userPrincipal = null;
        if (Objects.equals(principal, "anonymousUser")) {
            userPrincipal = null;
        } else if (principal instanceof CustomUserDetails) {
            userPrincipal = (CustomUserDetails) principal;
        }

        return userPrincipal;
	}
	
}

