package com.gamezone.ui;

import com.gamezone.model.Product;
import com.gamezone.model.Return;
import com.gamezone.model.Sale;
import com.gamezone.service.*;

import java.util.List;
import java.util.Scanner;

/**
 * Handles the console user interface and navigation menu for the GameZone system.
 */
public class ConsoleMenu {

    private final ProductService productService;
    private final AccessoryService accessoryService;
    private final PersonService personService;
    private final SaleService saleService;
    private final Scanner scanner;
    private final PromotionService promotionsService;
    private final ReturnService returnService;
    private final WarrantyService warrantyService;

    /**
     * Constructs a new ConsoleMenu with all required application services.
     */
    public ConsoleMenu(ProductService productService,
                       AccessoryService accessoryService,
                       PersonService personService,
                       SaleService saleService,
                       Scanner scanner,
                       PromotionService promotionsService,
                       ReturnService returnService,
                       WarrantyService warrantyService) {
        this.productService = productService;
        this.accessoryService = accessoryService;
        this.personService = personService;
        this.saleService = saleService;
        this.scanner = scanner;
        this.promotionsService = promotionsService;
        this.returnService = returnService;
        this.warrantyService = warrantyService;
    }

    /**
     * Starts the main console menu loop.
     */
    public void start() {
        boolean exit = false;
        while (!exit) {
            printMainMenu();
            int choice = readInt("Select an option: ");
            switch (choice) {
                case 1 -> manageSalesMenu();
                case 2 -> manageReturnsMenu();
                case 3 -> {
                    System.out.println("Exiting application. Goodbye!");
                    exit = true;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void printMainMenu() {
        System.out.println("\n=== GAMEZONE MAIN MENU ===");
        System.out.println("1. Manage Sales");
        System.out.println("2. Manage Returns");
        System.out.println("3. Exit");
    }

    private void manageSalesMenu() {
        System.out.println("\n--- Sales Management ---");
        System.out.println("1. List All Sales");
        int option = readInt("Select an option: ");

        switch (option) {
            case 1 -> {
                System.out.println("\n--- ALL SALES ---");
                List<Sale> sales = saleService.listAllSales();
                if (sales == null || sales.isEmpty()) {
                    System.out.println("No sales recorded.");
                } else {
                    for (Sale s : sales) {
                        System.out.println("Sale ID: " + s.getSaleId()
                                + " | Date: " + s.getDate()
                                + " | Client: " + s.getClient()
                                + " | Total: $" + s.getTotalAmount());
                    }
                }
            }
            default -> System.out.println("Invalid option.");
        }
    }

    /**
     * Displays and handles the return management sub-menu.
     */
    private void manageReturnsMenu() {
        System.out.println("\n--- Return Management ---");
        System.out.println("1. Process Product Return");
        System.out.println("2. List All Returns");
        System.out.println("3. Generate Monthly Balance"); // <-- Opción añadida para el Ajuste A6
        int option = readInt("Select an option: ");

        switch (option) {
            case 1 -> {
                String saleId = readString("Enter Original Sale ID: ");
                String productId = readString("Enter Product ID to Return: ");
                String reason = readString("Enter Reason (DEFECTIVE / CHANGE_OF_MIND): ");

                List<String> productIds = List.of(productId);

                try {
                    Return returnObj = returnService.registerReturn(saleId, productIds, reason);
                    if (returnObj != null) {
                        System.out.println("\nReturn processed successfully!");
                        System.out.println(returnObj.generateReturnReceipt());
                    } else {
                        System.out.println("Return failed. Check if sale is within 30 days or product exists.");
                    }
                } catch (Exception e) {
                    System.out.println("Error processing return: " + e.getMessage());
                }
            }
            case 2 -> {
                System.out.println("\n--- RETURNED TRANSACTIONS ---");
                List<Return> returns = returnService.viewAllReturns();
                if (returns == null || returns.isEmpty()) {
                    System.out.println("No returns recorded yet.");
                } else {
                    for (Return ret : returns) {
                        String originalSaleId = (ret.getSale() != null) ? ret.getSale().getSaleId() : "N/A";
                        System.out.println("ID: " + ret.getId()
                                + " | Date: " + ret.getReturnDate()
                                + " | Sale ID: " + originalSaleId
                                + " | Refund: $" + ret.getRefundAmount());
                    }
                }
            }
            case 3 -> { // <-- Lógica implementada para el Ajuste A6
                System.out.println("\n--- GENERATE MONTHLY BALANCE ---");
                int month = readInt("Enter Month (1-12): ");
                int year = readInt("Enter Year (e.g. 2026): ");

                try {
                    double netBalance = returnService.generateMonthlyBalance(month, year);
                    System.out.println("\n=======================================");
                    System.out.println(" NET FINANCIAL BALANCE FOR " + month + "/" + year);
                    System.out.println(" Total Balance: $" + netBalance);
                    System.out.println("=======================================");
                } catch (Exception e) {
                    System.out.println("Error generating monthly balance: " + e.getMessage());
                }
            }
            default -> System.out.println("Invalid option.");
        }
    }

    private int readInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
            System.out.print(prompt);
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return value;
    }

    private String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}