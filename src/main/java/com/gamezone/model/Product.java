package com.gamezone.model;

import java.io.Serializable;

/**
 * Base abstract class for all product items.
 * Implements Information Expert and Polymorphism GRASP patterns.
 */
public abstract class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private double price;
    private int stock;

    public Product(String id, String title, double price, int stock) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.stock = stock;
    }

    /**
     * Information Expert Pattern (SRP / V-01):
     * Product manages and mutates its own stock state safely.
     */
    public void reduceStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity to reduce must be greater than zero.");
        }
        if (quantity > this.stock) {
            throw new IllegalStateException("Insufficient stock available for product: " + this.title);
        }
        this.stock -= quantity;
    }

    /**
     * Information Expert Pattern: Restores product inventory.
     */
    public void restoreStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity to restore must be greater than zero.");
        }
        this.stock += quantity;
    }

    /**
     * Polymorphism Pattern (OCP / V-07):
     * Abstract method implemented by concrete subclasses to format CSV records without instanceof checks.
     */
    public abstract String toCsvRecord();

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}