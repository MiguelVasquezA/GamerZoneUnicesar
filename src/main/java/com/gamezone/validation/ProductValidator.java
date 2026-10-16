package com.gamezone.validation;

import com.gamezone.exceptions.BusinessRuleException;
import com.gamezone.exceptions.InvalidDataException;
import com.gamezone.exceptions.ResourceNotFoundException;
import com.gamezone.model.Product;

/**
 * Dedicated validator class for product domain rules and data integrity.
 *
 * @author Miguel Vasquez
 * @version 1.0
 */
public class ProductValidator {

    /**
     * Validates primitive data fields required for product creation.
     *
     * @param id    product identifier
     * @param title product title
     * @param price product price
     * @param stock product stock quantity
     */
    public static void validateProductData(String id, String title, double price, int stock) {
        if (id == null || id.trim().isEmpty()) {
            throw new InvalidDataException("ID", "Product identifier cannot be empty.");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new InvalidDataException("Title", "Product title cannot be empty.");
        }
        if (price <= 0) {
            throw new InvalidDataException("Price", "Price must be a positive number greater than zero.");
        }
        if (stock < 0) {
            throw new InvalidDataException("Stock", "Stock quantity cannot be negative.");
        }
    }

    /**
     * Validates that a product instance is not null.
     *
     * @param product   product entity
     * @param productId searched identifier
     */
    public static void validateProductExists(Product product, String productId) {
        if (product == null) {
            throw new ResourceNotFoundException("Product", productId);
        }
    }

    /**
     * Validates stock availability for sale processing.
     *
     * @param product           product entity
     * @param requestedQuantity requested quantity
     */
    public static void validateStockAvailability(Product product, int requestedQuantity) {
        if (product == null) {
            throw new BusinessRuleException("Cannot check stock for a null product.");
        }
        if (product.getStock() < requestedQuantity) {
            throw new BusinessRuleException("Insufficient stock for product '" + product.getTitle()
                    + "'. Available: " + product.getStock() + ", Requested: " + requestedQuantity);
        }
    }
}
