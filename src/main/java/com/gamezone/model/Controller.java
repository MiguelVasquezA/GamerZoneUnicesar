package com.gamezone.model;

import java.util.List;

/**
 * Concrete class representing a gaming controller accessory
 */

public class Controller extends Accessory {
    private String connectionType;

    public Controller(String id, double price, int stockQuantity, String title, List<String> compatibleConsoleIds, String connectionType) {
        super(id, price, stockQuantity, title, compatibleConsoleIds);
        this.connectionType = connectionType;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    @Override
    public String getDescription() {
        return "Controller: " + getTitle() + " | Price: $" + getPrice() + " | Stock: " + getStock() + " | Connection: " + connectionType;
    }
}
