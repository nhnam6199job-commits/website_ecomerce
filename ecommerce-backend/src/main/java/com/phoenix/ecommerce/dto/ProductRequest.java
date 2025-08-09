package com.phoenix.ecommerce.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private int stockQuantity;
    private String imageUrl;
    private Long categoryId; // We only need the ID from the request
}