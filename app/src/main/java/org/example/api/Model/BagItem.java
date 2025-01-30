package org.example.api.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "BagItems")
public class BagItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "bag_id", nullable = false)
    private Bag bag;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bag getBag() {
        return bag;
    }

    public void setBag(Bag bag) {
        this.bag = bag;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}

