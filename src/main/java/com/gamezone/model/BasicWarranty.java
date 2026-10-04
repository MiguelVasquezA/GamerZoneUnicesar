package com.gamezone.model;

import java.time.LocalDate;

/**
 * Concrete implementation representing a factory basic warranty (6 months duration, $0 additional cost).
 */
public class BasicWarranty extends Warranty {

    public BasicWarranty(String id, Product product, Sale sale, LocalDate startDate) {
        super(id, product, sale, startDate);
    }

    @Override
    public int getDurationInMonths() {
        return 6;
    }

    @Override
    public String getWarrantyType() {
        return "Basic Warranty";
    }

    @Override
    public double getAdditionalCost() {
        return 0.0;
    }
}
