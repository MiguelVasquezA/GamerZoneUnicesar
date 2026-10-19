package com.gamezone.service;

import com.gamezone.model.Sale;
import com.gamezone.model.SaleItem;

/**
 * Pure Fabrication utility responsible for formatting sale receipts for UI display.
 * Implements High Cohesion and Pure Fabrication GRASP patterns.
 */
public class SaleReceiptFormatter {

    /**
     * Formats a Sale object into a human-readable text receipt.
     *
     * @param sale the sale transaction to format
     * @return formatted receipt string
     */
    public String formatReceipt(Sale sale) {
        if (sale == null) {
            throw new IllegalArgumentException("Sale cannot be null.");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=========================================\n");
        sb.append("           GAMERZONE UNICESAR            \n");
        sb.append("             SALES RECEIPT               \n");
        sb.append("=========================================\n");
        sb.append("Sale ID:   ").append(sale.getSaleId()).append("\n");
        sb.append("Date:      ").append(sale.getDate()).append("\n");
        sb.append("Client:    ").append(sale.getClient()).append("\n");
        sb.append("Seller:    ").append(sale.getSeller()).append("\n");
        sb.append("-----------------------------------------\n");
        sb.append(String.format("%-20s %-5s %-10s\n", "Product", "Qty", "Subtotal"));
        sb.append("-----------------------------------------\n");

        if (sale.getItems() != null && !sale.getItems().isEmpty()) {
            for (SaleItem item : sale.getItems()) {
                sb.append(String.format("%-20s %-5d $%-10.2f\n",
                        item.getProduct().getTitle(),
                        item.getQuantity(),
                        item.getSubtotal()));
            }
        }

        sb.append("-----------------------------------------\n");
        if (sale.getDiscountAmount() > 0) {
            sb.append(String.format("Applied Promo: %s\n", sale.getAppliedPromotionName()));
            sb.append(String.format("Discount:     -$%.2f\n", sale.getDiscountAmount()));
            sb.append("-----------------------------------------\n");
        }
        sb.append(String.format("TOTAL AMOUNT:  $%.2f\n", sale.calculateTotal()));
        sb.append("=========================================\n");

        return sb.toString();
    }
}