package com.phoenix.ecommerce.repository;

import com.phoenix.ecommerce.model.Order;
import com.phoenix.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // A method to find all orders for a specific user, ordered by date
    List<Order> findByUserOrderByOrderDateDesc(User user);
}