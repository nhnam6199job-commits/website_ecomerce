package com.phoenix.ecommerce.controller;

import com.phoenix.ecommerce.dto.CheckoutRequest;
import com.phoenix.ecommerce.dto.OrderResponse;
import com.phoenix.ecommerce.model.User;
import com.phoenix.ecommerce.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(@AuthenticationPrincipal User user,
                                                     @Valid @RequestBody CheckoutRequest request) {
        OrderResponse createdOrder = orderService.createOrderFromCart(user, request.getShippingAddress());
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }
}