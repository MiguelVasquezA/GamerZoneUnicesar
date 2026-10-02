package com.gamezone.ui;

import com.gamezone.model.*;
import com.gamezone.service.*;
import com.gamezone.model.Promotion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Console user interface class responsible for handling application menus,
 * reading user inputs, and interacting exclusively with the service layer.
 *
 * @author Miguel Vasquez
 * @version 1.1
 */
public class ConsoleMenu {

    /** Service layer instance for managing products. */
    private final ProductService productService;

    /** Service layer instance for managing accessories. */
    private final AccessoryService accessoryService;

    /** Service layer instance for managing persons. */
    private final PersonService personService;

    /** Service layer instance for managing sales. */
    private final SaleService saleService;

    /** Scanner instance for reading input from the standard console. */
    private final Scanner scanner;

    /** Service layer instance for managing promotions. */
    private final PromotionService promotionService;

    /** Service layer instance for managing returns. */
    private final ReturnService returnService;

    /**
     * Constructs a ConsoleMenu instance with all required service dependencies,
     * including product, accessory, person, sale, promotion, and return services.
     *
     * @param productService   service layer instance handling product operations
     * @param accessoryService service layer instance handling accessory operations
     * @param personService    service layer instance handling person operations
     * @param saleService      service layer instance handling sale operations
     * @param promotionService service layer instance handling promotion operations
     * @param returnService    service layer instance handling return operations
     */
    public ConsoleMenu(ProductService productService, AccessoryService accessoryService, PersonService personService, SaleService saleService, Scanner scanner, PromotionService promotionService, ReturnService returnService) {
        this.productService = productService;
        this.accessoryService = accessoryService;
        this.personService = personService;
        this.saleService = saleService;
        this.scanner = scanner;
        this.promotionService = promotionService;
        this.returnService = returnService;
    }

    /**
     * Starts the interactive console menu loop for the application.
     */
    public void start() {
        boolean exit = false;
        while (!exit) {
            printMainMenu();
            int option = readInt("Select an option: ");
            switch (option) {
                case 1 -> manageProductsMenu();
                case 2 -> manageAccessoriesMenu();
                case 3 -> managePersonsMenu();
                case 4 -> registerSale();
                case 5 -> managePromotionsMenu();
                case 6 -> consultInformationMenu();
                case 7 -> manageReturnsMenu();
                case 0 -> {
                    System.out.println("Exiting the application. Goodbye!");
                    exit = true;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Prints the main system menu options to the console.
     */
    private void printMainMenu() {
        System.out.println("\n========== GAMERZONE UNICESAR ==========");
        System.out.println("1. Product Management");
        System.out.println("2. Accessory Management");
        System.out.println("3. Person Management");
        System.out.println("4. Register Sale");
        System.out.println("5. Promotions Management");
        System.out.println("6. Consult System Information");
        System.out.println("7. Manage Returns"); // <--- NUEVA OPCIÓN
        System.out.println("0. Exit");
        System.out.println("=======================================");
    }

    /**
     * Displays and handles the product management sub-menu.
     */
    private void manageProductsMenu() {
        System.out.println("\n--- Product Management ---");
        System.out.println("1. Add Video Game");
        System.out.println("2. Add Console");
        System.out.println("3. List All Products");
        int option = readInt("Select an option: ");

        switch (option) {
            case 1 -> {
                String id = readString("Enter Product ID: ");
                double price = readDouble("Enter Price: ");
                int stock = readInt("Enter Stock Quantity: ");
                String title = readString("Enter Title: ");
                int ageRating = readInt("Enter Age Rating (e.g. 18): ");
                String genre = readString("Enter Genre: ");
                String platform = readString("Enter Platform: ");

                VideoGame game = new VideoGame(id, price, stock, title, ageRating, genre, platform);
                productService.registerVideoGame(game);
                System.out.println("Video game registered and saved: " + game.getTitle());
            }
            case 2 -> {
                String id = readString("Enter Product ID: ");
                double price = readDouble("Enter Price: ");
                int stock = readInt("Enter Stock Quantity: ");
                String title = readString("Enter Title: ");
                String brand = readString("Enter Brand: ");
                String generation = readString("Enter Generation: ");
                String model = readString("Enter Model: ");

                Console console = new Console(id, price, stock, title, brand, generation, model);
                productService.registerConsole(console);
                System.out.println("Console registered and saved: " + console.getTitle());
            }
            case 3 -> displayInventory();
            default -> System.out.println("Invalid option.");
        }
    }

    /**
     * Displays and handles the accessory management sub-menu (Requerimiento 1).
     */
    private void manageAccessoriesMenu() {
        System.out.println("\n--- Accessory Management ---");
        System.out.println("1. Register Controller");
        System.out.println("2. Register Cable");
        System.out.println("3. Register Memory");
        System.out.println("4. List All Accessories");
        System.out.println("5. List Accessories by Type");
        System.out.println("6. Consult Accessories Compatible with Console");
        int option = readInt("Select an option: ");

        switch (option) {
            case 1 -> {
                String id = readString("Enter Accessory ID: ");
                double price = readDouble("Enter Price: ");
                int stock = readInt("Enter Stock Quantity: ");
                String title = readString("Enter Title: ");
                String consolesInput = readString("Enter Compatible Consoles (comma-separated IDs): ");
                List<String> compatibleConsoles = Arrays.stream(consolesInput.split(","))
                        .map(String::trim)
                        .toList();
                String connectionType = readString("Enter Connection Type (Wireless/Wired): ");

                accessoryService.registerController(id, price, stock, title, compatibleConsoles, connectionType);
                System.out.println("Controller registered successfully: " + title);
            }
            case 2 -> {
                String id = readString("Enter Accessory ID: ");
                double price = readDouble("Enter Price: ");
                int stock = readInt("Enter Stock Quantity: ");
                String title = readString("Enter Title: ");
                String consolesInput = readString("Enter Compatible Consoles (comma-separated IDs, or leave blank): ");
                List<String> compatibleConsoles = consolesInput.isBlank() ? new ArrayList<>() :
                        Arrays.stream(consolesInput.split(",")).map(String::trim).toList();
                String connectorType = readString("Enter Connector Type (HDMI, USB, etc.): ");
                double length = readDouble("Enter Cable Length (meters): ");

                // Se envían los parámetros individuales
                accessoryService.registerCable(id, price, stock, title, compatibleConsoles, connectorType, length);
                System.out.println("Cable registered successfully: " + title);
            }
            case 3 -> {
                String id = readString("Enter Accessory ID: ");
                double price = readDouble("Enter Price: ");
                int stock = readInt("Enter Stock Quantity: ");
                String title = readString("Enter Title: ");
                String consolesInput = readString("Enter Compatible Consoles (comma-separated IDs): ");
                List<String> compatibleConsoles = Arrays.stream(consolesInput.split(","))
                        .map(String::trim)
                        .toList();
                int capacity = readInt("Enter Storage Capacity (GB): ");
                String memoryType = readString("Enter Memory Type (SD, microSD, Internal): ");

                // Se envían los parámetros individuales
                accessoryService.registerMemory(id, price, stock, title, compatibleConsoles, capacity, memoryType);
                System.out.println("Memory registered successfully: " + title);
            }
            case 4 -> displayAccessories(accessoryService.listAllAccessories());
            case 5 -> {
                String type = readString("Enter type to filter (Controller, Cable, Memory): ");
                displayAccessories(accessoryService.listAccessoriesByType(type));
            }
            case 6 -> {
                String consoleId = readString("Enter Console ID to check compatibility: ");
                displayAccessories(accessoryService.findAccessoriesCompatibleWith(consoleId));
            }
            default -> System.out.println("Invalid option.");
        }
    }

    /**
     * Helper method to display a list of accessories.
     *
     * @param accessories the list of accessories to print
     */
    private void displayAccessories(List<Accessory> accessories) {
        System.out.println("\n--- ACCESSORY LIST ---");
        if (accessories == null || accessories.isEmpty()) {
            System.out.println("No accessories found.");
        } else {
            for (Accessory accessory : accessories) {
                System.out.println("ID: " + accessory.getId()
                        + " | Title: " + accessory.getTitle()
                        + " | Price: $" + accessory.getPrice()
                        + " | Stock: " + accessory.getStock()
                        + " | Info: " + accessory.getDescription());
            }
        }
    }

    /**
     * Displays and handles the person management sub-menu.
     */
    private void managePersonsMenu() {
        System.out.println("\n--- Person Management ---");
        System.out.println("1. Register Customer");
        System.out.println("2. Register Seller");
        System.out.println("3. List All Persons");
        int option = readInt("Select an option: ");

        switch (option) {
            case 1 -> {
                String idCard = readString("Enter ID Card: ");
                String name = readString("Enter Name: ");
                String phone = readString("Enter Phone: ");
                String email = readString("Enter Email: ");

                Customer customer = new Customer(idCard, name, phone, email);
                System.out.println("Customer registered: " + customer.getName());
            }
            case 2 -> {
                String idCard = readString("Enter ID Card: ");
                String name = readString("Enter Name: ");
                String phone = readString("Enter Phone: ");
                String employeeCode = readString("Enter Employee Code: ");
                String shift = readString("Enter Shift: ");

                Seller seller = new Seller(idCard, name, phone, employeeCode, shift);
                System.out.println("Seller registered: " + seller.getName());
            }
            case 3 -> System.out.println("Listing persons...");
            default -> System.out.println("Invalid option.");
        }
    }

    /**
     * Displays and handles the promotion management sub-menu.
     * Allows registering new promotion types (percentage, category, bulk)
     * and viewing all or active promotions.
     */
    private void managePromotionsMenu(){
        System.out.println("\n--- Promotions Management ---");
        System.out.println("1. Register General Percentage Promotions");
        System.out.println("2. Register Category Discount Promotion");
        System.out.println("3. Register Bulk Purchase Promotion");
        System.out.println("4. List All Promotions");
        System.out.println("5. List Active Promotions");
        int option = readInt("Select an option: ");

        switch (option) {
            case 1 -> {
                String id = readString("Enter Promotion ID: ");
                String name = readString("Enter Promotion Name: ");
                LocalDate startDate = LocalDate.parse(readString("Enter Start Date (YYYY-MM-DD): "));
                LocalDate endDate = LocalDate.parse(readString("Enter End Date (YYYY-MM-DD): "));
                double percentage = readDouble("Enter Discount Percentage: ");

                promotionService.registerPercentageDiscount(id, name, startDate, endDate, percentage);
                System.out.println("General Percentage Promotion registered successfully: " + name);
            }
            case 2 -> {
                String id = readString("Enter Promotion ID: ");
                String name = readString("Enter Promotion Name: ");
                LocalDate startDate = LocalDate.parse(readString("Enter Start Date (YYYY-MM-DD): "));
                LocalDate endDate = LocalDate.parse(readString("Enter End Date (YYYY-MM-DD): "));
                double percentage = readDouble("Enter Discount Percentage: ");
                String category = readString("Enter Target Category (VIDEOGAME/CONSOLE): ");

                promotionService.registerCategoryDiscount(id, name, startDate, endDate, percentage, category);
                System.out.println("Category Promotion registered successfully: " + name);
            }
            case 3 -> {
                String id = readString("Enter Promotion ID: ");
                String name = readString("Enter Promotion Name: ");
                LocalDate startDate = LocalDate.parse(readString("Enter Start Date (YYYY-MM-DD): "));
                LocalDate endDate = LocalDate.parse(readString("Enter End Date (YYYY-MM-DD): "));
                double percentage = readDouble("Enter Discount Percentage: ");
                int minQty = readInt("Enter Minimum Quantity of Items: ");

                promotionService.registerBulkPurchaseDiscount(id, name, startDate, endDate, percentage, minQty);
                System.out.println("Bulk Purchase Promotion registered successfully: " + name);
            }
            case 4 -> displayPromotions(promotionService.listAllPromotions());
            case 5 -> displayPromotions(promotionService.listActivePromotions());
            default -> System.out.println("Invalid option. Returning to main menu.");
        }
    }

    /**
     * Helper method to print a list of promotions in a clean formatted layout.
     *
     * @param promotions the list of promotions to display
     */
    private void displayPromotions(List<Promotion> promotions) {
        System.out.println("\n--- PROMOTION LIST ---");
        if (promotions == null || promotions.isEmpty()) {
            System.out.println("No promotions available.");
        } else {
            for (Promotion promo : promotions) {
                System.out.println("ID: " + promo.getId()
                        + " | Name: " + promo.getName()
                        + " | Start: " + promo.getStartDate()
                        + " | End: " + promo.getEndDate()
                        + " | Active: " + promo.isActive(LocalDate.now()));
            }
        }
    }

    /**
     * Displays and handles the sale registration flow.
     * Allows selecting both standard products and accessories in the same sale.
     */
    private void registerSale() {
        System.out.println("\n--- Register New Sale ---");
        String customerIdCard = readString("Enter Customer ID Card: ");
        String sellerIdCard = readString("Enter Seller ID Card: ");

        List<Product> itemsToBuy = new ArrayList<>();
        boolean addingItems = true;

        while (addingItems) {
            System.out.println("\n1. Add Product (Game/Console)");
            System.out.println("2. Add Accessory (Controller/Cable/Memory)");
            System.out.println("3. Finish and Process Sale");
            int choice = readInt("Select option: ");

            switch (choice) {
                case 1 -> {
                    String productId = readString("Enter Product ID: ");
                    productService.findById(productId).ifPresentOrElse(
                            itemsToBuy::add,
                            () -> System.out.println("Product not found.")
                    );
                }
                case 2 -> {
                    String accessoryId = readString("Enter Accessory ID: ");
                    Accessory accessory = accessoryService.findById(accessoryId);
                    if (accessory != null) {
                        itemsToBuy.add(accessory);
                        System.out.println("Accessory added to sale: " + accessory.getTitle());
                    } else {
                        System.out.println("Accessory not found.");
                    }
                }
                case 3 -> addingItems = false;
                default -> System.out.println("Invalid option.");
            }
        }

        if (!itemsToBuy.isEmpty()) {
            try {
                Sale sale = saleService.registerSale(customerIdCard, sellerIdCard, itemsToBuy);
                System.out.println("\nSale processed successfully!");
                System.out.println(sale.generateReceipt());
            } catch (Exception e) {
                System.out.println("Error processing sale: " + e.getMessage());
            }
        } else {
            System.out.println("Sale canceled. No items selected.");
        }
    }

    /**
     * Displays and handles the information lookup sub-menu.
     * Fetches real-time data from the service layer for inventory and registered persons.
     */
    private void consultInformationMenu() {
        System.out.println("\n--- Consult System Information ---");
        System.out.println("1. View Available Product Inventory");
        System.out.println("2. View Available Accessory Inventory");
        System.out.println("3. View All Persons");

        int option = readInt("Select an option: ");
        switch (option) {
            case 1 -> displayInventory();
            case 2 -> displayAccessories(accessoryService.listAllAccessories());
            case 3 -> displayPersons();
            default -> System.out.println("Invalid option.");
        }
    }

    /**
     * Helper method to fetch and print the current inventory of products.
     */
    private void displayInventory() {
        System.out.println("\n--- CURRENT PRODUCT INVENTORY ---");
        List<Product> products = productService.listProducts();

        if (products == null || products.isEmpty()) {
            System.out.println("No products available in inventory.");
        } else {
            for (Product product : products) {
                System.out.println("ID: " + product.getId()
                        + " | Title: " + product.getTitle()
                        + " | Price: $" + product.getPrice()
                        + " | Stock: " + product.getStock());
            }
        }
    }

    /**
     * Helper method to fetch and print all registered persons.
     */
    private void displayPersons() {
        System.out.println("\n--- REGISTERED PERSONS ---");
        System.out.println("Listing persons registered in system...");
    }

    /**
     * Reads a line of text input from the user.
     *
     * @param prompt message displayed to prompt the user
     * @return trimmed text entered by the user
     */
    private String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    /**
     * Reads an integer value from the user, retrying upon invalid input.
     *
     * @param prompt message displayed to prompt the user
     * @return valid integer entered by the user
     */
    private int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer.");
            }
        }
    }

    /**
     * Reads a double/floating point value from the user, retrying upon invalid input.
     *
     * @param prompt message displayed to prompt the user
     * @return valid double value entered by the user
     */
    private double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    /**
     * Displays and handles the return management sub-menu (Requerimiento 3).
     */
    private void manageReturnsMenu() {
        System.out.println("\n--- Return Management ---");
        System.out.println("1. Process Product Return");
        System.out.println("2. List All Returns");
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
            default -> System.out.println("Invalid option.");
        }
    }
}