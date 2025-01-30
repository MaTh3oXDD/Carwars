package org.example.api.Service;

import org.example.api.DTO.OrderRequest;
import org.example.api.Model.Orders;
import org.example.api.Model.User;
import org.example.api.Repository.OrderRepository;
import org.example.api.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository; // Repozytorium dla użytkownika

    public List<Orders> findAll() {
        return orderRepository.findAll();
    }

    public Orders saveOrder(OrderRequest orderRequest) {
        // Pobieramy użytkownika z bazy danych na podstawie user_id
        User user = userRepository.findById(orderRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + orderRequest.getUserId()));

        // Tworzymy zamówienie i ustawiamy relacje
        Orders order = new Orders();
        order.setUser(user); // Ustalamy powiązanie z użytkownikiem
        order.setStatus(orderRequest.getStatus());

        // Zapisujemy zamówienie
        return orderRepository.save(order);
    }
}

