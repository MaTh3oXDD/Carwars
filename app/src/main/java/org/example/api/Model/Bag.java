package org.example.api.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.List;

@Entity
@Table(name = "bags") // Tabela
public class Bag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int capacity;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user; // Usuń tu @JsonBackReference oraz @JsonIgnore (referencję można obsłużyć w `User`)

    @OneToMany(mappedBy = "bag", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Serializujemy listę "BagItem"
    private List<BagItem> bagItems;

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
}
