package com.gamezone.model;

import java.time.LocalDate;

/**
 * Abstract base class representing a promotion in the system.
 */
public abstract class Promotion {
    private String id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;

    /**
     * Constructs a promotion with the specified details.
     *
     * @param id        The promotion identifier.
     * @param name      The promotion name.
     * @param startDate The start date.
     * @param endDate   The end date.
     */
    public Promotion(String id, String name, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Checks if the promotion is active on a given date.
     *
     * @param date The date to check.
     * @return true if the date is between start and end date (inclusive), false otherwise.
     */
    public boolean isActive(LocalDate date) {
        if (date == null) return false;
        return (date.isEqual(startDate) || date.isAfter(startDate)) &&
                (date.isEqual(endDate) || date.isBefore(endDate));
    }

    /**
     * Calculates the discount amount for a given sale.
     *
     * @param sale The sale to calculate the discount for.
     * @return The discount amount.
     */
    public abstract double calculateDiscount(Sale sale);
}