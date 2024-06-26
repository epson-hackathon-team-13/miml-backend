package com.miml.word.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.miml.user.entity.UserEntity;
import com.miml.word.entity.WordEntity;

@Repository
public interface WordRepository extends JpaRepository<WordEntity, Long>{
	
    // UserEntity를 받아서 WordEntity 리스트를 반환하는 메소드
    List<WordEntity> findByUserEntity(UserEntity userEntity);
    
    // User ID를 받아서 WordEntity 리스트를 반환하는 메소드
    @Query("SELECT w FROM WordEntity w WHERE w.userEntity.id = :userId")
    List<WordEntity> findByUserId(@Param("userId") Long userId);
    
}
