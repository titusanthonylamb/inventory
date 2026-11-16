package com.tituslamb.inventory.config;

import com.tituslamb.inventory.model.*;
import com.tituslamb.inventory.repository.*;
import com.tituslamb.inventory.service.StockService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final CategoryRepository categories;
    private final ItemRepository items;
    private final StockMovementRepository movements;
    private final StockService stock;

    public DataSeeder(CategoryRepository categories, ItemRepository items,
                      StockMovementRepository movements, StockService stock) {
        this.categories = categories;
        this.items = items;
        this.movements = movements;
        this.stock = stock;
    }

    @Override
    public void run(String... args) {
        Category cleaning = categories.save(new Category("Cleaning supplies"));
        Category paper = categories.save(new Category("Paper goods"));

        Item gloves = items.save(new Item("Nitrile gloves", "CLN-001", "boxes", 5, cleaning));
        Item towels = items.save(new Item("Paper towels", "PPR-001", "rolls", 10, paper));

        stock.recordMovement(gloves.getId(), MovementType.RECEIVED, 12, "Initial stock");
        stock.recordMovement(towels.getId(), MovementType.RECEIVED, 8, "Initial stock");

        // Try to break the rules on purpose
        try {
            stock.recordMovement(towels.getId(), MovementType.USED, 100, "Way too many");
        } catch (IllegalArgumentException e) {
            System.out.println("=== Blocked: " + e.getMessage());
        }

        System.out.println("=== Movements recorded: " + movements.count());
        System.out.println("=== Low stock: " +
            items.findLowStock().stream().map(Item::getName).toList());
    }
}