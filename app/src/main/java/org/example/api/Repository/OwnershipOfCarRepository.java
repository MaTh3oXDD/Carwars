package org.example.api.Repository;

import org.example.api.Model.OwnershipOfCar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnershipOfCarRepository extends JpaRepository<OwnershipOfCar, Integer> {
}
