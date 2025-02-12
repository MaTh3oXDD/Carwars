package org.example.api.Service;

import org.example.api.DTO.ItemDTO;
import org.example.api.Model.Bag;
import org.example.api.Model.BagItem;
import org.example.api.Model.Item;
import org.example.api.Model.User;
import org.example.api.Repository.BagItemRepository;
import org.example.api.Repository.BagRepository;
import org.example.api.Repository.ItemRepository;
import org.example.api.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BagService {
    ;
    @Autowired
    private BagRepository bagRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BagItemRepository bagItemRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    public BagService(BagRepository bagRepository) {
        this.bagRepository = bagRepository;
    }

    public Bag createBag(Bag bag) {
        return bagRepository.save(bag);
    }
    public List<Bag> findAllBags() {
        return bagRepository.findAll();
    }
    public List<BagItem> getBagItemsByUsername(String username) {
        // Znalezienie użytkownika na podstawie username
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found for username: " + username);
        }

        // Znalezienie plecaka użytkownika
        Bag bag = bagRepository.findByUser(user.orElse(null));
        if (bag == null) {
            throw new RuntimeException("Bag not found for user: " + username);
        }

        // Znalezienie wszystkich przedmiotów w plecaku
        return bagItemRepository.findByBag(bag);
    }
    public void addItemToBag(String username, int itemId) {
        // Znalezienie użytkownika po username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));

        // Pobranie plecaka użytkownika
        Bag bag = bagRepository.findByUser(user);
        if (bag == null) {
            throw new RuntimeException("Bag not found for user: " + username);
        }

        // Pobranie przedmiotu po ID
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found with ID: " + itemId));

        // Dodanie przedmiotu do BagItem
        BagItem bagItem = new BagItem();
        bagItem.setBag(bag);
        bagItem.setItem(item);

        // Zapis do bazy
        bagItemRepository.save(bagItem);
    }


    /**
     * Usuwanie Item z plecaka użytkownika na podstawie ID
     */
    public void removeItemFromBag(String username, int itemId) {
        // Znajdź użytkownika na podstawie `username`
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));

        // Pobierz plecak użytkownika
        Bag bag = bagRepository.findByUser(user);
        if (bag == null) {
            throw new RuntimeException("Bag not found for user: " + username);
        }

        // Znajdź wszystkie dopasowane BagItem dla Bag.id i Item.id
        List<BagItem> bagItems = bagItemRepository.findAllByBag_IdAndItem_Id(bag.getId(), itemId);

        if (bagItems.isEmpty()) {
            throw new RuntimeException("No items found in bag for given itemId: " + itemId);
        }

        // Usuń wszystkie pasujące BagItem
        bagItemRepository.deleteAll(bagItems);
    }
    public List<ItemDTO> getItemsInBag(String username) {
        // Znajdź użytkownika po nazwie
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));

        // Pobierz torbę (Bag) użytkownika
        Bag bag = bagRepository.findByUser(user);
        if (bag == null) {
            throw new RuntimeException("Bag not found for user: " + username);
        }

        // Pobierz listę przedmiotów (Items) w torbie
        return bag.getBagItems().stream()
                .map(bagItem -> new ItemDTO(bagItem.getItem()))
                .collect(Collectors.toList());
    }
    public void removeBagItem(int bagItemId) {
        // Znajdź konkretny BagItem po ID
        BagItem bagItem = bagItemRepository.findById(bagItemId)
                .orElseThrow(() -> new RuntimeException("Bag item not found"));

        // Usuń element z bazy danych
        bagItemRepository.delete(bagItem);
    }



}
