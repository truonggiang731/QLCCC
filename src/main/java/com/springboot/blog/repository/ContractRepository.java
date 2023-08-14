package com.springboot.blog.repository;

import com.springboot.blog.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    //List<Contract> findBySomeCriteria(); // Thay thế bằng tiêu chí cụ thể
}
