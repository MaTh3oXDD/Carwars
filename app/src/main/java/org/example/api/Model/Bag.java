package org.example.api.Model;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Bag")
public class Bag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int capacity;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "bag", cascade = CascadeType.ALL)
    private List<BagItem> bagItems;

    public Bag(int id, int capacity, User user, List<BagItem> bagItems) {
        this.id = id;
        this.capacity = capacity;
        this.user = user;
        this.bagItems = bagItems;
    }

    public Bag() {

    }

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
