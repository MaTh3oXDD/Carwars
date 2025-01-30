package org.example.api.Controller;

import org.example.api.Model.Car;
import org.example.api.Model.Item;
import org.example.api.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {
    @Autowired
    ItemService itemService;

    @GetMapping("/findall")
    public ResponseEntity<List<Item>> findAllItems() {
        List<Item> items = itemService.findAllItems();
        return ResponseEntity.ok(items);
    }
    @PostMapping("/add")
    public ResponseEntity<Item> addItem(@RequestBody Item item) {
        Item newItem = itemService.addItems(item);
        return ResponseEntity.ok(newItem);
    }
    @GetMapping("/summaries")
    public List<Item> getItemSummaries() {
        return itemService.findItemSummaries();
    }
}
