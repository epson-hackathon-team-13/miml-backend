package com.miml.epson.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.miml.epson.entity.TokenEntity;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {

}