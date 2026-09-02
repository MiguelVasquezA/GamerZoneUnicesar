package com.gamezone.model;

/**
 * Represents a video game console in the store.
 * @author Miguel Vasquez
 * @version 1.0
 */

public class Console extends Product{
    private String brand;
    private String model;
    private String generation;

    /**
     * Constructs a Console.
     * @param id product id
     * @param title product title
     * @param price unit price
     * @param stock inventory quantity
     * @param brand manufacturer brand
     * @param model specific console model
     * @param generation console generation
     */

    public Console(String id, double price, int stock, String title, String brand, String generation, String model) {
        super(id, price, stock, title);
        this.brand = brand;
        this.generation = generation;
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public String getGeneration() {
        return generation;
    }

    public String getModel() {
        return model;
    }

    /**
     * Returns the complete description of the console.
     * @return formatted description string
     */

    @Override
    public String getDescription(){
        return "Console: "+brand+
                " "+ model+
                " ("+generation+
                " Generation) | Price: $"+getPrice();
    }
}
