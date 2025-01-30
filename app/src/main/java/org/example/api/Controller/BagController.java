package org.example.api.Controller;

import org.example.api.Model.Bag;
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
}

