package com.gamezone.model;

import java.time.LocalDate;

/**
 * Abstract base class representing a product warranty in the system.
 */
public abstract class Warranty {
    private String id;
    private Product product;
    private Sale sale;
    private LocalDate startDate;
    private LocalDate endDate;

    /**
     * Constructs a new Warranty instance and automatically calculates the end date.
     *
     * @param id        the unique identifier of the warranty
     * @param product   the associated product
     * @param sale      the associated sale transaction
     * @param startDate the activation/sale date
     */
    public Warranty(String id, Product product, Sale sale, LocalDate startDate) {
        this.id = id;
        this.product = product;
        this.sale = sale;
        this.startDate = startDate;
        this.endDate = startDate.plusMonths(getDurationInMonths());
    }

    public String getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public Sale getSale() {
        return sale;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Returns the duration of the warranty in months.
     *
     * @return duration in months
     */
    public abstract int getDurationInMonths();

    /**
     * Returns the display name of the warranty type.
     *
     * @return warranty type description
     */
    public abstract String getWarrantyType();

    /**
     * Returns the additional cost added to the sale total.
     *
     * @return additional warranty cost
     */
    public abstract double getAdditionalCost();

    /**
     * Checks whether the warranty is active on a specific date.
     *
     * @param date the date to check
     * @return true if active, false otherwise
     */
    public boolean isActive(LocalDate date) {
        if (date == null) {
            return false;
        }
        return (date.isEqual(startDate) || date.isAfter(startDate)) &&
                (date.isEqual(endDate) || date.isBefore(endDate));
    }

    /**
     * Generates a formatted warranty certificate string.
     *
     * @return formatted certificate details
     */
    public String generateWarrantyCertificate() {
        return String.format(
                "=== WARRANTY CERTIFICATE ===\n" +
                        "Warranty ID: %s\n" +
                        "Type: %s\n" +
                        "Product: %s\n" +
                        "Sale ID: %s\n" +
                        "Start Date: %s\n" +
                        "Expiration Date: %s\n" +
                        "Additional Cost: $%.2f\n" +
                        "===========================",
                id, getWarrantyType(),
                product != null ? product.getTitle() : "N/A",
                sale != null ? sale.getSaleId() : "N/A",
                startDate, endDate, getAdditionalCost()
        );
    }
}
