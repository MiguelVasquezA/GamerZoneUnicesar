package com.gamezone.persistence;

import com.gamezone.model.Product;
import java.util.List;
import java.util.Optional;

/**
 * Abstraction for Product persistence operations.
 * Satisfies the Dependency Inversion Principle (DIP / V-08).
 */
public interface IProductRepository {
    List<Product> findAll();
    void save(Product product);
    void update(Product product);
    Optional<Product> findById(String id);
}
