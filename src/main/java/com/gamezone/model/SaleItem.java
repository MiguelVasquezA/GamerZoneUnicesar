package com.gamezone.model;

/**
 * Represents a single line item in a sale with a frozen unit price at the transaction time.
 * Implements the Protected Variations GRASP pattern.
 */
public class SaleItem {
    private final Product product;
    private final int quantity;
    private final double unitPrice;

    /**
     * Constructs a SaleItem instance with a snapshot of the current product price.
     *
     * @param product  the purchased product
     * @param quantity the quantity of items purchased
     * @throws IllegalArgumentException if product is null or quantity is less than or equal to zero
     */
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

    /**
     * Calculates the subtotal for this line item based on the frozen unit price.
     *
     * @return subtotal amount
     */
    public double getSubtotal() {
        return unitPrice * quantity;
    }
}