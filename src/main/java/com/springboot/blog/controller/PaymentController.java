package com.springboot.blog.controller;

import com.springboot.blog.entity.HoaDon;
import com.springboot.blog.payload.HoaDonDto;
import com.springboot.blog.payload.PaymentRequest;
import com.springboot.blog.service.HoaDonService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/payment")
public class PaymentController {
    private HoaDonService hoaDonService;
    public PaymentController(HoaDonService hoaDonService){
        this.hoaDonService = hoaDonService;
    }

    @Value("${stripe.secretKey}")
    private String stripeSecretKey;

    @PostMapping("/process-payment")
    public ResponseEntity<?> processPayment(@RequestBody PaymentRequest paymentRequest) {
        Stripe.apiKey = stripeSecretKey;
        System.out.println(stripeSecretKey);

        try {
            Long amountToPay = calculateAmount(paymentRequest.getHoaDonId());

            Map<String, Object> params = new HashMap<>();
            params.put("amount", amountToPay);
            params.put("currency", "usd");
            params.put("description", "Payment for Invoice #" + paymentRequest.getHoaDonId());

            PaymentIntent paymentIntent = PaymentIntent.create(params);

            return ResponseEntity.ok(paymentIntent.getClientSecret());
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating payment: " + e.getMessage());
        }
    }

    private Long calculateAmount(Long hoaDonId) {
        HoaDonDto hoaDon = hoaDonService.getHoaDonById(hoaDonId);

        return hoaDon.getTongtien();
    }
}
