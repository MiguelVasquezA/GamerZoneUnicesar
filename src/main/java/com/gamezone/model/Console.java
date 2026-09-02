package com.gamezone.model;

public class Console extends Product{
    private String brand;
    private String model;
    private String generation;

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

    @Override
    public String getDescription(){
        return "Console: "+brand+
                " "+ model+
                " ("+generation+
                " Generation) | Price: $"+getPrice();
    }
}
