package com.gamezone.model;

import java.io.Serializable;

/**
 * Represents a single line item in a sale with a frozen unit price at the transaction time.
 * Implements the Protected Variations GRASP pattern.
 */
public class SaleItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Product product;
    private final int quantity;
    private final double unitPrice;

    public SaleItem(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = product.getPrice(); // Price snapshot to prevent historical alteration
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double getSubtotal() {
        return unitPrice * quantity;
    }
}