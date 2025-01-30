package org.example.api.Service;

import org.example.api.Model.Search;
import org.example.api.Repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class SearchService {
    private static final Random random = new Random();

    static class Item {
        String name;
        int weight;

        public Item(String name, int weight) {
            this.name = name;
            this.weight = weight;
        }
    }

    @Autowired
    private SearchRepository searchRepository;

    public List<Search> findall() {
        return searchRepository.findAll();
    }
    public Search deleteSearch(int id) {
        Search search = searchRepository.findById(id).orElseThrow();
        searchRepository.delete(search);
        return search;
    }
    public Search addSearch(Search search) {
        return searchRepository.save(search);
    }

    public List<String> getAllNames() {
        return searchRepository.findAll().stream()
                .map(Search::getName)
                .toList();
    }

    public List<Search> getAllSearches() {
        return searchRepository.findAll();
    }

    public String getRandomNameBasedOnWeight() {
        List<Search> searches = getAllSearches();

        if(searches.isEmpty()) {
            throw new IllegalStateException("No searches found");
        }

        Item[] items = searches.stream()
                .map(search -> new Item(search.getName(), search.getWeight()))
                .toArray(Item[]::new);

        return selectItem(items);
    }

    private String selectItem(Item[] items) {
        int totalWeight = 0;
        for (Item item : items) {
            totalWeight += item.weight;
        }

        int randomNumber = random.nextInt(totalWeight);
        int cumulativeWeight = 0;

        for (Item item : items) {
            cumulativeWeight += item.weight;
            if (randomNumber < cumulativeWeight) {
                return item.name;
            }
        }

        throw new IllegalArgumentException("Error in random selection. Possibly weights don't sum up to 100%");
    }

}