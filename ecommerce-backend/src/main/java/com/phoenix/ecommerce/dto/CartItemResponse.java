package com.phoenix.ecommerce.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Set;

@Data
@Builder
public class CartItemResponse {
    private Long cartItemId;
    private ProductResponse product;
    private int quantity;
    private BigDecimal subtotal;
}