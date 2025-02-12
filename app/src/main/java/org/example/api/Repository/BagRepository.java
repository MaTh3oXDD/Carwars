package org.example.api.Repository;

import org.example.api.Model.Bag;
import org.example.api.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BagRepository extends JpaRepository<Bag, Integer> {
    Bag findByUser(User user);
}
