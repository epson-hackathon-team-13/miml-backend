package com.miml.epson.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miml.epson.entity.TokenEntity;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
	
	Optional<TokenEntity> findBySubjectId(String subjectId);

}