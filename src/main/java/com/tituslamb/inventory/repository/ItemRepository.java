package com.tituslamb.inventory.repository;

import com.tituslamb.inventory.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findBySku(String sku);

    boolean existsBySku(String sku);

    List<Item> findByCategoryId(Long categoryId);

    @Query("SELECT i FROM Item i WHERE i.quantityOnHand <= i.reorderThreshold")
    List<Item> findLowStock();
}
