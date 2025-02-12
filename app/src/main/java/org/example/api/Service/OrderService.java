package org.example.api.Service;

import org.example.api.DTO.OrderRequest;
import org.example.api.Model.Item;
import org.example.api.Model.OrderItem;
import org.example.api.Model.Orders;
import org.example.api.Model.User;
import org.example.api.Repository.ItemRepository;
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
    private ItemRepository itemRepository; // Repozytorium dla przedmiotu

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
    public Orders createMarketListing(OrderRequest orderRequest, int itemId, int quantity, double unitPrice) {
        // 1. Znajdź użytkownika po userId z `OrderRequest`
        User user = userRepository.findById(orderRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found!"));

        // 2. Znajdź przedmiot po `itemId`
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found!"));

        // 3. Tworzenie nowego zamówienia/ogłoszenia
        Orders order = new Orders();
        order.setUser(user);              // Powiąż zamówienie z użytkownikiem
        order.setStatus(orderRequest.getStatus()); // Status (np. "MARKET")

        // 4. Dodanie pozycji zamówienia (OrderItem)
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setQuantity(quantity);      // Ilość przedmiotu
        orderItem.setUnitPrice(unitPrice);    // Cena jednostkowa
        orderItem.setOrder(order);            // Powiązanie z zamówieniem

        // Powiąż pozycję z ogłoszeniem
        order.setOrderItems(List.of(orderItem));

        // 5. Zapisz ogłoszenie w repozytorium
        return orderRepository.save(order);
    }
}

