package com.gamezone.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a sale transaction within the GameZone store.
 * Manages transaction details, line items, seller information, and applied discounts.
 *
 * Implements Information Expert, Creator, and Protected Variations GRASP patterns.
 *
 * @author Miguel Vasquez
 * @version 2.0
 */
public class Sale implements Serializable {
    private static final long serialVersionUID = 1L;

    private String saleId;
    private String date;
    private String client;
    private String seller;
    private List<SaleItem> items; // Replaces direct List<Product> to snapshot prices
    private double totalAmount;
    private String appliedPromotionName;
    private double discountAmount;

    /**
     * Constructs a new Sale instance with initial purchase details.
     */
    public Sale(String saleId, String client, List<SaleItem> items, double discountAmount, String date, String seller) {
        this.saleId = saleId;
        this.client = client;
        this.items = items != null ? items : new ArrayList<>();
        this.discountAmount = discountAmount;
        this.date = date;
        this.seller = seller != null ? seller : "N/A";
        this.appliedPromotionName = "None";
        this.totalAmount = calculateTotal();
    }

    /**
     * Overloaded constructor for compatibility.
     */
    public Sale(String saleId, String client, List<SaleItem> items, double discountAmount, String date) {
        this(saleId, client, items, discountAmount, date, "N/A");
    }

    /**
     * Creator Pattern: Sale manages and appends line items to its internal aggregate.
     *
     * @param item the SaleItem instance to add
     */
    public void addItem(SaleItem item) {
        if (this.items == null) {
            this.items = new ArrayList<>();
        }
        this.items.add(item);
        this.totalAmount = calculateTotal();
    }

    /**
     * Information Expert: Calculates total amount summing subtotal from line items.
     *
     * @return the net total price of the sale after discounts
     */
    public double calculateTotal() {
        double total = 0.0;
        if (items != null) {
            for (SaleItem item : items) {
                total += item.getSubtotal();
            }
        }
        return Math.max(0.0, total - discountAmount);
    }

    // Getters and Setters
    public String getSaleId() { return saleId; }
    public void setSaleId(String saleId) { this.saleId = saleId; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getClient() { return client; }
    public void setClient(String client) { this.client = client; }

    public String getSeller() { return seller; }
    public void setSeller(String seller) { this.seller = seller; }

    public List<SaleItem> getItems() { return items; }
    public void setItems(List<SaleItem> items) {
        this.items = items;
        this.totalAmount = calculateTotal();
    }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getAppliedPromotionName() { return appliedPromotionName; }
    public void setAppliedPromotionName(String appliedPromotionName) { this.appliedPromotionName = appliedPromotionName; }

    public double getDiscountAmount() { return discountAmount; }
    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
        this.totalAmount = calculateTotal();
    }
}