package org.example.api.Model;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "car_name", length = 50, nullable = false)
    private String name;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "car-ownership")
    private List<OwnershipOfCar> ownershipOfCars;

    // Gettery i setter


public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<OwnershipOfCar> getOwnershipOfCars() {
        return ownershipOfCars;
    }

    public void setOwnershipOfCars(List<OwnershipOfCar> ownershipOfCars) {
        this.ownershipOfCars = ownershipOfCars;
    }
}
