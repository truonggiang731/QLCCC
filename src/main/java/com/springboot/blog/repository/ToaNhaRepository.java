package com.springboot.blog.repository;

import com.springboot.blog.entity.ToaNha;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ToaNhaRepository extends JpaRepository<ToaNha, Long> {
    Optional<ToaNha> findToaNhaById(Long id);
}
