package com.example.restaurantbackendapplication1.inventory.model.enums;

public enum TransactionType {
    PURCHASE,       // stock in
    SALE,           // stock out
    CONSUME,        // usage (recipe/production)
    ADJUSTMENT,     // manual correction
    TRANSFER_IN,    // incoming transfer
    TRANSFER_OUT,   // outgoing transfer
    RETURN,                 // customer return (stock IN)
    RETURN_TO_SUPPLIER      // return to supplier (stock OUT)
}
