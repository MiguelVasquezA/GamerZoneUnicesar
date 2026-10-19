package com.gamezone.model;

public class Console extends Product {
    private String brand;
    private int storageCapacityGb;

    public Console(String id, String title, double price, int stock, String brand, int storageCapacityGb) {
        super(id, title, price, stock);
        this.brand = brand;
        this.storageCapacityGb = storageCapacityGb;
    }

    @Override
    public String toCsvRecord() {
        return String.join(",", "CONSOLE", getId(), getTitle(), String.valueOf(getPrice()), String.valueOf(getStock()), brand, String.valueOf(storageCapacityGb));
    }

    public String getBrand() { return brand; }
    public int getStorageCapacityGb() { return storageCapacityGb; }
}