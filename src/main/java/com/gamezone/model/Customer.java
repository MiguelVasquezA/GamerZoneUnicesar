package com.gamezone.model;

import java.util.List;
import java.util.ArrayList;

/**
 * represents a customer who interacts with the store,
 * inheriting basic personal attributes and maintaining a purchase history.
 */
public class Customer extends Person {
    private String email;
    private List<Sale> purchaseHistory;

    /**
     * creates a new customer with the given personal information and email
     *
     * @param name the customer's full name
     * @param identification the customer's identification number
     * @param phoneNumber the customer's contact phone number
     * @param email the customer's email address
     */
    public Customer(String name, String identification, String phoneNumber, String email) {
        super(name, identification, phoneNumber);
        this.email = email;
        this.purchaseHistory = new ArrayList<>();
    }

    /**
     * returns a textual description specific to the customer's role
     *
     * @return a description including the customer's name and email
     */
    @Override
    public String getDescription() {
        return "Customer: " + getName() + " - Email: " + email;
    }

    /**
     * returns the customer's email address
     *
     * @return the email of the customer
     */
    public String getEmail() {
        return email;
    }

    /**
     * sets or updates the customer's email address
     *
     * @param email the new email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * returns the customer's purchase history
     *
     * @return a list of sales representing the purchase history
     */
    public List<Sale> getPurchaseHistory() {
        return purchaseHistory;
    }

    /**
     * sets or updates the customer's purchase history
     *
     * @param purchaseHistory the new purchase history list to set
     */
    public void setPurchaseHistory(List<Sale> purchaseHistory) {
        this.purchaseHistory = purchaseHistory;
    }
}
