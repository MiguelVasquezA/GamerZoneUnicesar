package com.gamezone.service;

import com.gamezone.model.Product;
import com.gamezone.model.Sale;
import com.gamezone.persistence.SaleRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class responsible for handling business logic related to sales,
 * including stock validation, total calculation, and sale persistence.
 *
 * @author Miguel Vasquez
 * @version 1.0
 */
public class SaleService {

    private final SaleRepository saleRepository;
    private final List<Sale> sales;

    /**
     * Constructs a SaleService with a specified repository.
     *
     * @param saleRepository the repository used for persisting sales
     */
    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
        this.sales = saleRepository.loadSales();
    }

    /**
     * Registers a new sale after validating business constraints.
     *
     * @param sale the Sale object to be registered
     * @return true if the sale was successfully registered, false otherwise
     */
    public boolean registerSale(Sale sale) {
        if (sale == null || sale.getProducts() == null || sale.getProducts().isEmpty()) {
            System.err.println("Error: A sale must contain at least one product.");
            return false;
        }

        // Validate stock for all products in the sale
        for (Product product : sale.getProducts()) {
            if (product.getStock() <= 0) {
                System.err.println("Error: Insufficient stock for product: " + product.getTitle());
                return false;
            }
        }

        // Deduct stock for each product
        for (Product product : sale.getProducts()) {
            product.setStock(product.getStock() - 1);
        }

        // Calculate total and save sale
        sale.calculateTotal();
        sales.add(sale);
        saleRepository.saveSale(sales);
        return true;
    }

    /**
     * Retrieves the entire history of registered sales.
     *
     * @return a list of all sales
     */
    public List<Sale> getAllSales() {
        return new ArrayList<>(sales);
    }

    /**
     * Retrieves sales associated with a specific seller ID.
     *
     * @param sellerId the identification of the seller
     * @return a list of sales handled by the specified seller
     */
    public List<Sale> getSalesBySeller(String sellerId) {
        List<Sale> sellerSales = new ArrayList<>();
        for (Sale sale : sales) {
            if (sale.getSeller() != null && sale.getSeller().getId().equalsIgnoreCase(sellerId)) {
                sellerSales.add(sale);
            }
        }
        return sellerSales;
    }

    /**
     * Retrieves sales associated with a specific customer ID.
     *
     * @param customerId the identification of the customer
     * @return a list of sales made by the specified customer
     */
    public List<Sale> getSalesByCustomer(String customerId) {
        List<Sale> customerSales = new ArrayList<>();
        for (Sale sale : sales) {
            if (sale.getCustomer() != null && sale.getCustomer().getId().equalsIgnoreCase(customerId)) {
                customerSales.add(sale);
            }
        }
        return customerSales;
    }
}