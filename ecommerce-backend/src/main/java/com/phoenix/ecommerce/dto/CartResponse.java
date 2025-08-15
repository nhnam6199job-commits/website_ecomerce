package com.phoenix.ecommerce.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Set;

@Data
@Builder
public class CartResponse {
    private Long cartId;
    private Set<CartItemResponse> items;
    private BigDecimal totalAmount;
}