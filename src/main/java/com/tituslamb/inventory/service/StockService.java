package com.tituslamb.inventory.service;

import com.tituslamb.inventory.exception.NotFoundException;
import com.tituslamb.inventory.model.*;
import com.tituslamb.inventory.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockService {

    private final ItemRepository items;
    private final StockMovementRepository movements;

    public StockService(ItemRepository items, StockMovementRepository movements) {
        this.items = items;
        this.movements = movements;
    }

    @Transactional
    public StockMovement recordMovement(Long itemId, MovementType type, int quantity, String note) {
        Item item = items.findById(itemId)
            .orElseThrow(() -> new NotFoundException("Item " + itemId + " not found"));

        int change = switch (type) {
            case RECEIVED -> requirePositive(quantity);
            case USED -> -requirePositive(quantity);
            case ADJUSTMENT -> {
                if (quantity == 0) throw new IllegalArgumentException("Adjustment can't be zero");
                yield quantity;   // can be + or -
            }
        };

        item.applyChange(change);   // throws if stock would go negative
        return movements.save(new StockMovement(item, type, change, note));
        // no items.save(item) needed, see below
    }

    private int requirePositive(int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be greater than 0");
        return quantity;
    }
}
