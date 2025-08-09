package com.phoenix.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Lob // For longer text
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private int stockQuantity;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY) // Lazy fetch is better for performance
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist // This annotation sets the createdAt field before the entity is saved
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}