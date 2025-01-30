package org.example.api.Service;

import org.example.api.Model.Bag;
import org.example.api.Model.User;
import org.example.api.Repository.BagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BagService {
    private final BagRepository bagRepository;

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
}
