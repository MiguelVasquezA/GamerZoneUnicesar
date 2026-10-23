package com.gamezone.model;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class Warranty implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private Product product;
    private Sale sale;
    private LocalDate startDate;
    private LocalDate endDate;

    public Warranty(String id, Product product, Sale sale, LocalDate startDate) {
        this.id = id;
        this.product = product;
        this.sale = sale;
        this.startDate = startDate;
        this.endDate = startDate.plusMonths(getDurationInMonths());
    }

    public String getId() { return id; }
    public Product getProduct() { return product; }
    public Sale getSale() { return sale; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }

    public abstract int getDurationInMonths();
    public abstract String getWarrantyType();
    public abstract double getAdditionalCost();

    public boolean isActive(LocalDate date) {
        if (date == null) return false;
        return (date.isEqual(startDate) || date.isAfter(startDate)) &&
                (date.isEqual(endDate) || date.isBefore(endDate));
    }
}