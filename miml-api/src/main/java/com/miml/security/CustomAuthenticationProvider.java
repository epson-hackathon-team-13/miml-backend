package com.miml.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.miml.security.entity.UserEntity;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    
    
    public CustomAuthenticationProvider(
			CustomUserDetailsService userDetailsService, 
			PasswordEncoder passwordEncoder
			) {
		this.userDetailsService = userDetailsService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String email = authentication.getName();
        String password = (String) authentication.getCredentials();
        
        UserEntity entity = (UserEntity) userDetailsService.loadUserByUsername(email);

        if(!passwordEncoder.matches(password, entity.getPassword())) {
            throw new BadCredentialsException("Invalid Password");
        }
        
        CustomUserDetails customUserDetails = new CustomUserDetails(entity);

        return new CustomAuthenticationToken(entity, null, customUserDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(CustomAuthenticationToken.class);
    }
}