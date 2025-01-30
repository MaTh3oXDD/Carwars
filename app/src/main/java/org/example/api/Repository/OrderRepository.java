package org.example.api.Repository;


import org.example.api.DTO.OrderSummaryDTO;
import org.example.api.Model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Integer> {

    @Query("SELECT new org.example.api.DTO.OrderSummaryDTO(o.id, i.id, i.name, oi.quantity, oi.unitPrice) " +
            "FROM Orders o " +
            "JOIN o.orderItems oi " +
            "JOIN oi.item i")
    List<OrderSummaryDTO> findAllOrderSummaries();
}
