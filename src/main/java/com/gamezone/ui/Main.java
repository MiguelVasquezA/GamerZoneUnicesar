package com.gamezone.ui;

import com.gamezone.persistence.AccessoryRepository;
import com.gamezone.persistence.PersonFileHandler;
import com.gamezone.persistence.ProductRepository;
import com.gamezone.persistence.PromotionRepository;
import com.gamezone.persistence.ReturnRepository;
import com.gamezone.persistence.SaleRepository;
import com.gamezone.persistence.WarrantyRepository;
import com.gamezone.service.AccessoryService;
import com.gamezone.service.PersonService;
import com.gamezone.service.ProductService;
import com.gamezone.service.PromotionService;
import com.gamezone.service.ReturnService;
import com.gamezone.service.SaleService;
import com.gamezone.service.WarrantyService;

import java.util.Scanner;

/**
 * Main execution class for the GamerZone application.
 * Responsible for initializing system repositories, services, and the UI layer.
 *
 * @author Miguel Vasquez
 * @version 2.1
 */
public class Main {

    /**
     * Application main entry point.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1. Repositories
        ProductRepository productRepository = new ProductRepository("product.txt");
        AccessoryRepository accessoryRepository = new AccessoryRepository();
        PersonFileHandler personFileHandler = new PersonFileHandler("persons.txt");
        SaleRepository saleRepository = new SaleRepository("sales.txt");
        PromotionRepository promotionRepository = new PromotionRepository();
        WarrantyRepository warrantyRepository = new WarrantyRepository();

        // 2. Base Services
        ProductService productService = new ProductService(productRepository);
        AccessoryService accessoryService = new AccessoryService(accessoryRepository);
        PersonService personService = new PersonService(personFileHandler);
        PromotionService promotionService = new PromotionService(promotionRepository);

        // 3. WarrantyService initialization
        WarrantyService warrantyService = new WarrantyService(warrantyRepository, productService, saleRepository);

        // 4. SaleService includes WarrantyService dependency
        SaleService saleService = new SaleService(productService, accessoryService, saleRepository, promotionService, warrantyService);

        // 5. ReturnRepository and ReturnService initialization (Ahora pasamos accessoryService al ReturnService para el Ajuste A4)
        ReturnRepository returnRepository = new ReturnRepository(saleService, productService);
        ReturnService returnService = new ReturnService(returnRepository, saleService, productService, accessoryService);

        // 6. ConsoleMenu includes all required services and scanner
        ConsoleMenu consoleMenu = new ConsoleMenu(
                productService,
                accessoryService,
                personService,
                saleService,
                scanner,
                promotionService,
                returnService,
                warrantyService
        );

        consoleMenu.start();
    }
}
