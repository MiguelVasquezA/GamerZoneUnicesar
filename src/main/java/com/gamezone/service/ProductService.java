package com.gamezone.service;

import com.gamezone.exceptions.BusinessRuleException;
import com.gamezone.exceptions.ResourceNotFoundException;
import com.gamezone.model.Console;
import com.gamezone.model.Product;
import com.gamezone.model.VideoGame;
import com.gamezone.persistence.ProductRepository;
import com.gamezone.validation.ProductValidator;

import java.util.List;
import java.util.Optional;

/**
 * Contains the business rules for managing products.
 * Keeps the in-memory list of products in sync with the persisted file.
 *
 * @author Abraham Medina
 * @version 1.3
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
     *
     * @param videoGame the video game to register
     */
    public void registerVideoGame(VideoGame videoGame) {
        ProductValidator.validateProductData(
                videoGame.getId(),
                videoGame.getTitle(),
                videoGame.getPrice(),
                videoGame.getStock()
        );

        if (findById(videoGame.getId()).isPresent()) {
            throw new BusinessRuleException("A product with ID " + videoGame.getId() + " already exists.");
        }

        products.add(videoGame);
        repository.save(products);
    }

    /**
     * Registers a new console and persists the updated product list.
     *
     * @param console the console to register
     */
    public void registerConsole(Console console) {
        ProductValidator.validateProductData(
                console.getId(),
                console.getTitle(),
                console.getPrice(),
                console.getStock()
        );

        if (findById(console.getId()).isPresent()) {
            throw new BusinessRuleException("A product with ID " + console.getId() + " already exists.");
        }

        products.add(console);
        repository.save(products);
    }

    /**
     * Returns the full list of products currently available in the store.
     *
     * @return the list of registered products
     */
    public List<Product> listProducts() {
        return products;
    }

    /**
     * Searches for a product by its id.
     *
     * @param id the product id to search for
     * @return an Optional containing the product if found, or empty otherwise
     */
    public Optional<Product> findById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return Optional.empty();
        }
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    /**
     * Checks whether a product has enough stock available for a given quantity.
     *
     * @param id the product id to check
     * @param quantity the quantity requested
     * @return true if there is enough stock
     */
    public boolean hasStock(String id, int quantity) {
        Product product = findById(id).orElse(null);
        ProductValidator.validateProductExists(product, id);
        ProductValidator.validateStockAvailability(product, quantity);
        return product.getStock() >= quantity;
    }

    /**
     * Updates the stock quantity of a specific product and persists the changes.
     *
     * @param id the product id to update
     * @param newStock the new stock quantity
     */
    public void updateStock(String id, int newStock) {
        Product product = findById(id).orElse(null);
        ProductValidator.validateProductExists(product, id);

        ProductValidator.validateProductData(product.getId(), product.getTitle(), product.getPrice(), newStock);
        product.setStock(newStock);
        repository.save(products);
    }

    /**
     * Reduces the stock of a product by the given quantity and persists the change.
     *
     * @param id the product id whose stock will be reduced
     * @param quantity the quantity to subtract from the current stock
     */
    public void reduceStock(String id, int quantity) {
        Product product = findById(id).orElse(null);
        ProductValidator.validateProductExists(product, id);

        ProductValidator.validateStockAvailability(product, quantity);

        int updatedStock = product.getStock() - quantity;
        product.setStock(updatedStock);
        repository.save(products);
    }

    /**
     * Restores stock quantity for a given product when a return is processed.
     *
     * @param productId ID of the product to replenish
     * @param quantity  quantity to add back to stock
     */
    public void restoreStock(String productId, int quantity) {
        if (productId == null || quantity <= 0) {
            return;
        }
        Product product = findById(productId).orElse(null);
        ProductValidator.validateProductExists(product, productId);

        product.setStock(product.getStock() + quantity);
        repository.save(products);
    }
}