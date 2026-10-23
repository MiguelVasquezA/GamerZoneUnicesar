package com.gamezone.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class representing a generic accessory product.
 */
public abstract class Accessory extends Product {
    private List<String> compatibleConsoleIds;

    public Accessory(String id, double price, int stockQuantity, String title, List<String> compatibleConsoleIds) {
        super(id, title, price, stockQuantity);
        this.compatibleConsoleIds = compatibleConsoleIds != null ? compatibleConsoleIds : new ArrayList<>();
    }

    public List<String> getCompatibleConsoleIds() {
        return compatibleConsoleIds;
    }

    public void setCompatibleConsoleIds(List<String> compatibleConsoleIds) {
        this.compatibleConsoleIds = compatibleConsoleIds;
    }

    public String getDescription() {
        return String.format("%s | Price: $%.2f | Stock: %d", getTitle(), getPrice(), getStock());
    }
}