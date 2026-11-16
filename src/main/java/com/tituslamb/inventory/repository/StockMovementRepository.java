package com.tituslamb.inventory.repository;

import com.tituslamb.inventory.model.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
    List<StockMovement> findByItemIdOrderByCreatedAtDesc(Long itemId);
}