package com.gamezone.service;

import com.gamezone.model.BasicWarranty;
import com.gamezone.model.ExtendedWarranty;
import com.gamezone.model.Product;
import com.gamezone.model.Sale;
import com.gamezone.model.Warranty;
import com.gamezone.persistence.SaleRepository;
import com.gamezone.persistence.WarrantyRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service class responsible for managing warranty business logic,
 * including creation, persistence coordination, and date-based filtering.
 * Uses SaleRepository directly to prevent circular dependency cycles with SaleService.
 *
 * @author GameZone Team
 * @version 2.0
 */
public class WarrantyService {
    private final WarrantyRepository warrantyRepository;
    private final ProductService productService;
    private final SaleRepository saleRepository;
    private List<Warranty> warranties;

    /**
     * Constructs a new WarrantyService with its required clean dependencies and loads existing records.
     *
     * @param warrantyRepository repository for warranty data persistence
     * @param productService service to manage and retrieve products
     * @param saleRepository repository to manage and retrieve sales directly
     */
    public WarrantyService(WarrantyRepository warrantyRepository, ProductService productService, SaleRepository saleRepository) {
        this.warrantyRepository = warrantyRepository;
        this.productService = productService;
        this.saleRepository = saleRepository;
        this.warranties = new ArrayList<>();
        loadData();
    }

    /**
     * Loads all warranties from the repository using current product and sale database references.
     */
    public void loadData() {
        this.warranties = warrantyRepository.loadAll(productService, saleRepository);
        if (this.warranties == null) {
            this.warranties = new ArrayList<>();
        }
    }

    /**
     * Persists the current list of warranties to storage.
     */
    public void saveData() {
        warrantyRepository.saveAll(this.warranties);
    }

    /**
     * Assigns and persists a basic factory warranty to a product purchased in a sale.
     *
     * @param product the product covered by the warranty
     * @param sale the corresponding sale transaction
     * @param startDate the start date of the warranty
     * @return the created BasicWarranty instance
     */
    public BasicWarranty assignBasicWarranty(Product product, Sale sale, LocalDate startDate) {
        String id = "WAR-B-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        BasicWarranty warranty = new BasicWarranty(id, product, sale, startDate);
        warranties.add(warranty);
        saveData();
        return warranty;
    }

    /**
     * Assigns and persists an extended warranty to a product purchased in a sale.
     *
     * @param product the product covered by the warranty
     * @param sale the corresponding sale transaction
     * @param startDate the start date of the warranty
     * @return the created ExtendedWarranty instance
     */
    public ExtendedWarranty assignExtendedWarranty(Product product, Sale sale, LocalDate startDate) {
        String id = "WAR-E-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        ExtendedWarranty warranty = new ExtendedWarranty(id, product, sale, startDate);
        warranties.add(warranty);
        saveData();
        return warranty;
    }

    /**
     * Finds a specific warranty associated with a given product ID and sale ID.
     *
     * @param productId the unique identifier of the product
     * @param saleId the unique identifier of the sale
     * @return the matching Warranty, or null if not found
     */
    public Warranty findWarrantyByProduct(String productId, String saleId) {
        return warranties.stream()
                .filter(w -> w.getProduct() != null && w.getProduct().getId().equals(productId) &&
                        w.getSale() != null && w.getSale().getSaleId().equals(saleId))
                .findFirst()
                .orElse(null);
    }

    /**
     * Returns a list of all registered warranties.
     *
     * @return list of all warranties
     */
    public List<Warranty> listAllWarranties() {
        return new ArrayList<>(warranties);
    }

    /**
     * Filters and returns only the warranties that are currently active as of today.
     *
     * @return list of active warranties
     */
    public List<Warranty> listActiveWarranties() {
        LocalDate today = LocalDate.now();
        return warranties.stream()
                .filter(w -> w.isActive(today))
                .collect(Collectors.toList());
    }

    /**
     * Filters and returns warranties whose expiration date is within the next specified number of days.
     *
     * @param daysAhead the number of days ahead to check for expiration
     * @return list of warranties expiring soon
     */
    public List<Warranty> listWarrantiesExpiringSoon(int daysAhead) {
        LocalDate today = LocalDate.now();
        LocalDate limitDate = today.plusDays(daysAhead);
        return warranties.stream()
                .filter(w -> w.getEndDate() != null &&
                        (w.getEndDate().isEqual(today) || w.getEndDate().isAfter(today)) &&
                        (w.getEndDate().isEqual(limitDate) || w.getEndDate().isBefore(limitDate)))
                .collect(Collectors.toList());
    }
}