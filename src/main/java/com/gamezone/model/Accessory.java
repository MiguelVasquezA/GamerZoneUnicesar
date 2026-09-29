package com.gamezone.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class representing a generic accessory product.
 */
public abstract class Accessory extends Product {
private List<String> compatibleConsoleIds;

    public Accessory(String id, double price, int stockQuantity, String title, List<String> compatibleConsoleIds) {
        super(id, price, stockQuantity, title);
        this.compatibleConsoleIds = compatibleConsoleIds != null ? compatibleConsoleIds : new ArrayList<>();
    }

    public List<String> getCompatibleConsoleIds() {
        return compatibleConsoleIds;
    }

    public void setCompatibleConsoleIds(List<String> compatibleConsoleIds) {
        this.compatibleConsoleIds = compatibleConsoleIds;
    }

    public boolean isCompatibleWith(String consoleId){
        return compatibleConsoleIds.contains(consoleId);
    }
    @Override
    public abstract String getDescription();
}
