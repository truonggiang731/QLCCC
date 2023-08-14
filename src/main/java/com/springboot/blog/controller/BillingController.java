package com.springboot.blog.controller;


import com.springboot.blog.entity.Invoice;
import com.springboot.blog.service.impl.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BillingController {
    @Autowired
    private BillingService billingService;

//    @GetMapping("/unpaid-invoices")
//    public List<Invoice> getUnpaidInvoices() {
//        return billingService.getUnpaidInvoices();
//    }
}