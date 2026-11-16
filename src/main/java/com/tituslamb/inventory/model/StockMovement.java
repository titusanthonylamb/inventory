package com.tituslamb.inventory.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class StockMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "item_id")
    private Item item;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MovementType type;

    @Column(nullable = false)
    private int quantityChange;     // +12 for received, -3 for used

    private String note;

    @Column(nullable = false)
    private Instant createdAt;

    protected StockMovement() {}

    public StockMovement(Item item, MovementType type, int quantityChange, String note) {
        this.item = item;
        this.type = type;
        this.quantityChange = quantityChange;
        this.note = note;
        this.createdAt = Instant.now();
    }

    // Getters only. No setters.
    public Long getId() {
        return id;
    }

    public Item getItem() {
        return item;
    }

    public MovementType getType() {
        return type;
    }

    public int getQuantityChange() {
        return quantityChange;
    }

    public String getNote() {
        return note;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}