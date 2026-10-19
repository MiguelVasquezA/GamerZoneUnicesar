package com.gamezone.persistence;

import com.gamezone.model.Sale;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Concrete implementation of sales file storage.
 * Implements ISaleRepository interface to comply with Dependency Inversion.
 */
public class SaleRepository implements ISaleRepository {
    private final String filePath;

    public SaleRepository(String filePath) {
        this.filePath = filePath;
    }

    public SaleRepository() {
        this("data/sales.dat");
    }

    @Override
    @SuppressWarnings("unchecked")
    public synchronized List<Sale> findAll() {
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Sale>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public synchronized void save(Sale sale) {
        List<Sale> sales = findAll();
        sales.add(sale);
        File file = new File(filePath);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(sales);
        } catch (IOException e) {
            throw new RuntimeException("Error persisting sale transaction.", e);
        }
    }

    @Override
    public Optional<Sale> findById(String id) {
        return findAll().stream()
                .filter(s -> s.getSaleId().equalsIgnoreCase(id))
                .findFirst();
    }
}