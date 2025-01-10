package org.example.api.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "Bag_Item")
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
}

