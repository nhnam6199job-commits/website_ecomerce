package com.phoenix.ecommerce.service;

import com.phoenix.ecommerce.dto.OrderItemResponse;
import com.phoenix.ecommerce.dto.OrderResponse;
import com.phoenix.ecommerce.model.*;
import com.phoenix.ecommerce.repository.CartItemRepository;
import com.phoenix.ecommerce.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final CartItemRepository cartItemRepository;

    @Transactional
    public OrderResponse createOrderFromCart(User user, String shippingAddress) {
        Cart cart = cartService.getOrCreateCart(user);
        Set<CartItem> cartItems = cart.getItems();

        if (cartItems.isEmpty()) {
            throw new IllegalStateException("Cannot create an order from an empty cart.");
        }

        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(shippingAddress);
        order.setStatus("PENDING");

        List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPricePerUnit(cartItem.getProduct().getPrice());
            return orderItem;
        }).collect(Collectors.toList());

        order.setItems(orderItems);

        BigDecimal totalAmount = orderItems.stream()
                .map(item -> item.getPricePerUnit().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalAmount(totalAmount);

        // Clear the cart after creating the order
        // Use deleteAllInBatch for better performance with multiple items
        cart.getItems().clear();

        // CHANGE 2: Save the order to get the managed entity with its generated ID and date
        Order savedOrder = orderRepository.save(order);

        // CHANGE 3: Map the saved entity to a DTO before returning it.
        // This process happens INSIDE the transaction, so lazy-loaded fields are accessible.
        return mapToOrderResponse(savedOrder);
    }

    // NEW HELPER METHOD: This method safely maps an Order entity to an OrderResponse DTO.
    private OrderResponse mapToOrderResponse(Order order) {
        // Map the list of OrderItem entities to a list of OrderItemResponse DTOs
        List<OrderItemResponse> itemResponses = order.getItems().stream()
                .map(item -> OrderItemResponse.builder()
                        .productId(item.getProduct().getId())
                        .productName(item.getProduct().getName())
                        .quantity(item.getQuantity())
                        .pricePerUnit(item.getPricePerUnit())
                        .build())
                .collect(Collectors.toList());

        // Build the final OrderResponse DTO
        return OrderResponse.builder()
                .orderId(order.getId())
                .orderDate(order.getOrderDate())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .shippingAddress(order.getShippingAddress())
                .items(itemResponses)
                .build();
    }
}