package com.bydefault.store.services.impl;

import com.bydefault.store.dtos.checkout.PaymentResult;
import com.bydefault.store.dtos.checkout.WebhookRequest;
import com.bydefault.store.entities.Order;
import com.bydefault.store.entities.PaymentStatus;
import com.bydefault.store.exceptions.PaymentGatewayException;
import com.bydefault.store.repositories.OrderRepository;
import com.bydefault.store.services.CheckoutSession;
import com.bydefault.store.services.PaymentGateServices;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class StripePaymentGateway implements PaymentGateServices {

    @Value("${websiteUrl}")
    private String websiteUrl;
    @Value("${stripe.WEBHOOK_SECRET_KEY}")
    private String webhook_key;


    @Override
    public CheckoutSession createCheckoutSession(Order order) {

        try {
//        handle checkout or payment
            var builder = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(websiteUrl + "/checkout-success?orderId=" + order.getId())
                    .setCancelUrl(websiteUrl + "/checkout-cancel")
                    .putMetadata("order_id", order.getId().toString());


//       add the items to stripe now
            order.getItems().forEach(item -> {
                var lineItem = SessionCreateParams.LineItem.builder()
                        .setQuantity(Long.valueOf(item.getQuantity()))
                        .setPriceData(
                                SessionCreateParams.LineItem.PriceData.builder()
                                        .setCurrency("usd")
                                        .setUnitAmountDecimal(item.getUnitPrice().multiply(BigDecimal.valueOf(100)))
                                        .setProductData(
                                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                        .setName(item.getProduct().getName())
                                                        .build()
                                        ).build()
                        ).build();
                builder.addLineItem(lineItem);
            });
            var session = Session.create(builder.build());
            return new CheckoutSession(session.getUrl());
        } catch (PaymentGatewayException | StripeException e) {
            throw new PaymentGatewayException(e.getMessage());
        }

    }

    @Override
    public Optional<PaymentResult> parseWebhookRequest(WebhookRequest request) {
        try {
            var payload = request.getPayload();
            var signature = request.getHeaders().get("stripe-signature");
            var event = Webhook.constructEvent(payload, signature, webhook_key);
            switch (event.getType()) {
                case "payment_intent.succeeded" -> {
                    return Optional.of(new PaymentResult(extractOrderId(event), PaymentStatus.PAID));
                }
                case "payment_intent.payment_failed" -> {
                    return Optional.of(new PaymentResult(extractOrderId(event), PaymentStatus.FAILED));
                }
                default -> {
                    return Optional.empty();
                }
            }

        } catch (SignatureVerificationException e) {
            throw new PaymentGatewayException("Invalid Stripe Signature");
        }
    }

    private Long extractOrderId(Event event) {
        var stripeObject = event.getDataObjectDeserializer().getObject().orElseThrow(() -> new PaymentGatewayException("Stripe object is null"));
        var paymentIntent = (PaymentIntent) stripeObject;
        return Long.valueOf(paymentIntent.getMetadata().get("order_id"));
    }

}
