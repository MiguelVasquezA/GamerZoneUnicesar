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

    /**
     * Registers a new video game and persists the updated product list.
     * @param videoGame the video game to register
     */
    public void registerVideoGame(VideoGame videoGame) {
        products.add(videoGame);
        repository.save(products);
    }

    /**
     * Registers a new console and persists the updated product list.
     * @param console the console to register
     */
    public void registerConsole(Console console) {
        products.add(console);
        repository.save(products);
    }

    /**
     * Returns the full list of products currently available in the store.
     * @return the list of registered products
     */
    public List<Product> listProducts() {
        return products;
    }

    /**
     * Searches for a product by its id.
     * @param id the product id to search for
     * @return an Optional containing the product if found, or empty otherwise
     */
    public Optional<Product> findById(String id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    /**
     * Checks whether a product has enough stock available for a given quantity.
     * @param id the product id to check
     * @param quantity the quantity requested
     * @return true if there is enough stock, false if the product does not exist or stock is insufficient
     */
    public boolean hasStock(String id, int quantity) {
        return findById(id)
                .map(product -> product.getStock() >= quantity)
                .orElse(false);
    }

    /**
     * Updates the stock quantity of a specific product and persists the changes.
     * @param id the product id to update
     * @param newStock the new stock quantity
     */
    public void updateStock(String id, int newStock) {
        findById(id).ifPresent(product -> {
            product.setStock(newStock);
            repository.save(products);
        });
    }

    /**
     * Reduces the stock of a product by the given quantity and persists the change.
     * @param id the product id whose stock will be reduced
     * @param quantity the quantity to subtract from the current stock
     */
    public void reduceStock(String id, int quantity) {
        findById(id).ifPresent(product -> {
            int updatedStock = Math.max(0, product.getStock() - quantity);
            product.setStock(updatedStock);
            repository.save(products);
        });
    }

    /**
     * Restores stock quantity for a given product when a return is processed.
     *
     * @param productId ID of the product to replenish
     * @param quantity  quantity to add back to stock
     * @return true if product was found and stock updated; false otherwise
     */
    public boolean restoreStock(String productId, int quantity) {
        if (productId == null || quantity <= 0) {
            return false;
        }
        java.util.Optional<Product> optionalProduct = findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setStock(product.getStock() + quantity);
            repository.save(products);
            return true;
        }
        return false;
    }
}