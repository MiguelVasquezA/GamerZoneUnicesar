package com.gamezone.service;

import com.gamezone.model.Console;
import com.gamezone.model.Product;
import com.gamezone.model.VideoGame;
import com.gamezone.persistence.ProductRepository;

import java.util.List;
import java.util.Optional;

/**
 * Contains the business rules for managing products.
 * Keeps the in-memory list of products in sync with the persisted file.
 * @author Abraham Medina
 * @version 1.0
 */
public class ProductService {

    private final ProductRepository repository;
    private final List<Product> products;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
        this.products = repository.load();
    }

    public void registerVideoGame(VideoGame videoGame) {
        products.add(videoGame);
        repository.save(products);
    }

    public void registerConsole(Console console) {
        products.add(console);
        repository.save(products);
    }

    public List<Product> listProducts() {
        return products;
    }

    public Optional<Product> findById(String id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    public boolean hasStock(String id, int quantity) {
        return findById(id)
                .map(product -> product.getStock() >= quantity)
                .orElse(false);
    }

    public void reduceStock(String id, int quantity) {
        findById(id).ifPresent(product -> {
            product.setStock(product.getStock() - quantity);
            repository.save(products);
        });
    }
}