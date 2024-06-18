package com.miml.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.miml.user.repository.UserRepository;

public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;
	
	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return new CustomUserDetails(userRepository.findByEmail(email)
				.orElseThrow(
						() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다.")
				)
        );
    }

}
