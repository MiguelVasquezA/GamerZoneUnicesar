package com.gamezone.ui;

import com.gamezone.persistence.PersonFileHandler;
import com.gamezone.persistence.ProductRepository;
import com.gamezone.persistence.SaleRepository;
import com.gamezone.service.PersonService;
import com.gamezone.service.ProductService;
import com.gamezone.service.SaleService;

    /**
     * Main execution class for the GamerZone application.
     * Responsible for initializing system repositories, services, and the UI layer.
     *
     * @author Miguel Vasquez
     * @version 1.0
     */
    public class Main {

        /**
         * Application main entry point.
         *
         * @param args command-line arguments (unused)
         */
        public static void main(String[] args) {

            ProductRepository productRepository = new ProductRepository("product.txt");
            PersonFileHandler personFileHandler = new PersonFileHandler("persons.txt");
            SaleRepository saleRepository = new SaleRepository("sales.txt");


            ProductService productService = new ProductService(productRepository);
            PersonService personService = new PersonService(personFileHandler);
            SaleService saleService = new SaleService(saleRepository);


            ConsoleMenu consoleMenu = new ConsoleMenu(productService, personService, saleService);
            consoleMenu.start();
        }
    }

