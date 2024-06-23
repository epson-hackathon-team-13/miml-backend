package com.miml.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.miml.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	
	@EntityGraph(attributePaths = { "authorities" }, type = EntityGraph.EntityGraphType.LOAD)
	Optional<UserEntity> findByEmail(String email);
	
}
