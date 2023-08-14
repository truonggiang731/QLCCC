package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Contract;
import com.springboot.blog.entity.Invoice;
import com.springboot.blog.repository.ContractRepository;
import com.springboot.blog.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BillingService {
    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;

//    public List<Invoice> getUnpaidInvoices() {
//        List<Contract> contracts = contractRepository.findBySomeCriteria(); // Lấy danh sách hợp đồng chưa thanh toán
//        List<Invoice> unpaidInvoices = new ArrayList<>();
//
//        for (Contract contract : contracts) {
//            List<Invoice> invoices = invoiceRepository.findByContractAndPaidFalse(contract);
//            unpaidInvoices.addAll(invoices);
//        }
//
//        return unpaidInvoices;
//    }
}