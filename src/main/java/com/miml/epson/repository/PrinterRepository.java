package com.miml.epson.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miml.epson.entity.PrinterEntity;

public interface PrinterRepository extends JpaRepository<PrinterEntity, Long> {

}