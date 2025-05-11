package com.bydefault.store.config;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {
    @Value("${stripe.STRIPE_SECRET_KEY}")
    private String  secretKey;


//    init the stripe when ever a bean of the class is create
    @PostConstruct
    public void init(){
        Stripe.apiKey = secretKey;
    }
}
