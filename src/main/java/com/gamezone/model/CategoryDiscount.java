package com.gamezone.model;

import java.time.LocalDate;

/**
 * Concrete class representing a percentage discount applicable exclusively to products of a target category.
 *
 * @author Miguel Vasquez
 * @version 1.0
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
     * @param targetCategory target category ("VIDEOGAME", "CONSOLE")
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
    public double calculateDiscount(Sale sale){
        if (sale == null || sale.getProducts() == null){
            return 0.0;
        }

        double eligibleSubtotal = 0.0;
        for(Product product : sale.getProducts()){
            if("VIDEOGAME".equalsIgnoreCase(targetCategory)&& product instanceof VideoGame){
                eligibleSubtotal += product.getPrice();
            }else if("CONSOLE".equalsIgnoreCase(targetCategory)&& product instanceof Console){
                eligibleSubtotal += product.getPrice();
            }
        }

        return eligibleSubtotal * (percentage / 100.0);
    }
}
