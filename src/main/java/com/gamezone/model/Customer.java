package com.gamezone.model;

import java.util.List;
import java.util.ArrayList;

public class Customer extends Person {
    private String email;
    private List<Sale> purchaseHistory;

    public Customer(String name, String identification, String phoneNumber, String email) {
        super(name, identification, phoneNumber);
        this.email = email;
        this.purchaseHistory = new ArrayList<>();
    }

    @Override
    public String getDescription() {
        return "Customer: " + getName() + " - Email: " + email;
    }

    public String getEmail() {
        return email;
    }
}
