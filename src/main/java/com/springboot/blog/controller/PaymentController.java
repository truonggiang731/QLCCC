package com.springboot.blog.controller;

import com.springboot.blog.entity.HoaDon;
import com.springboot.blog.entity.HopDong;
import com.springboot.blog.payload.HoaDonDto;
import com.springboot.blog.payload.PaymentRequest;
import com.springboot.blog.payload.PaymentResponse;
import com.springboot.blog.service.HoaDonService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@Service
@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {

    private final HoaDonService hoaDonService;

    @Value("${stripe.secretKey}")
    private String stripeSecretKey;

    @Autowired
    public PaymentController(HoaDonService hoaDonService) {
        this.hoaDonService = hoaDonService;
    }

    @PostMapping("/process-payment")
    public ResponseEntity<?> processPayment(@RequestBody PaymentRequest paymentRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        try {
            Stripe.apiKey = stripeSecretKey;

            Long invoiceId = paymentRequest.getInvoiceId();
            Long amountToPay = calculateAmount(invoiceId);

            Map<String, Object> customerParams = new HashMap<>();
            customerParams.put("email", currentPrincipalName);
            customerParams.put("source", paymentRequest.getToken());

            Customer customer = Customer.create(customerParams);

            Map<String, Object> params = new HashMap<>();
            params.put("amount", amountToPay);
            params.put("currency", "vnd");
            params.put("description", "Payment for Invoice #" + invoiceId);
            params.put("customer", customer.getId());

            Charge charge = Charge.create(params);
            hoaDonService.paidHoaDon(invoiceId);

            PaymentResponse paymentResponse = new PaymentResponse(charge.getId());

            return ResponseEntity.ok(paymentResponse);
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    private Long calculateAmount(Long id) {
        HoaDonDto hoaDon = hoaDonService.getHoaDonById(id);

        return hoaDon.getTongtien();
    }
}


