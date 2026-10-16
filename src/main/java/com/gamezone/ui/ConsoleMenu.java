package com.gamezone.ui;

import com.gamezone.model.Return;
import com.gamezone.model.Warranty;
import com.gamezone.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Console user interface for GamerZoneUnicesar application.
 * Handles user interaction for managing sales, returns, inventory, warranties,
 * and financial balances.
 *
 * @author Desarrolladora 2
 * @version 1.5
 */
public class ConsoleMenu {

    private final SaleService saleService;
    private final ProductService productService;
    private final AccessoryService accessoryService;
    private final ReturnService returnService;
    private final WarrantyService warrantyService;
    private final Scanner scanner;

    /**
     * Constructs the ConsoleMenu with all required service dependencies.
     *
     * @param saleService sale operations service
     * @param productService product stock service
     * @param accessoryService accessory stock service
     * @param returnService return and warranty cancellation service
     * @param warrantyService warranty management service
     */
    public ConsoleMenu(SaleService saleService, ProductService productService, AccessoryService accessoryService, ReturnService returnService, WarrantyService warrantyService) {
        this.saleService = saleService;
        this.productService = productService;
        this.accessoryService = accessoryService;
        this.returnService = returnService;
        this.warrantyService = warrantyService;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Starts the main interactive loop of the console application.
     */
    public void start() {
        int option;
        do {
            printHeader();
            printMenuOptions();
            option = readOption();
            handleOption(option);
        } while (option != 0);
    }

    private void printHeader() {
        System.out.println("\n=========================================");
        System.out.println("           GAMERZONE UNICESAR            ");
        System.out.println("=========================================");
    }

    private void printMenuOptions() {
        System.out.println("1. Listar Ventas");
        System.out.println("2. Registrar Devolución (con anulación de garantía)");
        System.out.println("3. Ver Devoluciones Registradas");
        System.out.println("4. Generar Balance Financiero Mensual");
        System.out.println("5. Ver Garantías Activas");
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
                listSales();
                break;
            case 2:
                registerReturnMenu();
                break;
            case 3:
                viewReturnsMenu();
                break;
            case 4:
                generateMonthlyBalanceMenu();
                break;
            case 5:
                viewWarrantiesMenu();
                break;
            case 0:
                System.out.println("¡Gracias por usar GamerZone Unicesar!");
                break;
            default:
                System.out.println("Opción inválida. Intente de nuevo.");
        }
    }

    private void listSales() {
        System.out.println("\n--- LISTADO DE VENTAS ---");
        var sales = saleService.listAllSales();
        if (sales.isEmpty()) {
            System.out.println("No hay ventas registradas.");
        } else {
            sales.forEach(s -> System.out.println("ID Venta: " + s.getSaleId() + " | Fecha: " + s.getDate() + " | Total: $" + s.getTotalAmount()));
        }
    }

    private void registerReturnMenu() {
        System.out.println("\n--- REGISTRAR DEVOLUCIÓN ---");
        System.out.print("Ingrese el ID de la venta original: ");
        String saleId = scanner.nextLine();

        List<String> productIds = new ArrayList<>();
        System.out.println("Ingrese los IDs de los productos/accesorios a devolver (escriba 'fin' para terminar):");
        while (true) {
            System.out.print("ID de ítem: ");
            String pId = scanner.nextLine();
            if ("fin".equalsIgnoreCase(pId)) {
                break;
            }
            productIds.add(pId);
        }

        System.out.print("Ingrese el motivo de la devolución: ");
        String reason = scanner.nextLine();

        try {
            Return newReturn = returnService.registerReturn(saleId, productIds, reason);
            System.out.println("\n¡Devolución registrada y garantía anulada exitosamente!");
            System.out.println(newReturn.generateReturnReceipt());
        } catch (Exception e) {
            System.out.println("Error al procesar la devolución: " + e.getMessage());
        }
    }

    private void viewReturnsMenu() {
        System.out.println("\n--- LISTADO DE DEVOLUCIONES ---");
        List<Return> returns = returnService.viewAllReturns();
        if (returns.isEmpty()) {
            System.out.println("No hay devoluciones registradas.");
        } else {
            for (Return r : returns) {
                System.out.println("Devolución ID: " + r.getId() + " | Venta: " + r.getSale().getSaleId() + " | Monto Reembolsado: $" + String.format("%.2f", r.getRefundAmount()) + " | Fecha: " + r.getReturnDate());
            }
        }
    }

    private void generateMonthlyBalanceMenu() {
        System.out.println("\n--- REPORTE DE BALANCE MENSUAL ---");
        System.out.print("Ingrese el mes (1-12): ");
        int month = Integer.parseInt(scanner.nextLine());
        System.out.print("Ingrese el año (ej. 2026): ");
        int year = Integer.parseInt(scanner.nextLine());

        double balance = returnService.generateMonthlyBalance(month, year);
        System.out.println("\nBalance Neto (Ventas - Devoluciones) para el período " + month + "/" + year + ": $" + String.format("%.2f", balance));
    }

    private void viewWarrantiesMenu() {
        System.out.println("\n--- LISTADO DE GARANTÍAS ---");
        List<Warranty> warranties = warrantyService.listAllWarranties();
        if (warranties.isEmpty()) {
            System.out.println("No hay garantías registradas o vigentes.");
        } else {
            for (Warranty w : warranties) {
                System.out.println("Garantía ID: " + w.getId() + " | Venta Asociada: " + (w.getSale() != null ? w.getSale().getSaleId() : "N/A"));
            }
        }
    }
}