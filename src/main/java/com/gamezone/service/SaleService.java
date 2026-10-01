package com.gamezone.service;

import  com.gamezone.model.Accessory;
import com.gamezone.model.Product;
import com.gamezone.model.Promotion;
import com.gamezone.model.Sale;
import com.gamezone.persistence.SaleRepository;

import java.util.List;

/**
 * Service class responsible for managing sale transactions, applying best promotions,
 * and updating inventory.
 *
 * @author Miguel Vasquez
 * @version 1.1
 */

public class SaleService{
    private final ProductService productService;
    private final AccessoryService accessoryService;
    private final SaleRepository saleRepository;
    private final PromotionService promotionService;

    /**
     * Constructs a new SaleService with necessary dependencies.
     *
     * @param accessoryService service for managing accessory inventory.
     * @param productService service for managing general product inventory.
     * @param saleRepository repository for persisting sale records.
     * @param promotionService service for calculating and applying best available promotions.
     */
    public SaleService(ProductService productService, AccessoryService accessoryService, SaleRepository saleRepository, PromotionService promotionService) {
        this.productService = productService;
        this.accessoryService = accessoryService;
        this.saleRepository = saleRepository;
        this.promotionService = promotionService;
    }

    /**
     * Registers a new sale transaction, calculates the best applicable promotion,
     * validates stock availability, and updates inventory.
     *
     * @param client the client who makes the purchase.
     * @param seller the vendor who processes the sale.
     * @param products the list of products or accessories being purchased.
     * @return the processed {@link Sale} object with discounts applied if available.
     * @throws IllegalArgumentException if the items list is null or empty.
     * @throws IllegalStateException if any item in the sale does not have enough stock.
     */
    public Sale registerSale(String client, String seller, List<Product> products) {
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("Sale must contain at least one product.");
        }


        for (Product item : products) {
            if (item.getStock() <= 0) {
                throw new IllegalStateException("Item '" + item.getTitle() + "' is out of stock.");
            }
        }

        String saleId = "SALE-" + System.currentTimeMillis();
        String date = java.time.LocalDate.now().toString();


        double totalAmount = 0.0;
        for (Product item : products) {
            totalAmount += item.getPrice();
        }


        Sale sale = new Sale(client, date, 0.0, products, saleId, seller, totalAmount);

        if(promotionService != null){
            Promotion bestPromotion = promotionService.findBestPromotionFor(sale);
            if(bestPromotion != null){
                double discount = bestPromotion.calculateDiscount(sale);
                sale.setAppliedPromotionName(bestPromotion.getName());
                sale.setDiscountAmount(discount);
                sale.setTotalAmount(totalAmount - discount);
            }
        }

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
     * Retrieves all recorded sales from storage.
     *
     * @return a list of all {@link Sale} instances
     */
    public List<Sale> listAllSales(){
        return saleRepository.loadSales();
    }

}
