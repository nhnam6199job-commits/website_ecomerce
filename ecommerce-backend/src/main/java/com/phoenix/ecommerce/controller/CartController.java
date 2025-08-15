package com.phoenix.ecommerce.controller;

import com.phoenix.ecommerce.dto.AddItemRequest;
import com.phoenix.ecommerce.dto.CartResponse;
import com.phoenix.ecommerce.model.User;
import com.phoenix.ecommerce.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<CartResponse> getCart(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(cartService.getCartDtoForUser(user));
    }

    @PostMapping("/items")
    public ResponseEntity<CartResponse> addItemToCart(@AuthenticationPrincipal User user, @Valid @RequestBody AddItemRequest request) {
        return ResponseEntity.ok(cartService.addProductToCart(user, request));
    }

    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<Void> removeCartItem(@AuthenticationPrincipal User user, @PathVariable Long cartItemId) {
        cartService.removeCartItem(user, cartItemId);
        return ResponseEntity.noContent().build();
    }
}