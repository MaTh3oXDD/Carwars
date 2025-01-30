package org.example.api.Controller;

import jakarta.persistence.Column;
import org.example.api.DTO.OrderItemRequest;
import org.example.api.Model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.example.api.Service.OrderItemService;

import java.util.List;

@RestController
@RequestMapping("/orderitems")
public class OrderItemController {
    @Autowired
    private OrderItemService orderItemService;

    @GetMapping("/findall")
    public List<OrderItem> findAllOrderItems() {
        return orderItemService.findAll();
    }

    @PostMapping("/add")
    public OrderItem addOrderItem(@RequestBody OrderItemRequest orderItemRequest) {
        return orderItemService.save(orderItemRequest);
    }
}

