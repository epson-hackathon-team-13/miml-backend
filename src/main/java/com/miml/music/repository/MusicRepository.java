package com.miml.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.miml.music.entity.MusicEntity;

@Repository
public interface MusicRepository extends JpaRepository<MusicEntity, Long>{

}
