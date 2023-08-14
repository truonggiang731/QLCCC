package com.springboot.blog.repository;

import com.springboot.blog.entity.Contract;
import com.springboot.blog.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByContractAndPaidFalse(Contract contract);
}
