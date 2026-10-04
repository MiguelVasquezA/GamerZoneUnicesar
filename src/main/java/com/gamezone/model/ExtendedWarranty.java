package com.gamezone.model;

import java.time.LocalDate;

/**
 * Concrete implementation representing an extended warranty (12 months duration, 10% of product price).
 */
public class ExtendedWarranty extends Warranty {

    public ExtendedWarranty(String id, Product product, Sale sale, LocalDate startDate) {
        super(id, product, sale, startDate);
    }

    @Override
    public int getDurationInMonths() {
        return 12;
    }

    @Override
    public String getWarrantyType() {
        return "Extended Warranty";
    }

    @Override
    public double getAdditionalCost() {
        if (getProduct() != null) {
            return getProduct().getPrice() * 0.10;
        }
        return 0.0;
    }
}
