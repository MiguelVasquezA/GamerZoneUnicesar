package com.gamezone.model;

import java.time.LocalDate;

/**
 * Represents a product return transaction in the GameZone system.
 * Handles refund calculations based on return reason and generates return receipts.
 *
 * @author Miguel Vasquez
 * @version 1.0
 */
public class Return {
    private String id;
    private Sale sale;
    private Product product;
    private String reason;
    private LocalDate returnDate;
    private double refundAmount;

    /**
     * Constructs a Return instance and automatically calculates the refund amount.
     *
     * @param id         unique return transaction ID
     * @param sale       original sale reference
     * @param product    returned product
     * @param reason     reason for return
     * @param returnDate date of the return request
     */
    public Return(String id, Sale sale, Product product, String reason, LocalDate returnDate, double refundAmount) {
        this.id = id;
        this.sale = sale;
        this.product = product;
        this.reason = reason;
        this.returnDate = returnDate;
        this.refundAmount = refundAmount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }

    /**
     * Calculates the refund amount based on business rules:
     * - DEFECTIVE: Full refund (100% of product price)
     * - CHANGE_OF_MIND / OTHER: Partial refund (90% of product price due to restock fee)
     *
     * @return total refund amount
     */
    public double calculatedRefundAmount(){
        if (product == null){
            return 0.0;
        }

        if ("DEFECTIVE".equalsIgnoreCase(reason)){
            return product.getPrice();
        }else{
            return product.getPrice() * 0.90;
        }
    }
    /**
     * Generates a formatted text receipt for the return transaction.
     *
     * @return formatted string containing return details
     */
    public String generateReturnReceipt(){
        return String.format(
        "=== RETURN RECEIPT ===\n"+
                "Return ID: %s\n" +
                "Original Sale ID: %s\n" +
                "Porduct: %s (ID: %s)\n"+
                "Reason: %s\n" +
                "Date: %s\n"+
                "Refund Amount: %.2f\n"+
                "=====================",
                id,
                (sale != null ? sale.getSaleId() : "N/A"),
                (product != null ? product.getTitle() : "N/A"),
                (product != null ? product.getId() : "N/A"),
                reason,
                returnDate,
                refundAmount
        );
    }
}
