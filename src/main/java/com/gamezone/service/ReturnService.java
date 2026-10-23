package com.gamezone.service;

import com.gamezone.model.Product;
import com.gamezone.model.Return;
import com.gamezone.model.Sale;
import com.gamezone.model.SaleItem;
import com.gamezone.persistence.ReturnRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Servicio encargado de gestionar la lógica de devoluciones, validaciones,
 * restauración de inventario y la integración con anulación de garantías.
 */
public class ReturnService {

    private final ReturnRepository returnRepository;
    private final SaleService saleService;
    private final ProductService productService;
    private final AccessoryService accessoryService;
    private final WarrantyService warrantyService;

    public ReturnService(ReturnRepository returnRepository, SaleService saleService, ProductService productService, AccessoryService accessoryService, WarrantyService warrantyService) {
        this.returnRepository = returnRepository;
        this.saleService = saleService;
        this.productService = productService;
        this.accessoryService = accessoryService;
        this.warrantyService = warrantyService;
    }

    public Return registerReturn(String saleId, List<String> productIds, String reason) {
        // 1. Buscar la venta original
        Sale sale = saleService.getAllSales().stream()
                .filter(s -> s.getSaleId().equals(saleId))
                .findFirst()
                .orElse(null);

        if (sale == null) {
            throw new IllegalArgumentException("La venta original con ID " + saleId + " no existe.");
        }

        // 2. Validar límite de 30 días para devoluciones
        LocalDate saleDate = LocalDate.parse(sale.getDate());
        if (saleDate.plusDays(30).isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("El plazo de 30 días para realizar devoluciones de esta venta ha expirado.");
        }

        // 3. Validar los productos pertenecientes a la venta
        List<Product> productsToReturn = new ArrayList<>();
        for (String productId : productIds) {
            Product product = sale.getItems().stream()
                    .map(SaleItem::getProduct)
                    .filter(p -> p != null && p.getId().equals(productId))
                    .findFirst()
                    .orElse(null);

            if (product == null) {
                throw new IllegalArgumentException("El ítem con ID " + productId + " no pertenece a la venta original.");
            }
            productsToReturn.add(product);
        }

        // 4. Restaurar el stock en inventario
        for (Product product : productsToReturn) {
            restoreItemStock(product);
        }

        // 5. Anular la garantía asociada a la venta si existe
        if (warrantyService != null) {
            warrantyService.cancelWarrantyBySale(saleId);
        }

        // 6. Crear la instancia de la devolución
        String returnId = "RET-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        Return newReturn = new Return(returnId, sale, productsToReturn, reason, LocalDate.now());

        // 7. ¡AQUÍ ESTÁ EL TRUCO! Cargar la lista existente, agregar la nueva devolución y GUARDAR EN ARCHIVO
        List<Return> allReturns = viewAllReturns();
        if (allReturns == null) {
            allReturns = new ArrayList<>();
        }
        allReturns.add(newReturn);
        returnRepository.saveAll(allReturns);

        return newReturn;
    }

    private void restoreItemStock(Product product) {
        if (accessoryService != null) {
            boolean restoredAsAccessory = accessoryService.restoreStock(product.getId(), 1);
            if (!restoredAsAccessory && productService != null) {
                productService.registerProduct(product);
            }
        } else if (productService != null) {
            productService.registerProduct(product);
        }
    }

    public List<Return> viewAllReturns() {
        List<Return> returns = returnRepository.loadAll();
        return (returns != null) ? returns : new ArrayList<>();
    }

    public List<Return> viewReturnsByCustomer(String customerId) {
        List<Return> returns = viewAllReturns();
        if (returns == null) return new ArrayList<>();
        return returns.stream()
                .filter(r -> r.getSale() != null && customerId.equals(r.getSale().getClient()))
                .collect(Collectors.toList());
    }

    public List<Return> viewReturnsBySale(String saleId) {
        List<Return> returns = viewAllReturns();
        if (returns == null) return new ArrayList<>();
        return returns.stream()
                .filter(r -> r.getSale() != null && saleId.equals(r.getSale().getSaleId()))
                .collect(Collectors.toList());
    }

    public double generateMonthlyBalance(int month, int year) {
        double totalSales = saleService.getAllSales().stream()
                .filter(s -> {
                    LocalDate date = LocalDate.parse(s.getDate());
                    return date.getMonthValue() == month && date.getYear() == year;
                })
                .mapToDouble(Sale::getTotalAmount)
                .sum();

        List<Return> returns = viewAllReturns();
        double totalReturns = 0.0;
        if (returns != null) {
            totalReturns = returns.stream()
                    .filter(r -> r.getReturnDate() != null && r.getReturnDate().getMonthValue() == month && r.getReturnDate().getYear() == year)
                    .mapToDouble(Return::getRefundAmount)
                    .sum();
        }

        return totalSales - totalReturns;
    }
}