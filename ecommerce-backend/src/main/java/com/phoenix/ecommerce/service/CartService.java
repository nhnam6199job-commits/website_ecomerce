package com.phoenix.ecommerce.service;

import com.phoenix.ecommerce.dto.AddItemRequest;
import com.phoenix.ecommerce.dto.CartItemResponse;
import com.phoenix.ecommerce.dto.CartResponse;
import com.phoenix.ecommerce.dto.ProductResponse;
import com.phoenix.ecommerce.model.Cart;
import com.phoenix.ecommerce.model.CartItem;
import com.phoenix.ecommerce.model.Product;
import com.phoenix.ecommerce.model.User;
import com.phoenix.ecommerce.repository.CartItemRepository;
import com.phoenix.ecommerce.repository.CartRepository;
import com.phoenix.ecommerce.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    // We'll need this helper method from ProductService later
    private final ProductService productService;

    @Transactional
    public CartResponse addProductToCart(User user, AddItemRequest request) {
        Cart cart = getOrCreateCart(user);
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        // Check if item already in cart
        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(request.getProductId()))
                .findFirst()
                .orElse(new CartItem());

        if (cartItem.getId() == null) { // New item
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cart.getItems().add(cartItem);
        } else { // Existing item, update quantity
            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
        }

        cartRepository.save(cart);
        return getCartDtoForUser(user);
    }

    @Transactional(readOnly = true)
    public CartResponse getCartDtoForUser(User user) {
        Cart cart = getOrCreateCart(user);
        return mapCartToCartResponse(cart);
    }

    @Transactional
    public void removeCartItem(User user, Long cartItemId) {
        Cart cart = getOrCreateCart(user);
        CartItem itemToRemove = cart.getItems().stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Cart item not found"));

        cart.getItems().remove(itemToRemove);
        cartItemRepository.delete(itemToRemove);
        cartRepository.save(cart);
    }

    public Cart getOrCreateCart(User user) {
        return cartRepository.findByUserId(user.getId()).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        });
    }

    private CartResponse mapCartToCartResponse(Cart cart) {
        Set<CartItemResponse> itemResponses = cart.getItems().stream().map(item ->
                CartItemResponse.builder()
                        .cartItemId(item.getId())
                        .product(productService.mapToProductResponse(item.getProduct())) // Reuse the mapper
                        .quantity(item.getQuantity())
                        .subtotal(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                        .build()
        ).collect(Collectors.toSet());

        BigDecimal totalAmount = itemResponses.stream()
                .map(CartItemResponse::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return CartResponse.builder()
                .cartId(cart.getId())
                .items(itemResponses)
                .totalAmount(totalAmount)
                .build();
    }
}