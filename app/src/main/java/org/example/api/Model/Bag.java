package org.example.api.Model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.List;

@Entity
@Table(name = "bags") // Tabela nazywa się "bags"
public class Bag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Automatyczne generowanie klucza głównego
    private int id; // Klucz główny (Primary Key)

    @Column(nullable = false) // Każda torba musi mieć określoną pojemność
    private int capacity;

    // Relacja ManyToOne z User - każda torba należy do jednego użytkownika
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false) // Klucz obcy w tabeli "bags"
    @JsonBackReference // Rozwiązuje cykliczne odniesienia w JSON-ie (dotyczy JPA + Jackson)
    private User user;

    // Opcjonalny przykład relacji z BagItem
    @OneToMany(mappedBy = "bag", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BagItem> bagItems;

    // Konstruktor domyślny (wymagany przez Hibernate)
    public Bag() {
    }

    // Konstruktor z parametrami
    public Bag(int capacity, User user) {
        this.capacity = capacity;
        this.user = user;
    }

    // Gettery i settery
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<BagItem> getBagItems() {
        return bagItems;
    }

    public void setBagItems(List<BagItem> bagItems) {
        this.bagItems = bagItems;
    }

    @Override
    public String toString() {
        return "Bag{" +
                "id=" + id +
                ", capacity=" + capacity +
                ", user=" + (user != null ? user.getUsername() : null) +
                '}';
    }
}
