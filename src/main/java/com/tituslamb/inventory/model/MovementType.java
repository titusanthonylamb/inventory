package com.tituslamb.inventory.model;

public enum MovementType {
    RECEIVED,     // new stock came in
    USED,         // stock went out (used, sold, given away)
    ADJUSTMENT    // corrections: damaged, miscounted, lost
}
