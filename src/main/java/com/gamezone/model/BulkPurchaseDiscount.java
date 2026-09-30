package com.gamezone.model;

import java.time.LocalDate;

public class BulkPurchaseDiscount extends Promotion {
    private int minQuantity;
    private double percentage;

    public BulkPurchaseDiscount(String id, String name, LocalDate startDate, LocalDate endDate, double percentage, int minQuantity) {
        super(id, name, startDate, endDate);
        this.percentage = percentage;
        this.minQuantity = minQuantity;
    }

    public int getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(int minQuantity) {
        this.minQuantity = minQuantity;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public double calculateDiscount(Sale sale) {
        if (sale == null || sale.getItems() == null) return 0.0;
        int totalQuantity = sale.getItems().stream().mapToInt(item -> item.getQuantity()).sum();
        if (totalQuantity >= minQuantity) {
            return sale.getTotal() * (percentage / 100.0);
        }
        return 0.0;
    }
}