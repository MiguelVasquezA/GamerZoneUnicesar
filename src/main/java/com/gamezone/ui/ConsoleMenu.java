package com.gamezone.ui;

import com.gamezone.exceptions.BusinessRuleException;
import com.gamezone.exceptions.GameZoneException;
import com.gamezone.exceptions.InvalidDataException;
import com.gamezone.exceptions.PersistenceException;
import com.gamezone.exceptions.ResourceNotFoundException;
import com.gamezone.model.Console;
import com.gamezone.model.Product;
import com.gamezone.model.Return;
import com.gamezone.model.Sale;
import com.gamezone.model.SaleItem;
import com.gamezone.model.VideoGame;
import com.gamezone.model.Warranty;
import com.gamezone.service.AccessoryService;
import com.gamezone.service.ProductService;
import com.gamezone.service.ReturnService;
import com.gamezone.service.SaleService;
import com.gamezone.service.WarrantyService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Interactive console interface for the GamerZone Unicesar system.
 * Handles user interactions and routes business logic execution with centralized exception handling.
 *
 * @author Miguel Vasquez
 * @version 2.4
 */
public class ConsoleMenu {

    private final SaleService saleService;
    private final ProductService productService;
    private final AccessoryService accessoryService;
    private final ReturnService returnService;
    private final WarrantyService warrantyService;
    private final Scanner scanner;

    public ConsoleMenu(SaleService saleService, ProductService productService, AccessoryService accessoryService, ReturnService returnService, WarrantyService warrantyService) {
        this.saleService = saleService;
        this.productService = productService;
        this.accessoryService = accessoryService;
        this.returnService = returnService;
        this.warrantyService = warrantyService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        int option;
        do {
            printHeader();
            printMenuOptions();
            option = readOption();

            try {
                handleOption(option);
            } catch (ResourceNotFoundException e) {
                System.out.println("\n [ERROR 404 - RECURSO NO ENCONTRADO] " + e.getMessage() + " (Código: " + e.getErrorCode() + ")");
            } catch (BusinessRuleException e) {
                System.out.println("\n [ERROR 400 - REGLA DE NEGOCIO] " + e.getMessage() + " (Código: " + e.getErrorCode() + ")");
            } catch (InvalidDataException e) {
                System.out.println("\n [ERROR 422 - DATO INVÁLIDO] " + e.getMessage() + " (Código: " + e.getErrorCode() + ")");
            } catch (PersistenceException e) {
                System.out.println("\n [ERROR 503 - ERROR DE PERSISTENCIA] " + e.getMessage() + " (Código: " + e.getErrorCode() + ")");
                if (e.getCause() != null) {
                    System.out.println("   Causa técnica: " + e.getCause().getMessage());
                } else {
                    e.printStackTrace();
                }
            } catch (GameZoneException e) {
                System.out.println("\n [ERROR DEL SISTEMA] " + e.getMessage() + " (Código: " + e.getErrorCode() + ")");
            } catch (Exception e) {
                System.out.println("\n [ERROR INESPERADO] Ocurrió una falla no controlada: " + e.getMessage());
                e.printStackTrace(); // <--- Esto nos dirá la línea exacta de la falla
            }

        } while (option != 0);
    }

    private void printHeader() {
        System.out.println("\n=========================================");
        System.out.println("           GAMERZONE UNICESAR            ");
        System.out.println("=========================================");
    }

    private void printMenuOptions() {
        System.out.println("1. Registrar Producto (Videojuego / Consola)");
        System.out.println("2. Listar Inventario de Productos");
        System.out.println("3. Registrar Venta");
        System.out.println("4. Listar Ventas");
        System.out.println("5. Registrar Devolución (con anulación de garantía)");
        System.out.println("6. Ver Devoluciones Registradas");
        System.out.println("7. Ver Garantías Activas");
        System.out.println("8. Generar Balance Financiero Mensual");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private int readOption() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void handleOption(int option) {
        switch (option) {
            case 1:
                registerProductMenu();
                break;
            case 2:
                listProductsMenu();
                break;
            case 3:
                registerSaleMenu();
                break;
            case 4:
                listSales();
                break;
            case 5:
                registerReturnMenu();
                break;
            case 6:
                viewReturnsMenu();
                break;
            case 7:
                viewWarrantiesMenu();
                break;
            case 8:
                generateMonthlyBalanceMenu();
                break;
            case 0:
                System.out.println("¡Gracias por usar GamerZone Unicesar!");
                break;
            default:
                System.out.println("Opción inválida. Intente de nuevo.");
        }
    }

    private void registerProductMenu() {
        System.out.println("\n--- REGISTRAR NUEVO PRODUCTO ---");
        System.out.println("1. Videojuego");
        System.out.println("2. Consola");
        System.out.print("Seleccione el tipo de producto: ");
        int type = parseIntegerInput(scanner.nextLine(), "Tipo de Producto");

        System.out.print("ID del Producto: ");
        String id = scanner.nextLine();

        System.out.print("Título / Nombre: ");
        String title = scanner.nextLine();

        System.out.print("Precio: ");
        double price = parseDoubleInput(scanner.nextLine(), "Precio");

        System.out.print("Stock (Cantidad): ");
        int stock = parseIntegerInput(scanner.nextLine(), "Stock");

        switch (type) {
            case 1:
                System.out.print("Plataforma (ej. PS5, Xbox, PC): ");
                String platform = scanner.nextLine();
                System.out.print("Género: ");
                String genre = scanner.nextLine();
                VideoGame game = new VideoGame(id, title, price, stock, platform, genre);
                productService.registerProduct(game);
                break;

            case 2:
                System.out.print("Marca / Fabricante: ");
                String brand = scanner.nextLine();
                System.out.print("Capacidad de Almacenamiento en GB (número entero): ");
                int storageGb = parseIntegerInput(scanner.nextLine(), "Capacidad GB");
                Console console = new Console(id, title, price, stock, brand, storageGb);
                productService.registerProduct(console);
                break;

            default:
                throw new InvalidDataException("Tipo", "Tipo de producto no válido.");
        }

        System.out.println("\n¡Producto registrado exitosamente en el inventario!");
    }

    private void listProductsMenu() {
        System.out.println("\n--- INVENTARIO DE PRODUCTOS ---");
        List<Product> products = productService.getAllProducts();
        if (products == null || products.isEmpty()) {
            System.out.println("No hay productos registrados en el inventario.");
        } else {
            for (Product p : products) {
                System.out.println("ID: " + p.getId() + " | Título: " + p.getTitle() + " | Precio: $" + String.format("%.2f", p.getPrice()) + " | Stock: " + p.getStock());
            }
        }
    }

    private void registerSaleMenu() {
        System.out.println("\n--- REGISTRAR NUEVA VENTA ---");
        System.out.print("Ingrese ID de la venta: ");
        String saleId = scanner.nextLine().trim();

        System.out.print("Ingrese Nombre/ID del Cliente: ");
        String client = scanner.nextLine().trim();

        System.out.print("Ingrese Nombre/ID del Vendedor: ");
        String seller = scanner.nextLine().trim();

        List<SaleItem> items = new ArrayList<>();
        System.out.println("Ingrese los productos a vender (escriba 'fin' en el ID para terminar):");

        while (true) {
            System.out.print("ID Producto: ");
            String pId = scanner.nextLine().trim();

            if ("fin".equalsIgnoreCase(pId)) {
                break;
            }
            if (pId.isEmpty()) {
                continue;
            }

            Product product = productService.getProductById(pId).orElse(null);
            if (product == null) {
                System.out.println("⚠️ Producto con ID " + pId + " no encontrado en el inventario.");
                continue;
            }

            System.out.print("Cantidad a comprar: ");
            String qtyInput = scanner.nextLine().trim();
            if (qtyInput.isEmpty()) {
                continue;
            }
            int qty = parseIntegerInput(qtyInput, "Cantidad");

            // Buscar si ya habíamos agregado este producto al carrito
            SaleItem existingItem = null;
            for (SaleItem item : items) {
                if (item.getProduct() != null && item.getProduct().getId().equals(product.getId())) {
                    existingItem = item;
                    break;
                }
            }

            int currentQtyInCart = (existingItem != null) ? existingItem.getQuantity() : 0;

            if ((currentQtyInCart + qty) > product.getStock()) {
                System.out.println("⚠️ Stock insuficiente. Disponible: " + product.getStock() + " (En carrito: " + currentQtyInCart + ")");
                continue;
            }

            if (existingItem != null) {
                // Al ser inmutable, removemos la instancia previa y creamos una con la cantidad acumulada
                items.remove(existingItem);
                items.add(new SaleItem(product, currentQtyInCart + qty));
            } else {
                items.add(new SaleItem(product, qty));
            }
        }

        if (items.isEmpty()) {
            throw new InvalidDataException("Productos", "Debe agregar al menos un producto a la venta.");
        }

        String currentDate = LocalDate.now().toString();
        Sale newSale = new Sale(saleId, client, items, 0.0, currentDate, seller);

        saleService.registerSale(newSale);

        // 2. Activar y guardar la garantía básica para cada producto
        if (warrantyService != null) {
            for (SaleItem item : items) {
                if (item.getProduct() != null) {
                    warrantyService.assignBasicWarranty(item.getProduct(), newSale, LocalDate.now());
                }
            }
        }

        System.out.println("\n¡Venta registrada y garantías activadas exitosamente!");
        System.out.println("ID Venta: " + saleId + " | Total: $" + String.format("%.2f", newSale.getTotalAmount()));
    }

    private void listSales() {
        System.out.println("\n--- LISTADO DE VENTAS ---");
        List<Sale> sales = saleService.getAllSales();
        if (sales == null || sales.isEmpty()) {
            System.out.println("No hay ventas registradas.");
        } else {
            sales.forEach(s -> System.out.println("ID Venta: " + s.getSaleId() + " | Fecha: " + s.getDate() + " | Total: $" + String.format("%.2f", s.getTotalAmount())));
        }
    }

    private void registerReturnMenu() {
        System.out.println("\n--- REGISTRAR DEVOLUCIÓN ---");
        System.out.print("Ingrese el ID de la venta original: ");
        String saleId = scanner.nextLine();

        List<String> productIds = new ArrayList<>();
        System.out.println("Ingrese los IDs de los productos a devolver (escriba 'fin' para terminar):");
        while (true) {
            System.out.print("ID de ítem: ");
            String pId = scanner.nextLine();
            if ("fin".equalsIgnoreCase(pId)) {
                break;
            }
            if (!pId.trim().isEmpty()) {
                productIds.add(pId);
            }
        }

        if (productIds.isEmpty()) {
            throw new InvalidDataException("Items", "Debe ingresar al menos un ítem para la devolución.");
        }

        System.out.print("Ingrese el motivo de la devolución: ");
        String reason = scanner.nextLine();

        Return newReturn = returnService.registerReturn(saleId, productIds, reason);
        System.out.println("\n¡Devolución registrada y garantía anulada exitosamente!");
        System.out.println(newReturn.generateReturnReceipt());
    }

    private void viewReturnsMenu() {
        System.out.println("\n--- LISTADO DE DEVOLUCIONES ---");
        List<Return> returns = returnService.viewAllReturns();
        if (returns == null || returns.isEmpty()) {
            System.out.println("No hay devoluciones registradas.");
        } else {
            for (Return r : returns) {
                String saleId = (r.getSale() != null) ? r.getSale().getSaleId() : "N/A";
                System.out.println("Devolución ID: " + r.getId() + " | Venta: " + saleId + " | Monto Reembolsado: $" + String.format("%.2f", r.getRefundAmount()) + " | Fecha: " + r.getReturnDate());
            }
        }
    }

    private void viewWarrantiesMenu() {
        System.out.println("\n--- LISTADO DE GARANTÍAS ---");
        List<Warranty> warranties = warrantyService.listAllWarranties();
        if (warranties == null || warranties.isEmpty()) {
            System.out.println("No hay garantías registradas o vigentes.");
        } else {
            for (Warranty w : warranties) {
                String saleId = (w.getSale() != null) ? w.getSale().getSaleId() : "N/A";
                System.out.println("Garantía ID: " + w.getId() + " | Venta Asociada: " + saleId);
            }
        }
    }

    private void generateMonthlyBalanceMenu() {
        System.out.println("\n--- REPORTE DE BALANCE MENSUAL ---");
        System.out.print("Ingrese el mes (1-12): ");
        int month = parseIntegerInput(scanner.nextLine(), "Mes");

        System.out.print("Ingrese el año (ej. 2026): ");
        int year = parseIntegerInput(scanner.nextLine(), "Año");

        double balance = returnService.generateMonthlyBalance(month, year);
        System.out.println("\nBalance Neto (Ventas - Devoluciones) para el período " + month + "/" + year + ": $" + String.format("%.2f", balance));
    }

    private int parseIntegerInput(String input, String fieldName) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new InvalidDataException(fieldName, "Debe ser un número entero válido.");
        }
    }

    private double parseDoubleInput(String input, String fieldName) {
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            throw new InvalidDataException(fieldName, "Debe ser un valor numérico válido.");
        }
    }
}