package org.example.api.Service;

import org.example.api.Model.OwnershipOfCar;
import org.example.api.Repository.OwnershipOfCarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnershipOfCarService {

    @Autowired
    private OwnershipOfCarRepository ownershipOfCarRepository;

    public List<OwnershipOfCar> findAllOwnerships() {
        return ownershipOfCarRepository.findAll();
    }

    public OwnershipOfCar addOwnership(OwnershipOfCar ownershipOfCar) {
        return ownershipOfCarRepository.save(ownershipOfCar);
    }
}
