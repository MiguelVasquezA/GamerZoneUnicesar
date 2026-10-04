package com.gamezone.persistence;

import com.gamezone.model.Warranty;
import com.gamezone.model.BasicWarranty;
import com.gamezone.model.ExtendedWarranty;
import com.gamezone.model.Product;
import com.gamezone.model.Sale;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for managing warranty data persistence in CSV format.
 * Handles reading from and writing to data/warranties.csv using a discriminator field.
 *
 * @author GameZone Team
 * @version 1.0
 */
public class WarrantyRepository {
    private static final String FILE_PATH = "data/warranties.csv";

    /**
     * Constructs a new WarrantyRepository instance and ensures that
     * the target CSV storage file and its parent directories exist.
     */
    public WarrantyRepository() {
        ensureFileExists();
    }

    /**
     * Ensures that the storage file and its parent directories exist on disk.
     * If the file does not exist, it creates a new empty file.
     */
    private void ensureFileExists() {
        File file = new File(FILE_PATH);
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads all warranties from the CSV file.
     * Reconstructs BasicWarranty and ExtendedWarranty instances using a discriminator field,
     * and maps their respective product and sale relationships using the provided lists.
     *
     * @param products list of available products to resolve product references
     * @param sales list of available sales to resolve sale references
     * @return a list of loaded warranties, or an empty list if the file does not exist
     */
    public List<Warranty> loadAll(List<Product> products, List<Sale> sales) {
        List<Warranty> warranties = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return warranties;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length < 4) continue;

                String type = parts[0];
                String id = parts[1];
                String productId = parts[2];
                String saleId = parts[3];
                LocalDate startDate = parts.length > 4 && !parts[4].isEmpty() ? LocalDate.parse(parts[4]) : LocalDate.now();

                // Find product by ID
                Product product = null;
                if (products != null) {
                    product = products.stream().filter(p -> p.getId().equals(productId)).findFirst().orElse(null);
                }

                // Find sale by ID
                Sale sale = null;
                if (sales != null) {
                    sale = sales.stream().filter(s -> s.getSaleId().equals(saleId)).findFirst().orElse(null);
                }

                if ("BASIC".equalsIgnoreCase(type)) {
                    warranties.add(new BasicWarranty(id, product, sale, startDate));
                } else if ("EXTENDED".equalsIgnoreCase(type)) {
                    warranties.add(new ExtendedWarranty(id, product, sale, startDate));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return warranties;
    }

    /**
     * Saves a complete list of warranties into the CSV file.
     * Serializes each warranty using a type discriminator and its key fields.
     *
     * @param warranties the list of warranties to persist
     */
    public void saveAll(List<Warranty> warranties) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            if (warranties != null) {
                for (Warranty warranty : warranties) {
                    String type = (warranty instanceof ExtendedWarranty) ? "EXTENDED" : "BASIC";
                    String id = warranty.getId();
                    String productId = warranty.getProduct() != null ? warranty.getProduct().getId() : "";
                    String saleId = warranty.getSale() != null ? warranty.getSale().getSaleId() : "";
                    String startDate = warranty.getStartDate() != null ? warranty.getStartDate().toString() : "";

                    writer.println(type + "," + id + "," + productId + "," + saleId + "," + startDate);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
