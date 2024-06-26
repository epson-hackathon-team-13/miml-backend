package com.miml.common.utils;

import java.util.Objects;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.miml.security.CustomUserDetails;
import com.miml.user.entity.UserEntity;

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
        Object principal = authentication.getPrincipal();
        
        if (Objects.equals(principal, "anonymousUser")) {
            return null;
        }

        if (principal instanceof CustomUserDetails) {
            return (CustomUserDetails) principal;
        }

        if (principal instanceof UserEntity) {
            return new CustomUserDetails((UserEntity) principal);
        }

        throw new IllegalStateException("Authentication principal is not an instance of CustomUserDetails or UserEntity");
    }

    /*
     * UserEntity 형태로 return
     */
    public UserEntity getUserEntity() {
        Authentication authentication = getContext().getAuthentication();
        return userEntityConvert(authentication);
    }

    /*
     * SecurityContextHolder.getContext().getAuthentication() 형태로 값을 가져오는게 아닌 로직은 authentication를 파라미터로 넣어줌
     */
    public UserEntity getUserEntity(Authentication authentication) {
        return userEntityConvert(authentication);
    }

    private UserEntity userEntityConvert(Authentication authentication) {
        CustomUserDetails customUserDetails = principalConvert(authentication);
        if (customUserDetails != null) {
            return customUserDetails.getUser();
        }

        throw new IllegalStateException("User is not authenticated");
    }
}