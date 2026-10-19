package com.gamezone.ui;

import com.gamezone.persistence.*;
import com.gamezone.service.*;

public class Main {
    public static void main(String[] args) {
        // 1. Repositorios de Persistencia
        ISaleRepository saleRepository = new SaleRepository();
        IProductRepository productRepository = new ProductRepository();
        PersonFileHandler personFileHandler = new PersonFileHandler();
        ReturnRepository returnRepository = new ReturnRepository();
        WarrantyRepository warrantyRepository = new WarrantyRepository();

        // 2. Servicios de la Aplicación
        SaleService saleService = new SaleService(saleRepository, productRepository);
        ProductService productService = new ProductService((ProductRepository) productRepository);
        AccessoryService accessoryService = new AccessoryService();
        ReturnService returnService = new ReturnService(returnRepository);
        WarrantyService warrantyService = new WarrantyService(warrantyRepository);

        // 3. Inicialización del Menú Principal
        ConsoleMenu menu = new ConsoleMenu(
                saleService,
                productService,
                accessoryService,
                returnService,
                warrantyService
        );

        // 4. Iniciar ejecución
        menu.start();
    }
}
