package org.example.api.Controller;


import org.example.api.DTO.ItemDTO;
import org.example.api.Model.Bag;
import org.example.api.Model.BagItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.example.api.Service.BagService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bags")
public class BagController {
    private final BagService bagService;

    @Autowired
    public BagController(BagService bagService) {
        this.bagService = bagService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> createBag(@RequestBody Bag bag) {
        try {
            Bag savedBag = bagService.createBag(bag);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedBag);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
    @GetMapping("/findall")
    public List<Bag> findAllBags() {
        return bagService.findAllBags();
    }
    @GetMapping("/items")
    public List<BagItem> getBagItems(@RequestParam String username) {
        return bagService.getBagItemsByUsername(username);
    }
    @PostMapping("/{username}/add-item/{itemId}")
    public ResponseEntity<String> addItemToBag(
            @PathVariable String username,
            @PathVariable int itemId) {
        bagService.addItemToBag(username, itemId);
        return ResponseEntity.ok("Item with ID " + itemId + " successfully added to " + username + "'s bag.");
    }

    /**
     * Endpoint do usuwania przedmiotu z plecaka
     */
    @DeleteMapping("/{username}/remove-item/{itemId}")
    public ResponseEntity<String> removeItemFromBag(
            @PathVariable String username,
            @PathVariable int itemId) {
        bagService.removeItemFromBag(username, itemId);
        return ResponseEntity.ok("Item with ID " + itemId + " successfully removed from " + username + "'s bag.");
    }
    @GetMapping("/{username}/items")
    public ResponseEntity<List<ItemDTO>> getItemsInBag(@PathVariable String username) {
        List<ItemDTO> items = bagService.getItemsInBag(username);
        return ResponseEntity.ok(items);
    }
    @DeleteMapping("/sell-item/{bagItemId}")
    public ResponseEntity<?> deleteBagItem(@PathVariable int bagItemId) {
        try {
            bagService.removeBagItem(bagItemId);  // Wywołaj serwis do usunięcia konkretnego elementu
            return ResponseEntity.ok("Item removed from bag successfully!");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }



}

