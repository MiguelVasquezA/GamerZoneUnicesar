package com.gamezone.ui;

import com.gamezone.model.*;
import com.gamezone.service.AccessoryService;
import com.gamezone.service.PersonService;
import com.gamezone.service.ProductService;
import com.gamezone.service.SaleService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Console user interface class responsible for handling application menus,
 * reading user inputs, and interacting exclusively with the service layer.
 *
 * @author Miguel Vasquez
 * @version 1.0
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

    /**
     * Constructs a ConsoleMenu instance with the required services.
     *
     * @param productService   service handling product operations
     * @param accessoryService service handling accessory operations
     * @param personService    service handling person operations
     * @param saleService      service handling sale operations
     */
    public ConsoleMenu(ProductService productService, AccessoryService accessoryService, PersonService personService, SaleService saleService) {
        this.productService = productService;
        this.accessoryService = accessoryService;
        this.personService = personService;
        this.saleService = saleService;
        this.scanner = new Scanner(System.in);
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
                case 5 -> consultInformationMenu();
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
        System.out.println("5. Consult System Information");
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

                // Se envían los parámetros individuales
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
                System.out.println("Sale ID: " + sale.getSaleId() + " | Total: $" + sale.getTotalAmount());
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
}