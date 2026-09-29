package com.gamezone.model;

import java.util.List;

/**
 * Concrete class representing a memory expansion accessory.
 */

public class Memory extends Accessory {
    private int capacity;
    private String memoryType;

    public Memory(String id, double price, int stockQuantity, String title, List<String> compatibleConsoleIds, int capacity, String memoryType) {
        super(id, price, stockQuantity, title, compatibleConsoleIds);
        this.capacity = capacity;
        this.memoryType = memoryType;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getMemoryType() {
        return memoryType;
    }

    public void setMemoryType(String memoryType) {
        this.memoryType = memoryType;
    }

    @Override
    public String getDescription() {
        return String.format("Memory: %s | Price: $%.2f | Stock: %d | Capacity: %dGB | Type: %s",
                getTitle(), getPrice(), getStock(), capacity, memoryType);
    }
}
