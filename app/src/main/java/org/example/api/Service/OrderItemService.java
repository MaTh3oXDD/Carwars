package org.example.api.Service;

import org.example.api.DTO.OrderItemRequest;
import org.example.api.Model.Item;
import org.example.api.Model.OrderItem;
import org.example.api.Model.Orders;
import org.example.api.Repository.ItemRepository;
import org.example.api.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.api.Repository.OrderItemRepository;

import java.util.List;

@Service
public class OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    public List<OrderItem> findAll() {
        return orderItemRepository.findAll();
    }

    public OrderItem save(OrderItemRequest orderItemRequest) {
        // Pobieramy powiązane zamówienie (Orders)
        Orders order = orderRepository.findById(orderItemRequest.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderItemRequest.getOrderId()));

        // Pobieramy powiązany przedmiot (Item)
        Item item = itemRepository.findById(orderItemRequest.getItemId())
                .orElseThrow(() -> new RuntimeException("Item not found with ID: " + orderItemRequest.getItemId()));

        // Tworzymy nowy obiekt OrderItem
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setItem(item);
        orderItem.setQuantity(orderItemRequest.getQuantity());
        orderItem.setUnitPrice(orderItemRequest.getUnitPrice());

        // Zapisujemy obiekt OrderItem do bazy danych
        return orderItemRepository.save(orderItem);
    }
}

