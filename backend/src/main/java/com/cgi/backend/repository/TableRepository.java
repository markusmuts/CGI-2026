package com.cgi.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cgi.backend.entity.Table;

public interface TableRepository extends JpaRepository<Table, Long> {
}