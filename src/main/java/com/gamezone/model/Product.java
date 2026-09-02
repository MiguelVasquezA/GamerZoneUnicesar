package com.gamezone.model;

public abstract class Product {
    private String id;
    private String title;
    private double price;
    private int stock;

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

    public abstract String getDescription();
}
