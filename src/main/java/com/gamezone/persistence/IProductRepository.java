package com.gamezone.persistence;

import com.gamezone.model.Product;
import java.util.List;
import java.util.Optional;

/**
 * Abstraction for product repository operations.
 * Implements Low Coupling and Indirection GRASP patterns.
 */
public interface IProductRepository {
    /**
     * Saves a new product entity to storage.
     *
     * @param product the product entity to save
     */
    void save(Product product);

    /**
     * Retrieves all registered products.
     *
     * @return a list of all products
     */
    List<Product> findAll();

    /**
     * Finds a product by its unique identifier.
     *
     * @param id the product ID
     * @return an Optional containing the product if found, or empty otherwise
     */
    Optional<Product> findById(String id);

    /**
     * Updates an existing product's state in storage.
     *
     * @param product the product entity to update
     */
    void update(Product product);
}
