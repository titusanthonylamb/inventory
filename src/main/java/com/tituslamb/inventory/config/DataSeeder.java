package com.tituslamb.inventory.config;

import com.tituslamb.inventory.model.*;
import com.tituslamb.inventory.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final CategoryRepository categories;
    private final ItemRepository items;
    private final StockMovementRepository movements;

    public DataSeeder(CategoryRepository categories, ItemRepository items,
                      StockMovementRepository movements) {
        this.categories = categories;
        this.items = items;
        this.movements = movements;
    }

    @Override
    public void run(String... args) {
        Category cleaning = categories.save(new Category("Cleaning supplies"));
        Category paper = categories.save(new Category("Paper goods"));

        Item gloves = items.save(new Item("Nitrile gloves", "CLN-001", "boxes", 5, cleaning));
        Item towels = items.save(new Item("Paper towels", "PPR-001", "rolls", 10, paper));

        receive(gloves, 12, "Initial stock");
        receive(towels, 8, "Initial stock");

        System.out.println("=== Low stock: " +
            items.findLowStock().stream().map(Item::getName).toList());
    }

    private void receive(Item item, int qty, String note) {
        item.applyChange(qty);
        items.save(item);
        movements.save(new StockMovement(item, MovementType.RECEIVED, qty, note));
    }
}
