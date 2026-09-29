package com.gamezone.model;

import java.util.List;

/**
 * Concrete class representing a clable Accessory.
 */

public class Cable extends Accessory {
    private double length;
    private String connectorType;

    public Cable(String id, double price, int stockQuantity, String title, List<String> compatibleConsoleIds, String connectorType, double length) {
        super(id, price, stockQuantity, title, compatibleConsoleIds);
        this.connectorType = connectorType;
        this.length = length;
    }

    public String getConnectorType() {
        return connectorType;
    }

    public void setConnectorType(String connectorType) {
        this.connectorType = connectorType;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    @Override
    public String getDescription() {
        return String.format("Cable: %s | Price: $%.2f | Stock: %d | Length: %.1fm | Connector: %s",
                getTitle(), getPrice(), getStock(), length, connectorType);
    }
}
