package org.example.api.Repository;

import org.example.api.Model.Car;
import org.example.api.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Integer> {
    Optional<? extends Car> findById(User selectedCarId);
}
