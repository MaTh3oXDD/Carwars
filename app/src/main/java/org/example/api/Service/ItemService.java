package org.example.api.Service;

import org.example.api.Model.Item;
import org.example.api.Repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {
    @Autowired
    ItemRepository itemRepository;

    public Item addItems(Item item) {
        return itemRepository.save(item);
    }
    public List<Item> findAllItems() {
        return itemRepository.findAll();
    }
    public List<Item> findItemSummaries() {
        return itemRepository.findAll()
                .stream()
                .map(item -> new Item((int) item.getId(), item.getName(), item.getWeight()))
                .collect(Collectors.toList());
    }
}
