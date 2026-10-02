package com.gamezone.model;

import java.io.Serializable;
import java.util.List;

/**
 * Represents a sale transaction within the GameZone store.
 * Stores transaction details, customer and seller references, purchased items,
 * and tracks applied promotions and discount amounts.
 *
 * @author Miguel Vasquez
 * @version 1.1
 */

public class Sale implements Serializable{
    private static final long serialVersionUID = 1L;

    private String saleId;
    private String date;
    private String client;
    private String seller;
    private List<Product> products;
    private double totalAmount;
    private String appliedPromotionName;
    private double discountAmount;

    /**
     * Constructs a new Sale instance with initial purchase details.
     *
     * @param saleId Unique identifier for the sale.
     * @param date Date when the sale occurred.
     * @param client Customer identifier.
     * @param seller Staff/seller identifier.
     * @param products List of purchased products.
     * @param totalAmount Subtotal amount before promotions.
     */

    public Sale(String client, String date, double discountAmount, List<Product> products, String saleId, String seller, double totalAmount) {
        this.appliedPromotionName = "None";
        this.client = client;
        this.date = date;
        this.discountAmount = 0.0;
        this.products = products;
        this.saleId = saleId;
        this.seller = seller;
        this.totalAmount = totalAmount;
    }

    /**
     * Calculates the total amount of the sale based on the products.
     *
     * @return the total price of the sale
     */

    public double calculateTotal(){
        double total = 0.0;
        if (products != null){
            for (Product p: products){
                total += p.getPrice();
            }
        }
        return total;
    }

    /** @return Sale identifier. */
    public String getSaleId() {
        return saleId;
    }

    /** @param saleId Sale identifier to set. */
    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    /** @return Sale date string. */
    public String getDate() {
        return date;
    }

    /** @param date Sale date string to set. */
    public void setDate(String date) {
        this.date = date;
    }

    /** @return Client identifier. */
    public String getClient() {
        return client;
    }

    /** @param client Client identifier to set. */
    public void setClient(String client) {
        this.client = client;
    }

    /** @return Seller identifier. */
    public String getSeller() {
        return seller;
    }

    /** @param seller Seller identifier to set. */
    public void setSeller(String seller) {
        this.seller = seller;
    }

    /** @return Purchased products list. */
    public List<Product> getProducts() {
        return products;
    }

    /** @param products Purchased products list to set. */
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    /** @return Final total amount. */
    public double getTotalAmount() {
        return totalAmount;
    }

    /** @param totalAmount Final total amount to set. */
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    /** @return Applied promotion name. */
    public String getAppliedPromotionName() {
        return appliedPromotionName;
    }

    /** @param appliedPromotionName Applied promotion name to set. */
    public void setAppliedPromotionName(String appliedPromotionName) {
        this.appliedPromotionName = appliedPromotionName;
    }

    /** @return Applied discount amount. */
    public double getDiscountAmount() {
        return discountAmount;
    }

    /** @param discountAmount Applied discount amount to set. */
    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    /**
     * Generates a formatted text receipt containing sale details, item list,
     * subtotal, applied promotion discount, and total final price.
     *
     * @return Formatted receipt string in English.
     */
    public String generateReceipt() {
        StringBuilder sb = new StringBuilder();
        sb.append("=========================================\n");
        sb.append("              SALE RECEIPT               \n");
        sb.append("=========================================\n");
        sb.append("Sale ID    : ").append(saleId).append("\n");
        sb.append("Date       : ").append(date).append("\n");
        sb.append("Client     : ").append(client).append("\n");
        sb.append("Seller     : ").append(seller).append("\n");
        sb.append("-----------------------------------------\n");
        sb.append("PRODUCTS:\n");

        double subtotal = 0.0;
        if (products != null) {
            for (Product p : products) {
                sb.append(" - ").append(p.getTitle())
                        .append(" | $").append(String.format("%.2f", p.getPrice())).append("\n");
                subtotal += p.getPrice();
            }
        }

        sb.append("-----------------------------------------\n");
        sb.append(String.format("Subtotal    : $%.2f\n", subtotal));
        sb.append("Promotion   : ").append(appliedPromotionName != null ? appliedPromotionName : "None").append("\n");
        sb.append(String.format("Discount    : -$%.2f\n", discountAmount));
        sb.append("-----------------------------------------\n");
        sb.append(String.format("TOTAL FINAL : $%.2f\n", totalAmount));
        sb.append("=========================================\n");

        return sb.toString();
    }
    /**
     * Checks if the sale is eligible for return within 30 calendar days.
     *
     * @return true if the current date is within 30 days of the sale date, false otherwise.
     */
    public boolean canBeReturned() {
        if (this.date == null || this.date.trim().isEmpty()) {
            return false;
        }
        try {
            // Extracts the YYYY-MM-DD portion if the string contains time or ISO format
            String dateOnly = this.date.contains("T") ? this.date.split("T")[0] : this.date.trim();
            java.time.LocalDate saleLocalDate = java.time.LocalDate.parse(dateOnly);
            long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(saleLocalDate, java.time.LocalDate.now());
            return daysBetween >= 0 && daysBetween <= 30;
        } catch (java.time.format.DateTimeParseException e) {
            return false;
        }
    }
}
