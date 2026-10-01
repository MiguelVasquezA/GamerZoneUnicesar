package com.gamezone.ui;

import com.gamezone.persistence.AccessoryRepository;
import com.gamezone.persistence.PersonFileHandler;
import com.gamezone.persistence.ProductRepository;
import com.gamezone.persistence.PromotionRepository;
import com.gamezone.persistence.SaleRepository;
import com.gamezone.service.AccessoryService;
import com.gamezone.service.PersonService;
import com.gamezone.service.ProductService;
import com.gamezone.service.PromotionService;
import com.gamezone.service.SaleService;

/**
 * Main execution class for the GamerZone application.
 * Responsible for initializing system repositories, services, and the UI layer.
 *
 * @author Miguel Vasquez
 * @version 1.1
 */
public class Main {

    /**
     * Application main entry point.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {

        // 1. Repositories
        ProductRepository productRepository = new ProductRepository("product.txt");
        AccessoryRepository accessoryRepository = new AccessoryRepository();
        PersonFileHandler personFileHandler = new PersonFileHandler("persons.txt");
        SaleRepository saleRepository = new SaleRepository("sales.txt");
        PromotionRepository promotionRepository = new PromotionRepository();

        // 2. Services
        ProductService productService = new ProductService(productRepository);
        AccessoryService accessoryService = new AccessoryService(accessoryRepository);
        PersonService personService = new PersonService(personFileHandler);
        PromotionService promotionService = new PromotionService(promotionRepository);

        // SaleService includes PromotionService dependency
        SaleService saleService = new SaleService(productService, accessoryService, saleRepository, promotionService);

        // ConsoleMenu includes PromotionService dependency
        ConsoleMenu consoleMenu = new ConsoleMenu(productService, accessoryService, personService, saleService, promotionService);
        consoleMenu.start();
    }
}