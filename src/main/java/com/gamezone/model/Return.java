package com.gamezone.model;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents a product return transaction within the GameZone system.
 * Manages the returned products from a sale, calculates refund amounts,
 * and generates return receipts.
 *
 * @author Miguel Vasquez
 * @version 1.0
 */
public class Return {

    private String id;
    private LocalDate returnDate;
    private Sale sale;
    private List<Product> returnedProducts;
    private String reason;
    private double refundAmount;

    /**
     * Constructs a Return instance and automatically calculates the total refund amount.
     *
     * @param id               unique identifier for the return
     * @param returnDate       date when the return was requested
     * @param sale             associated original sale transaction
     * @param returnedProducts list of products being returned
     * @param reason           reason or justification for the return
     */
    public Return(String id, LocalDate returnDate, Sale sale, List<Product> returnedProducts, String reason) {
        this.id = id;
        this.returnDate = returnDate;
        this.sale = sale;
        this.returnedProducts = returnedProducts;
        this.reason = reason;
        this.refundAmount = calculateRefundAmount();
    }

    /**
     * Gets the return identifier.
     *
     * @return return ID
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the date when the return was registered.
     *
     * @return return date
     */
    public LocalDate getReturnDate() {
        return returnDate;
    }

    /**
     * Gets the associated original sale.
     *
     * @return original sale
     */
    public Sale getSale() {
        return sale;
    }

    /**
     * Gets the list of products included in this return.
     *
     * @return list of returned products
     */
    public List<Product> getReturnedProducts() {
        return returnedProducts;
    }

    /**
     * Gets the reason for the return.
     *
     * @return return reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * Gets the total calculated refund amount.
     *
     * @return total refund amount
     */
    public double getRefundAmount() {
        return refundAmount;
    }

    /**
     * Calculates the total refund amount by summing the prices of all returned products.
     *
     * @return total calculated refund amount
     */
    public double calculateRefundAmount() {
        if (returnedProducts == null || returnedProducts.isEmpty()) {
            return 0.0;
        }
        double total = 0.0;
        for (Product product : returnedProducts) {
            if (product != null) {
                total += product.getPrice();
            }
        }
        return total;
    }

    /**
     * Generates a formatted text receipt containing full details of the return transaction in Spanish.
     *
     * @return formatted return receipt string
     */
    public String generateReturnReceipt() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== COMPROBANTE DE DEVOLUCIÓN ===\n");
        sb.append("ID Devolución: ").append(id).append("\n");
        sb.append("Fecha: ").append(returnDate).append("\n");
        sb.append("ID Venta Original: ").append(sale != null ? sale.getSaleId() : "N/A").append("\n");
        sb.append("Motivo: ").append(reason).append("\n");
        sb.append("Productos Devueltos:\n");
        if (returnedProducts != null) {
            for (Product p : returnedProducts) {
                sb.append(" - ").append(p.getTitle()).append(" ($").append(p.getPrice()).append(")\n");
            }
        }
        sb.append(String.format("Monto Reembolsado Total: $%.2f\n", refundAmount));
        sb.append("=================================");
        return sb.toString();
    }
}