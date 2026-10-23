package com.gamezone.model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Concrete implementation representing a factory basic warranty (6 months duration, $0 additional cost).
 * Implements Serializable to allow binary repository persistence.
 */
public class BasicWarranty extends Warranty implements Serializable {

    private static final long serialVersionUID = 1L;

    public BasicWarranty(String id, Product product, Sale sale, LocalDate startDate) {
        super(id, product, sale, startDate);
    }

    @Override
    public int getDurationInMonths() {
        return 6;
    }

    @Override
    public String getWarrantyType() {
        return "Garantía Básica";
    }

    @Override
    public double getAdditionalCost() {
        return 0.0;
    }
}