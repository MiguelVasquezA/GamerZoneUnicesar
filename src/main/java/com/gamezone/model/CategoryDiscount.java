package com.gamezone.model;

import java.time.LocalDate;

/**
 * Concrete class representing a percentage discount applicable exclusively to products of a target category.
 *
 * @author Miguel Vasquez
 * @version 1.1
 */
public class CategoryDiscount extends Promotion {
    private double percentage;
    private String targetCategory;

    /**
     * Constructs a CategoryDiscount promotion instance.
     *
     * @param id             unique identifier
     * @param name           campaign name
     * @param startDate      start date
     * @param endDate        end date
     * @param percentage     discount percentage
     * @param targetCategory target category ("VIDEOGAME", "CONSOLE", "ACCESSORY")
     */
    public CategoryDiscount(String id, String name, LocalDate startDate, LocalDate endDate, double percentage, String targetCategory) {
        super(id, name, startDate, endDate);
        this.targetCategory = targetCategory;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public String getTargetCategory() {
        return targetCategory;
    }

    public void setTargetCategory(String targetCategory) {
        this.targetCategory = targetCategory;
    }

    @Override
    public double calculateDiscount(Sale sale) {
        if (sale == null || sale.getItems() == null) {
            return 0.0;
        }

        double eligibleSubtotal = 0.0;

        for (SaleItem item : sale.getItems()) {
            Product product = item.getProduct();
            if (product == null) continue;

            boolean isEligible = false;

            if ("VIDEOGAME".equalsIgnoreCase(targetCategory) && product instanceof VideoGame) {
                isEligible = true;
            } else if ("CONSOLE".equalsIgnoreCase(targetCategory) && product instanceof Console) {
                isEligible = true;
            } else if ("ACCESSORY".equalsIgnoreCase(targetCategory) && product instanceof Accessory) {
                isEligible = true;
            }

            if (isEligible) {
                eligibleSubtotal += item.getSubtotal();
            }
        }

        return eligibleSubtotal * (percentage / 100.0);
    }
}