package com.gamezone.persistence;

import com.gamezone.model.Sale;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for saving and loading sale records to and from disk.
 *
 * @author Miguel Vasquez
 * @version 1.0
 */
public class SaleRepository {

    private final String filePath;

    /**
     * Constructs a SaleRepository with a specified file path.
     *
     * @param filePath the path of the file where sales are stored
     */
    public SaleRepository(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Saves the list of sales into the storage file.
     *
     * @param sales the list of Sale objects to persist
     */
    public void saveSale(List<Sale> sales) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(sales);
        } catch (IOException e) {
            System.err.println("Error saving sales to file: " + e.getMessage());
        }
    }

    /**
     * Loads the list of sales from the storage file.
     *
     * @return the list of stored Sale objects, or an empty list if the file does not exist or an error occurs
     */
    @SuppressWarnings("unchecked")
    public List<Sale> loadSales() {
        List<Sale> sales = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            sales = (List<Sale>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading sales from file (or file not found): " + e.getMessage());
        }
        return sales;
    }
}