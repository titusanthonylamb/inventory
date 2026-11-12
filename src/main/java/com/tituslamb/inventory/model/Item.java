package com.tituslamb.inventory.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String sku;

    @NotBlank
    private String unit;              // "boxes", "lbs", "each"

    @Column(nullable = false)
    private int quantityOnHand = 0;

    @Min(0)
    private int reorderThreshold;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id")
    private Category category;

    protected Item() {}

    public Item(String name, String sku, String unit, int reorderThreshold, Category category) {
        this.name = name;
        this.sku = sku;
        this.unit = unit;
        this.reorderThreshold = reorderThreshold;
        this.category = category;
    }

    // The ONLY way quantity can change. Stock movements will call this.
    public void applyChange(int change) {
        int newQuantity = quantityOnHand + change;
        if (newQuantity < 0) {
            throw new IllegalArgumentException(
                "Not enough stock: have " + quantityOnHand + ", tried to remove " + (-change));
        }
        quantityOnHand = newQuantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(int quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    public int getReorderThreshold() {
        return reorderThreshold;
    }

    public void setReorderThreshold(int reorderThreshold) {
        this.reorderThreshold = reorderThreshold;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isLowStock() {
        return quantityOnHand <= reorderThreshold;
    }

    // Getters for every field. Setters for everything EXCEPT quantityOnHand.
}
