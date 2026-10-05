package com.gamezone.model;

import java.time.LocalDate;
import java.util.List;

/**
 * Class representing a product return transaction.
 * Calculates proportional refund amounts when discounts were applied to the original sale.
 *
 * @author Miguel Vasquez
 * @version 1.2
 */
public class Return {
    private String id;
    private Sale sale;
    private List<Product> returnedItems;
    private String reason;
    private LocalDate returnDate;
    private double refundAmount;

    /**
     * Constructs a Return instance and calculates the proportional refund amount.
     *
     * @param id            unique return identifier
     * @param sale          original sale transaction reference
     * @param returnedItems list of products being returned
     * @param reason        return justification
     * @param returnDate    date of the return
     */
    public Return(String id, Sale sale, List<Product> returnedItems, String reason, LocalDate returnDate) {
        this.id = id;
        this.sale = sale;
        this.returnedItems = returnedItems;
        this.reason = reason;
        this.returnDate = returnDate;
        this.refundAmount = calculateRefundAmount();
    }

    public String getId() {
        return id;
    }

    public Sale getSale() {
        return sale;
    }

    public List<Product> getReturnedItems() {
        return returnedItems;
    }

    public String getReason() {
        return reason;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    /**
     * Calculates the total refund amount proportionally applying original sale discounts.
     * Formula per item: price - (price * totalDiscount / itemsSubtotal)
     *
     * @return total proportional refund amount
     */
    public double calculateRefundAmount() {
        if (returnedItems == null || returnedItems.isEmpty() || sale == null) {
            return 0.0;
        }

        // Calculate original items subtotal
        double saleSubtotal = 0.0;
        if (sale.getProducts() != null) {
            for (Product p : sale.getProducts()) {
                saleSubtotal += p.getPrice();
            }
        }

        if (saleSubtotal == 0.0) {
            return 0.0;
        }

        double totalDiscount = sale.getDiscountAmount();
        double totalRefund = 0.0;

        for (Product item : returnedItems) {
            double listPrice = item.getPrice();
            double itemDiscount = listPrice * (totalDiscount / saleSubtotal);
            double itemRefund = listPrice - itemDiscount;
            totalRefund += itemRefund;
        }

        return totalRefund;
    }

    /**
     * Generates a formatted return receipt voucher displaying list prices,
     * proportional discounts, and final refunded amounts.
     *
     * @return formatted return receipt text
     */
    public String generateReturnReceipt() {
        StringBuilder sb = new StringBuilder();
        sb.append("=========================================\n");
        sb.append("           GAMERZONE UNICESAR            \n");
        sb.append("             RETURN VOUCHER              \n");
        sb.append("=========================================\n");
        sb.append("Return ID: ").append(id).append("\n");
        sb.append("Date: ").append(returnDate).append("\n");
        sb.append("Original Sale ID: ").append(sale != null ? sale.getSaleId() : "N/A").append("\n");
        sb.append("Reason: ").append(reason).append("\n");
        sb.append("-----------------------------------------\n");
        sb.append("RETURNED ITEMS:\n");

        double saleSubtotal = 0.0;
        if (sale != null && sale.getProducts() != null) {
            for (Product p : sale.getProducts()) {
                saleSubtotal += p.getPrice();
            }
        }

        double totalDiscount = (sale != null) ? sale.getDiscountAmount() : 0.0;

        if (returnedItems != null) {
            for (Product item : returnedItems) {
                double listPrice = item.getPrice();
                double itemDiscount = (saleSubtotal > 0) ? listPrice * (totalDiscount / saleSubtotal) : 0.0;
                double itemRefund = listPrice - itemDiscount;

                sb.append(" - ").append(item.getTitle()).append("\n")
                        .append("   List Price: $").append(String.format("%.2f", listPrice))
                        .append(" | Discount: -$").append(String.format("%.2f", itemDiscount))
                        .append(" | Refunded: $").append(String.format("%.2f", itemRefund)).append("\n");
            }
        }

        sb.append("-----------------------------------------\n");
        sb.append("TOTAL REFUNDED: $").append(String.format("%.2f", refundAmount)).append("\n");
        sb.append("=========================================\n");

        return sb.toString();
    }
}