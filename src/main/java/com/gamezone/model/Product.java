package com.gamezone.model;

/**
 * Represents a generic product in the GameZone store.
 * This is an abstract base class containing common attributes and behaviors
 * for all commercialized products.
 *
 * @author Miguel Vasquez
 * @version 1.0
 */

public abstract class Product {
    private String id;
    private String title;
    private double price;
    private int stock;

    /**
     * Constructs a Product.
     * @param id product id
     * @param title product title
     * @param price unit price
     * @param stock inventory quantity
     */

    public Product(String id, double price, int stock, String title) {
        this.id = id;
        this.price = price;
        this.stock = stock;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getTitle() {
        return title;
    }

    /**
     * Returns the complete description of the product.
     * @return formatted description string
     */

    public abstract String getDescription();
}
