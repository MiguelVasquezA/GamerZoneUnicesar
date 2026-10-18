package com.gamezone.service;

import com.gamezone.model.Sale;
import com.gamezone.model.SaleItem;
import com.gamezone.persistence.IProductRepository;
import com.gamezone.persistence.ISaleRepository;
import java.util.List;
import java.util.Optional;

/**
 * Service managing sales business transactions.
 * Interacts with persistence through repository abstractions.
 */
public class SaleService {
    private final ISaleRepository saleRepository;
    private final IProductRepository productRepository;

    public SaleService(ISaleRepository saleRepository, IProductRepository productRepository) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
    }

    public void registerSale(Sale sale) {
        if (sale == null) {
            throw new IllegalArgumentException("Sale cannot be null.");
        }
        // Information Expert: Sale calculates its total internally
        sale.calculateTotal();
        saleRepository.save(sale);
    }

    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    public Optional<Sale> getSaleById(String saleId) {
        return saleRepository.findById(saleId);
    }
}