package com.gamezone.service;

import com.gamezone.model.Accessory;
import com.gamezone.model.Console;
import com.gamezone.model.Product;
import com.gamezone.model.Promotion;
import com.gamezone.model.Sale;
import com.gamezone.persistence.SaleRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Service class responsible for unified sale transactions processing,
 * stock validations, promotion evaluation, and warranty issuance.
 *
 * @author Miguel Vasquez
 * @version 1.3
 */
public class SaleService {
    private final ProductService productService;
    private final AccessoryService accessoryService;
    private final SaleRepository saleRepository;
    private final PromotionService promotionService;
    private final WarrantyService warrantyService;

    /**
     * Constructs a SaleService with required dependencies.
     *
     * @param productService    service for product management
     * @param accessoryService  service for accessory management
     * @param saleRepository     repository for sale persistence
     * @param promotionService   service for evaluating promotions
     * @param warrantyService    service for managing warranties
     */
    public SaleService(ProductService productService, AccessoryService accessoryService,
                       SaleRepository saleRepository, PromotionService promotionService,
                       WarrantyService warrantyService) {
        this.productService = productService;
        this.accessoryService = accessoryService;
        this.saleRepository = saleRepository;
        this.promotionService = promotionService;
        this.warrantyService = warrantyService;
    }

    /**
     * Registers a new sale transaction following the unified flow sequence.
     *
     * @param client                client ID or name
     * @param seller                seller ID or name
     * @param products              list of items to purchase
     * @param wantsExtendedWarranty flag for optional extended warranty on consoles
     * @return registered {@link Sale} instance
     */
    public Sale registerSale(String client, String seller, List<Product> products, boolean wantsExtendedWarranty) {

        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("Sale must contain at least one item.");
        }

        for (Product item : products) {
            if (item.getStock() <= 0) {
                throw new IllegalStateException("Item '" + item.getTitle() + "' is out of stock.");
            }
        }

        String saleId = "SALE-" + System.currentTimeMillis();
        String date = LocalDate.now().toString();

        double subtotal = 0.0;
        for (Product item : products) {
            subtotal += item.getPrice();
        }

        Sale sale = new Sale(client, date, 0.0, products, saleId, seller, subtotal);

        double discountAmount = 0.0;
        if (promotionService != null) {
            Promotion bestPromotion = promotionService.findBestPromotionFor(sale);
            if (bestPromotion != null) {
                discountAmount = bestPromotion.calculateDiscount(sale);
                sale.setAppliedPromotionName(bestPromotion.getName());
                sale.setDiscountAmount(discountAmount);
            }
        }

        double warrantyCost = 0.0;
        if (warrantyService != null) {
            for (Product item : products) {
                if (item instanceof Console) {
                    if (wantsExtendedWarranty) {
                        var extended = warrantyService.assignExtendedWarranty(item, sale, LocalDate.now());
                        warrantyCost += extended.getAdditionalCost();
                    } else {
                        warrantyService.assignBasicWarranty(item, sale, LocalDate.now());
                    }
                }
            }
        }

        double finalTotal = subtotal - discountAmount + warrantyCost;
        sale.setTotalAmount(finalTotal);


        for (Product item : products) {
            if (item instanceof Accessory) {
                accessoryService.updateStock(item.getId(), item.getStock() - 1);
            } else {
                productService.updateStock(item.getId(), item.getStock() - 1);
            }
        }

        List<Sale> sales = saleRepository.loadSales();
        sales.add(sale);
        saleRepository.saveSale(sales);

        return sale;
    }

    /**
     * Retrieves all recorded sales.
     *
     * @return list of sales
     */
    public List<Sale> listAllSales() {
        return saleRepository.loadSales();
    }
}
