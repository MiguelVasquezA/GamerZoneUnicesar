package com.gamezone.persistence;

import com.gamezone.model.Console;
import com.gamezone.model.Product;
import com.gamezone.model.VideoGame;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles file-based persistence for Product objects.
 * Responsible only for reading from and writing to the products data file.
 * @author FC Evento
 * @version 1.0
 */
public class ProductRepository {

    private final String filePath;

    /**
     * Constructs a ProductRepository pointing to the given file path.
     * @param filePath path to the CSV file used for storage
     */
    public ProductRepository(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Saves the full list of products to the file, overwriting its previous content.
     * @param products the list of products to persist
     */
    public void save(List<Product> products) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Product product : products) {
                writer.write(toLine(product));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving products: " + e.getMessage());
        }
    }

    /**
     * Loads the list of products from the file.
     * @return the list of products found in the file, or an empty list if the file does not exist
     */
    public List<Product> load() {
        List<Product> products = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return products;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) {
                    products.add(fromLine(line));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading products: " + e.getMessage());
        }

        return products;
    }

    /**
     * Converts a Product into its CSV line representation.
     * @param product the product to convert
     * @return a CSV-formatted line
     */
    private String toLine(Product product) {
        if (product instanceof VideoGame videoGame) {
            return String.join(",",
                    "VIDEOGAME",
                    videoGame.getId(),
                    String.valueOf(videoGame.getPrice()),
                    String.valueOf(videoGame.getStock()),
                    videoGame.getTitle(),
                    String.valueOf(videoGame.getAgeRating()),
                    videoGame.getGenre(),
                    videoGame.getPlatform());
        } else if (product instanceof Console console) {
            return String.join(",",
                    "CONSOLE",
                    console.getId(),
                    String.valueOf(console.getPrice()),
                    String.valueOf(console.getStock()),
                    console.getTitle(),
                    console.getBrand(),
                    console.getModel(),
                    console.getGeneration());
        }
        throw new IllegalArgumentException("Unknown product type: " + product.getClass());
    }

    /**
     * Converts a CSV line back into a Product instance.
     * @param line the CSV-formatted line
     * @return the reconstructed Product
     */
    private Product fromLine(String line) {
        String[] fields = line.split(",");
        String type = fields[0];
        String id = fields[1];
        double price = Double.parseDouble(fields[2]);
        int stock = Integer.parseInt(fields[3]);
        String title = fields[4];

        if (type.equals("VIDEOGAME")) {
            int ageRating = Integer.parseInt(fields[5]);
            String genre = fields[6];
            String platform = fields[7];
            return new VideoGame(id, price, stock, title, ageRating, genre, platform);
        } else if (type.equals("CONSOLE")) {
            String brand = fields[5];
            String model = fields[6];
            String generation = fields[7];
        
            return new Console(id, price, stock, title, brand, generation, model);
        }
        throw new IllegalArgumentException("Unknown product type in file: " + type);
    }
}