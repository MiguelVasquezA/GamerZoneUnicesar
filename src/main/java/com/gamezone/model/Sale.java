package com.gamezone.model;

import java.io.Serializable;
import java.util.List;

/**
 * Represents a sale transaction within the GameZone store.
 *
 * @author Miguel Vasquez
 * @version 1.0
 */

public class Sale implements Serializable{
    private static final long serialVersionUID = 1L;

    private String saleId;
    private String date;
    private String client;
    private String seller;
    private List<Product> products;
    private double totalAmount;

    /**
     * Constructs a new Sale with the specified details.
     *
     * @param saleId unique identifier for the sale
     * @param date date of the transaction
     * @param client client who made the purchase
     * @param seller vendor who attended the sale
     * @param products list of products purchased
     */

    public Sale(String client, String date, List<Product> products, String saleId, String seller, double totalAmount) {
        this.client = client;
        this.date = date;
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

    private double calculateTotal(){
        double total = 0.0;
        if (products != null){
            for (Product p: products){
                total += p.getPrice();
            }
        }
        return total;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getSaleId() {
        return saleId;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}
