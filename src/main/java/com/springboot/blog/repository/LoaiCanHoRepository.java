package com.springboot.blog.repository;

import com.springboot.blog.entity.LoaiCanHo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoaiCanHoRepository extends JpaRepository<LoaiCanHo, Long> {
    Optional<LoaiCanHo> findLoaiCanHoById(Long id);
}
