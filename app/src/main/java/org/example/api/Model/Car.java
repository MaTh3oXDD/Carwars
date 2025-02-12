package org.example.api.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "car_name", length = 50, nullable = false)
    private String name;

    @Column(name = "speed", length = 50, nullable = false)
    private int speed;

    // Relacja z User jako ManyToMany
    @ManyToMany(mappedBy = "cars") // Druga strona relacji
    @JsonBackReference // Referencja cykliczna (ignorowana przy serializacji)
    private List<User> users; // Lista użytkowników, którzy posiadają ten samochód

    // Gettery i settery
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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
