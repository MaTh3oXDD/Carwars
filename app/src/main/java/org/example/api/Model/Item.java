package org.example.api.Model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int weight;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<BagItem> bagItems;

    public Item(int id, String name, int weight) {
        this.id = id;
        this.name = name;
        this.weight = weight;
    }

    public Item() {
    }

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

    public List<BagItem> getBagItems() {
        return bagItems;
    }

    public void setBagItems(List<BagItem> bagItems) {
        this.bagItems = bagItems;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
