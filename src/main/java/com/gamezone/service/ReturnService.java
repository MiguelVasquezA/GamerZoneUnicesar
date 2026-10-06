package com.gamezone.service;

import com.gamezone.model.Product;
import com.gamezone.model.Return;
import com.gamezone.model.Sale;
import com.gamezone.persistence.ReturnRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service responsible for managing return business logic, including validations,
 * inventory restoration (both products and accessories), monthly balance generation,
 * and warranty cancellation integration (Task A7).
 *
 * @author Desarrolladora 2
 * @version 1.6
 */
public class ReturnService {

    private final ReturnRepository returnRepository;
    private final SaleService saleService;
    private final ProductService productService;
    private final AccessoryService accessoryService;
    private final WarrantyService warrantyService; // Integrado para la tarea A7

    /**
     * Constructs a new ReturnService with its required repository and service dependencies.
     *
     * @param returnRepository the repository to persist and load returns
     * @param saleService the service to handle and verify sales
     * @param productService the service to manage product inventory
     * @param accessoryService the service to manage accessory inventory
     * @param warrantyService the service to manage and cancel warranties
     */
    public ReturnService(ReturnRepository returnRepository, SaleService saleService, ProductService productService, AccessoryService accessoryService, WarrantyService warrantyService) {
        this.returnRepository = returnRepository;
        this.saleService = saleService;
        this.productService = productService;
        this.accessoryService = accessoryService;
        this.warrantyService = warrantyService;
    }

    /**
     * Registers a new product or accessory return associated with a sale after validating business rules.
     *
     * @param saleId the ID of the original sale
     * @param productIds the list of product/accessory IDs being returned
     * @param reason the reason for the return
     * @return the newly created and persisted Return object
     * @throws IllegalArgumentException if the sale does not exist, the return period has expired, or items do not belong to the sale
     */
    public Return registerReturn(String saleId, List<String> productIds, String reason) {
        // 1. Find the sale using listAllSales() provided by SaleService
        Sale sale = saleService.listAllSales().stream()
                .filter(s -> s.getSaleId().equals(saleId))
                .findFirst()
                .orElse(null);

        if (sale == null) {
            throw new IllegalArgumentException("La venta original con ID " + saleId + " no existe.");
        }

        // 2. Validate return time window (30 days limit)
        if (!sale.canBeReturned()) {
            throw new IllegalArgumentException("El plazo de 30 días para realizar devoluciones de esta venta ha expirado.");
        }

        // 3. Validate product/accessory ownership
        List<Product> productsToReturn = new ArrayList<>();
        for (String productId : productIds) {
            Product product = sale.getProducts().stream()
                    .filter(p -> p.getId().equals(productId))
                    .findFirst()
                    .orElse(null);

            if (product == null) {
                throw new IllegalArgumentException("El ítem con ID " + productId + " no pertenece a la venta original.");
            }
            productsToReturn.add(product);
        }

        // 4. Restore inventory stock (delegating to AccessoryService if it's an accessory, otherwise to ProductService)
        for (Product product : productsToReturn) {
            if (accessoryService.findById(product.getId()) != null) {
                accessoryService.restoreStock(product.getId(), 1);
            } else {
                productService.updateStock(product.getId(), product.getStock() + 1);
            }
        }

        // 4.1. Cancel associated warranty if it exists (Task A7 - Anulación de Garantía)
        if (warrantyService != null) {
            warrantyService.cancelWarrantyBySale(saleId);
        }

        // 5. Create the return instance using the exact constructor parameters of Return model
        String returnId = "RET-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        Return newReturn = new Return(returnId, sale, productsToReturn, reason, LocalDate.now());

        // 6. Persist the return
        List<Return> allReturns = returnRepository.loadAll();
        allReturns.add(newReturn);
        returnRepository.saveAll(allReturns);

        return newReturn;
    }

    /**
     * Retrieves all registered returns.
     *
     * @return a list of all returns
     */
    public List<Return> viewAllReturns() {
        return returnRepository.loadAll();
    }

    /**
     * Filters and returns all returns associated with a specific customer ID.
     *
     * @param customerId the customer ID to filter by
     * @return a list of returns belonging to the customer
     */
    public List<Return> viewReturnsByCustomer(String customerId) {
        return returnRepository.loadAll().stream()
                .filter(r -> r.getSale().getClient().equals(customerId))
                .collect(Collectors.toList());
    }

    /**
     * Filters and returns all returns associated with a specific sale ID.
     *
     * @param saleId the sale ID to filter by
     * @return a list of returns associated with the sale
     */
    public List<Return> viewReturnsBySale(String saleId) {
        return returnRepository.loadAll().stream()
                .filter(r -> r.getSale().getSaleId().equals(saleId))
                .collect(Collectors.toList());
    }

    /**
     * Generates a monthly financial balance by subtracting total returns from total sales for a given month and year.
     *
     * @param month the target month (1-12)
     * @param year the target year
     * @return the net financial balance for the period
     */
    public double generateMonthlyBalance(int month, int year) {
        double totalSales = saleService.listAllSales().stream()
                .filter(s -> {
                    LocalDate date = LocalDate.parse(s.getDate());
                    return date.getMonthValue() == month && date.getYear() == year;
                })
                .mapToDouble(Sale::getTotalAmount)
                .sum();

        double totalReturns = returnRepository.loadAll().stream()
                .filter(r -> r.getReturnDate().getMonthValue() == month && r.getReturnDate().getYear() == year)
                .mapToDouble(Return::getRefundAmount)
                .sum();

        return totalSales - totalReturns;
    }
}