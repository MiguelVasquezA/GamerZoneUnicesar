package com.gamezone.service;

import com.gamezone.model.BasicWarranty;
import com.gamezone.model.ExtendedWarranty;
import com.gamezone.model.Product;
import com.gamezone.model.Sale;
import com.gamezone.model.Warranty;
import com.gamezone.persistence.SaleRepository;
import com.gamezone.persistence.WarrantyRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class WarrantyService {
    private final WarrantyRepository warrantyRepository;
    private final ProductService productService;
    private final SaleRepository saleRepository;

    public WarrantyService(WarrantyRepository warrantyRepository, ProductService productService, SaleRepository saleRepository) {
        this.warrantyRepository = warrantyRepository;
        this.productService = productService;
        this.saleRepository = saleRepository;
    }

    public List<Warranty> listAllWarranties() {
        return warrantyRepository.loadAll(productService, saleRepository);
    }

    public ExtendedWarranty assignExtendedWarranty(Product product, Sale sale, LocalDate startDate) {
        String id = "WAR-EXT-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        ExtendedWarranty warranty = new ExtendedWarranty(id, product, sale, startDate);
        List<Warranty> warranties = warrantyRepository.loadAll(productService, saleRepository);
        warranties.add(warranty);
        warrantyRepository.saveAll(warranties);
        return warranty;
    }

    public BasicWarranty assignBasicWarranty(Product product, Sale sale, LocalDate startDate) {
        String id = "WAR-BAS-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        BasicWarranty warranty = new BasicWarranty(id, product, sale, startDate);
        List<Warranty> warranties = warrantyRepository.loadAll(productService, saleRepository);
        warranties.add(warranty);
        warrantyRepository.saveAll(warranties);
        return warranty;
    }

    public void cancelWarrantyBySale(String saleId) {
        List<Warranty> warranties = warrantyRepository.loadAll(productService, saleRepository);
        boolean removed = warranties.removeIf(w -> w.getSale() != null && w.getSale().getSaleId().equals(saleId));
        if (removed) {
            warrantyRepository.saveAll(warranties);
        }
    }
}