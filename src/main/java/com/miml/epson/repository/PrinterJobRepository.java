package com.miml.epson.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miml.epson.entity.PrinterJob;

public interface PrinterJobRepository extends JpaRepository<PrinterJob, Long> {

}