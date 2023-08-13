package com.springboot.blog.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
public class PaymentResponse {
    private String clientSecret;

    public PaymentResponse(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    // getter
}
