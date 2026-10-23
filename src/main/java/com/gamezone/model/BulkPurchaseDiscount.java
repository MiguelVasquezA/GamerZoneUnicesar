package com.gamezone.model;

import java.time.LocalDate;

/**
 * Represents a bulk purchase discount promotion that applies a percentage discount
 * when the sale contains a minimum quantity of products.
 */
public class BulkPurchaseDiscount extends Promotion {
    private int minQuantity;
    private double percentage;

    /**
     * Constructs a new BulkPurchaseDiscount with the specified details.
     *
     * @param name        The promotion name.
     * @param startDate   The start date of the promotion.
     * @param endDate     The end date of the promotion.
     * @param percentage  The discount percentage to apply.
     * @param minQuantity The minimum quantity of products required for the discount.
     * @ The promotion identifier.
     */
    public BulkPurchaseDiscount(String id, String name, LocalDate startDate, LocalDate endDate, double percentage, int minQuantity) {
        super(id, name, startDate, endDate);
        this.percentage = percentage;
        this.minQuantity = minQuantity;
    }

    /**
     * Gets the minimum quantity required.
     *
     * @return The minimum quantity of products.
     */
    public int getMinQuantity() {
        return minQuantity;
    }

    /**
     * Sets the minimum quantity required.
     *
     * @param minQuantity The new minimum quantity.
     */
    public void setMinQuantity(int minQuantity) {
        this.minQuantity = minQuantity;
    }

    /**
     * Gets the discount percentage.
     *
     * @return The discount percentage.
     */
    public double getPercentage() {
        return percentage;
    }

    /**
     * Sets the discount percentage.
     *
     * @param percentage The new discount percentage.
     */
    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    /**
     * Calculates the discount amount for the given sale based on the bulk purchase rule.
     *
     * @param sale The sale to calculate the discount for.
     * @return The discount amount in currency units, or 0.0 if conditions are not met.
     */
    @Override
    public double calculateDiscount(Sale sale) {
        if (sale == null || sale.getItems() == null) {
            return 0.0;
        }

        // Suma la cantidad total de unidades vendidas considerando cada SaleItem
        int totalQuantity = 0;
        for (SaleItem item : sale.getItems()) {
            if (item != null) {
                totalQuantity += item.getQuantity();
            }
        }

        if (totalQuantity >= minQuantity) {
            return sale.getTotalAmount() * (percentage / 100.0);
        }

        return 0.0;
    }
}