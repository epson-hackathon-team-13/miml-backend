package com.miml.word.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.miml.word.entity.WordEntity;

@Repository
public interface WordRepository extends JpaRepository<WordEntity, Long>{

}
