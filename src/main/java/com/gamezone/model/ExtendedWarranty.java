package com.gamezone.model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Concrete implementation representing an extended warranty (12 months duration).
 * Implements Serializable to allow binary repository persistence.
 */
public class ExtendedWarranty extends Warranty implements Serializable {

    private static final long serialVersionUID = 1L;

    public ExtendedWarranty(String id, Product product, Sale sale, LocalDate startDate) {
        super(id, product, sale, startDate);
    }

    @Override
    public int getDurationInMonths() {
        return 12;
    }

    @Override
    public String getWarrantyType() {
        return "Garantía Extendida";
    }

    @Override
    public double getAdditionalCost() {
        return 50.0; // O el costo configurado en tu proyecto
    }
}