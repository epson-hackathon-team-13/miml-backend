package com.miml.epson.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.miml.epson.entity.TokenInfo;

public interface TokenInfoRepository extends JpaRepository<TokenInfo, Long> {

}