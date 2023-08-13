package com.springboot.blog.repository;

import com.springboot.blog.entity.LoaiDichVu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoaiDichVuRepository extends JpaRepository<LoaiDichVu, Long> {
    Optional<LoaiDichVu> findLoaiDichVuById(Long id);
}
