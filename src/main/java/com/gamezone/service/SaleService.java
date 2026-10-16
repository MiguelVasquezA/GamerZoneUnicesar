package com.gamezone.service;

import com.gamezone.model.Product;
import com.gamezone.model.Sale;
import com.gamezone.persistence.SaleRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Service responsible for managing sales transactions, inventory reduction,
 * and warranty assignments during sale registration.
 *
 * @author Desarrolladora 2
 * @version 1.8
 */
public class SaleService {

    private final SaleRepository saleRepository;
    private final ProductService productService;
    private final AccessoryService accessoryService;
    private final WarrantyService warrantyService;

    /**
     * Constructs a SaleService with its required repository and service dependencies.
     *
     * @param saleRepository repository to persist and load sales
     * @param productService service to manage product inventory
     * @param accessoryService service to manage accessory inventory
     * @param warrantyService service to assign basic or extended warranties
     */
    public SaleService(SaleRepository saleRepository, ProductService productService, AccessoryService accessoryService, WarrantyService warrantyService) {
        this.saleRepository = saleRepository;
        this.productService = productService;
        this.accessoryService = accessoryService;
        this.warrantyService = warrantyService;
    }

    /**
     * Retrieves all registered sales.
     *
     * @return a list of all sales
     */
    public List<Sale> listAllSales() {
        return saleRepository.loadSales();
    }

    /**
     * Registers a new sale, updates product/accessory stock, and assigns warranties.
     *
     * @param clientId the client ID making the purchase
     * @param products the list of products or accessories purchased
     * @param discountAmount any applicable discount amount
     * @param hasExtendedWarranty true if the customer opts for extended warranty
     * @return the created Sale object
     */
    public Sale registerSale(String clientId, List<Product> products, double discountAmount, boolean hasExtendedWarranty) {
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("La venta debe contener al menos un producto.");
        }

        // 1. Update inventory stock for each purchased item
        for (Product p : products) {
            if (accessoryService.findById(p.getId()) != null) {
                accessoryService.reduceStock(p.getId(), 1);
            } else {
                productService.reduceStock(p.getId(), 1);
            }
        }

        // 2. Generate unique sale ID and calculate financial amounts
        String saleId = "SALE-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        double subtotal = products.stream().mapToDouble(Product::getPrice).sum();
        double totalAmount = subtotal - discountAmount;

        // 3. Build Sale instance matching the updated 7-argument constructor
        Sale sale = new Sale(
                saleId,
                clientId,
                products,
                totalAmount,
                discountAmount,
                LocalDate.now().toString(),
                "System"
        );

        // 4. Assign warranties for each product in the sale
        for (Product item : products) {
            if (hasExtendedWarranty) {
                warrantyService.assignExtendedWarranty(item, sale, LocalDate.now());
            } else {
                warrantyService.assignBasicWarranty(item, sale, LocalDate.now());
            }
        }

        // 5. Persist the sale
        List<Sale> sales = saleRepository.loadSales();
        sales.add(sale);
        saleRepository.saveSale(sales);

        return sale;
    }
}