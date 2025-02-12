package org.example.api.DTO;

import org.example.api.Model.Item;

public class ItemDTO {
    private int id;
    private String name;
    private int weight;

    public ItemDTO(Item item) {
        this.id = (int) item.getId();
        this.name = item.getName();
        this.weight = item.getWeight();
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

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
