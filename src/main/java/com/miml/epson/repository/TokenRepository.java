package com.miml.epson.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miml.epson.entity.TokenEntity;
import com.miml.user.entity.UserEntity;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
	
	Optional<TokenEntity> findBySubjectId(String subjectId);
	
	List<TokenEntity> findByUser(UserEntity user);
	
	Optional<TokenEntity> findByUser_IdAndUsername(Long userId, String username);
	
}