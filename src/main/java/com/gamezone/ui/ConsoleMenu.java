package com.gamezone.ui;

import com.gamezone.model.*;
import com.gamezone.service.PersonService;
import com.gamezone.service.ProductService;
import com.gamezone.service.SaleService;

import java.util.Scanner;

/**
 * Console user interface for managing the GamerZone system.
 */
public class ConsoleMenu {

    private final ProductService productService;
    private final PersonService personService;
    private final SaleService saleService;
    private final Scanner scanner;

    public ConsoleMenu(ProductService productService, PersonService personService, SaleService saleService) {
        this.productService = productService;
        this.personService = personService;
        this.saleService = saleService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean exit = false;
        while (!exit) {
            printMainMenu();
            int option = readInt("Select an option: ");
            switch (option) {
                case 1 -> manageProductsMenu();
                case 2 -> managePersonsMenu();
                case 3 -> registerSale();
                case 4 -> consultInformationMenu();
                case 0 -> {
                    System.out.println("Exiting the application. Goodbye!");
                    exit = true;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void printMainMenu() {
        System.out.println("\n========== GAMERZONE UNICESAR ==========");
        System.out.println("1. Product Management");
        System.out.println("2. Person Management");
        System.out.println("3. Register Sale");
        System.out.println("4. Consult System Information");
        System.out.println("0. Exit");
        System.out.println("=======================================");
    }

    // ----------------------------------------------------
    // 1. PRODUCT MANAGEMENT
    // ----------------------------------------------------
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

                // Firma exacta en VideoGame.java: (id, price, stock, title, ageRating, genre, platform)
                VideoGame game = new VideoGame(id, price, stock, title, ageRating, genre, platform);
                System.out.println("Video game object created: " + game.getTitle());
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
                System.out.println("Console object created: " + console.getTitle());
            }
            case 3 -> System.out.println("Listing products...");
            default -> System.out.println("Invalid option.");
        }
    }

    // ----------------------------------------------------
    // 2. PERSON MANAGEMENT
    // ----------------------------------------------------
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

    // ----------------------------------------------------
    // 3. REGISTER SALE
    // ----------------------------------------------------
    private void registerSale() {
        System.out.println("\n--- Register New Sale ---");
        String customerIdCard = readString("Enter Customer ID Card: ");
        String sellerIdCard = readString("Enter Seller ID Card: ");
        System.out.println("Registering sale for customer ID: " + customerIdCard + " with seller ID: " + sellerIdCard);
    }

    // ----------------------------------------------------
    // 4. CONSULT INFORMATION
    // ----------------------------------------------------
    private void consultInformationMenu() {
        System.out.println("\n--- Consult System Information ---");
        System.out.println("1. View Available Inventory");
        System.out.println("2. View All Persons");

        int option = readInt("Select an option: ");
        switch (option) {
            case 1 -> System.out.println("Consulting available inventory...");
            case 2 -> System.out.println("Consulting registered persons...");
            default -> System.out.println("Invalid option.");
        }
    }

    // ----------------------------------------------------
    // HELPER METHODS FOR INPUT READING
    // ----------------------------------------------------
    private String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

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