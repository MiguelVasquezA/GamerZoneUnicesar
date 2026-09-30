package com.gamezone.model;

import java.time.LocalDate;

/**
 * Concrete class representing a flat percentage discount applied to the total sale amount.
 *
 * @author Migue
 * @version 1.0
 */
public class PercentageDiscount extends Promotion {
    private double percentage;

    /**
     * Constructs a PercentageDiscount promotion instance.
     *
     * @param id         unique identifier
     * @param name       campaign name
     * @param startDate  start date
     * @param endDate    end date
     * @param percentage discount percentage (e.g., 15.0 for 15%)
     */
    public PercentageDiscount(String id, String name, LocalDate startDate, LocalDate endDate, double percentage) {
        super(id, name, startDate, endDate);
        this.percentage = percentage;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public double calculateDiscount(Sale sale){
        if (sale == null){
            return 0.0;
        }
        return sale.getTotalAmount()*(percentage / 100.0);
    }
}
