package org.example.api.Controller;

import org.example.api.Model.*;
import org.example.api.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import lombok.Data;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/market")
public class MarketController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private BagRepository bagRepository;

    @Autowired
    private OrderRepository ordersRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @PostMapping("/buy")
    @Transactional
    public ResponseEntity<?> buyItem(@RequestBody PurchaseRequest purchaseRequest) {

        System.out.println("Received username: " + purchaseRequest.getUsername());
        System.out.println("Received itemId: " + purchaseRequest.getItemId());

        // 1. Pobieramy użytkownika na podstawie username
        User user = userRepository.findByUsername(purchaseRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Pobieramy przedmiot do kupienia
        Item item = itemRepository.findById(purchaseRequest.getItemId())
                .orElseThrow(() -> new RuntimeException("Item not found"));

        // 3. Sprawdzamy, czy użytkownik ma plecak
        Bag bag = user.getBags();
        if (bag == null) {
            return handleBadRequest("User does not have a bag.");
        }
        // 4. Znajdź istniejący OrderItem
        Optional<OrderItem> existingOrderItem = orderItemRepository.findAll().stream()
                .filter(orderItem -> orderItem.getItem().getId() == purchaseRequest.getItemId() &&
                        orderItem.getOrder().getUser().getId() == user.getId())
                .findFirst();
        // 5. Jeżeli orderItem istnieje to go usuwamy.
        if(existingOrderItem.isPresent()) {
            OrderItem orderItemToDelete = existingOrderItem.get();
            orderItemRepository.delete(orderItemToDelete);
            // Pobieramy BagItem i go usuwamy
            Optional<BagItem> existingBagItem = bag.getBagItems().stream()
                    .filter(bi -> bi.getItem().getId() == item.getId())
                    .findFirst();

            if(existingBagItem.isPresent()){
                BagItem bagItemToDelete = existingBagItem.get();
                bag.getBagItems().remove(bagItemToDelete);
            }

            bagRepository.save(bag);
            return ResponseEntity.ok("Item removed from order and bag successfully!");
        } else {
            // 6. Obliczamy całkowity koszt oraz wagę przedmiotu
            double totalCost = purchaseRequest.getUnitPrice() * purchaseRequest.getQuantity();
            int totalWeight = item.getWeight() * purchaseRequest.getQuantity();

            // 7. Sprawdzamy, czy użytkownik ma wystarczająco pieniędzy
            if (user.getMoney() < totalCost) {
                return handleBadRequest("Not enough money to buy this item.");
            }

            // 8. Sprawdzamy, czy jest miejsce w plecaku
            int usedCapacity = bag.getBagItems().stream()
                    .mapToInt(bagItem -> bagItem.getItem().getWeight())
                    .sum();
            int availableCapacity = bag.getCapacity() - usedCapacity;
            if (availableCapacity < totalWeight) {
                return handleBadRequest("Not enough space in the bag for this item.");
            }
            // 9. Tworzymy nowe zamówienie
            Orders order = new Orders(user, "COMPLETED");
            ordersRepository.save(order);

            // 10. Tworzymy nowy wiersz w OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setItem(item);
            orderItem.setQuantity(purchaseRequest.getQuantity());
            orderItem.setUnitPrice(purchaseRequest.getUnitPrice());
            orderItemRepository.save(orderItem);
            // 11. Sprawdzamy czy BagItem już istnieje dla danego itemu i plecaka
            Optional<BagItem> existingBagItem = bag.getBagItems().stream()
                    .filter(bi -> bi.getItem().getId() == item.getId())
                    .findFirst();

            BagItem bagItem;
            if(existingBagItem.isPresent()){
                bagItem = existingBagItem.get();
            } else {
                bagItem = new BagItem();
                bagItem.setBag(bag);
                bagItem.setItem(item);
                bag.getBagItems().add(bagItem);
            }

            // 12. Odejmujemy pieniądze użytkownikowi
            user.setMoney((int) (user.getMoney() - totalCost));
            // 13. Zapisujemy aktualne dane
            userRepository.save(user);
            bagRepository.save(bag);
            return ResponseEntity.ok("Item purchased successfully!");

        }
    }
    private ResponseEntity<Map<String, String>> handleBadRequest(String message) {
        Map<String, String> response = new HashMap<>();
        response.put("error", message);
        return ResponseEntity.badRequest().body(response);
    }


    @Data
    private static class PurchaseRequest{
        private String username;
        private int itemId;
        private int quantity;
        private double unitPrice;
    }
}
