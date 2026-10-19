package com.gamezone.service;

import com.gamezone.model.Product;
import com.gamezone.persistence.IProductRepository;
import java.util.List;
import java.util.Optional;

/**
 * Service managing product operations.
 * Interacts with storage strictly through the IProductRepository abstraction.
 */
public class ProductService {
    private final IProductRepository productRepository;

    public ProductService(IProductRepository productRepository) {
        if (productRepository == null) {
            throw new IllegalArgumentException("Repository cannot be null.");
        }
        this.productRepository = productRepository;
    }

    public void registerProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null.");
        }
        productRepository.save(product);
    }

    /**
     * Information Expert Pattern (Fix V-DEV1-01):
     * Delegates stock reduction logic directly to the Product domain entity.
     */
    public void reduceStock(String productId, int quantity) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.reduceStock(quantity); // Encapsulated mutation inside entity
            productRepository.update(product);
        } else {
            throw new IllegalArgumentException("Product with ID " + productId + " not found.");
        }
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }
}