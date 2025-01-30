// SearchController.java
package org.example.api.Controller;

import org.example.api.Model.Search;
import org.example.api.Service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private SearchService searchService;

    @PostMapping("/add")
    public ResponseEntity<Search> addSearch(@RequestBody Search search) {
        Search savedSearch = searchService.addSearch(search);
        return ResponseEntity.ok(savedSearch);
    }

    @GetMapping("/names")
    public ResponseEntity<String> getRandomName() {
        try {
            String randomName = searchService.getRandomNameBasedOnWeight();
            return ResponseEntity.ok(randomName);
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/findall")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(searchService.findall());
    }
    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteSearch(@PathVariable int id) {
        if(ResponseEntity.ok(searchService.deleteSearch(id)).hasBody())
            return ResponseEntity.ok("Search deleted");
        else
            return ResponseEntity.notFound().build();
    }
}