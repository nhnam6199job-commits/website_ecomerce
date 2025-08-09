package com.phoenix.ecommerce.controller;

import com.phoenix.ecommerce.dto.ProductRequest;
import com.phoenix.ecommerce.model.Product;
import com.phoenix.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/products")
@RequiredArgsConstructor
@Slf4j
@PreAuthorize("hasRole('ADMIN')") // Secures all endpoints in this controller
public class AdminProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        log.info("Received request to create a new product: {}", productRequest);
        Product createdProduct = productService.createProduct(productRequest);
        log.info("Successfully created product with ID: {}", createdProduct.getId());
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @Valid @RequestBody ProductRequest productRequest) {
        log.info("Received request to update product with ID: {}", productId);
        Product updatedProduct = productService.updateProduct(productId, productRequest);
        log.info("Successfully updated product with ID: {}", updatedProduct.getId());
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        log.info("Received request to delete product with ID: {}", productId);
        productService.deleteProduct(productId);
        log.info("Successfully deleted product with ID: {}", productId);
        return ResponseEntity.noContent().build(); // Standard response for successful delete
    }
}