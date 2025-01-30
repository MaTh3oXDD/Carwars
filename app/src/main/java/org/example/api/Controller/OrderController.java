package org.example.api.Controller;

import org.example.api.DTO.OrderRequest;
import org.example.api.DTO.OrderSummaryDTO;
import org.example.api.Model.Orders;
import org.example.api.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.example.api.Service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/findall")
    public List<Orders> findAllOrders() {
        return orderService.findAll();
    }

    @PostMapping("/add")
    public Orders addOrder(@RequestBody OrderRequest orderRequest) {
        // Wywołanie serwisu i przesłanie danych
        return orderService.saveOrder(orderRequest);
    }
    @GetMapping("/summaries")
    public List<OrderSummaryDTO> getOrderSummaries() {
        return orderRepository.findAllOrderSummaries();
    }
}

