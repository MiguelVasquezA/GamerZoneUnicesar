package com.gamezone.persistence;

import com.gamezone.model.Product;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Concrete implementation of product storage.
 * Implements IProductRepository interface to comply with Dependency Inversion.
 */
public class ProductRepository implements IProductRepository {
    private final String filePath;

    public ProductRepository(String filePath) {
        this.filePath = filePath;
    }

    public ProductRepository() {
        this("data/products.dat");
    }

    @Override
    @SuppressWarnings("unchecked")
    public synchronized List<Product> findAll() {
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Product>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public synchronized void save(Product product) {
        List<Product> products = findAll();
        products.removeIf(p -> p.getId().equalsIgnoreCase(product.getId()));
        products.add(product);
        saveAll(products);
    }

    @Override
    public synchronized void update(Product product) {
        save(product);
    }

    @Override
    public Optional<Product> findById(String id) {
        return findAll().stream()
                .filter(p -> p.getId().equalsIgnoreCase(id))
                .findFirst();
    }

    private synchronized void saveAll(List<Product> products) {
        File file = new File(filePath);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(products);
        } catch (IOException e) {
            throw new RuntimeException("Error persisting product list.", e);
        }
    }
}