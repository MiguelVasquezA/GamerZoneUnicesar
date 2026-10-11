package com.gamezone.validation;

import com.gamezone.exceptions.BusinessRuleException;
import com.gamezone.exceptions.InvalidDataException;
import com.gamezone.exceptions.ResourceNotFoundException;
import com.gamezone.model.Product;
import com.gamezone.model.Sale;

import java.util.List;

/**
 * Dedicated validator class for sale transaction integrity.
 *
 * @author Miguel Vasquez
 * @version 1.0
 */
public class SaleValidator {

    /**
     * Validates required participants and items for a sale transaction.
     *
     * @param customer customer entity or ID
     * @param seller   seller entity or ID
     * @param products list of items to purchase
     */
    public static void validateSaleData(Object customer, Object seller, List<Product> products) {
        if (customer == null) {
            throw new InvalidDataException("Customer", "Sale transaction requires a valid customer.");
        }
        if (seller == null) {
            throw new InvalidDataException("Seller", "Sale transaction requires an assigned seller.");
        }
        if (products == null || products.isEmpty()) {
            throw new BusinessRuleException("Sale transaction must contain at least one item.");
        }
    }

    /**
     * Validates that a sale entity exists.
     *
     * @param sale   sale instance
     * @param saleId searched sale transaction ID
     */
    public static void validateSaleExists(Sale sale, String saleId) {
        if (sale == null) {
            throw new ResourceNotFoundException("Sale", saleId);
        }
    }
}
