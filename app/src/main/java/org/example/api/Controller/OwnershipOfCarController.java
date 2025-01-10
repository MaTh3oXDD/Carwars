package org.example.api.Controller;

import org.example.api.Model.OwnershipOfCar;
import org.example.api.Service.OwnershipOfCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ownerships")
public class OwnershipOfCarController {

    @Autowired
    private OwnershipOfCarService ownershipOfCarService;

    @GetMapping("/findall")
    public List<OwnershipOfCar> findAllOwnerships() {
        return ownershipOfCarService.findAllOwnerships();
    }

    @PostMapping("/add")
    public ResponseEntity<OwnershipOfCar> addOwnership(@RequestBody OwnershipOfCar ownershipOfCar) {
        OwnershipOfCar newOwnership = ownershipOfCarService.addOwnership(ownershipOfCar);
        return ResponseEntity.ok(newOwnership);
    }
}
