package com.gamezone.persistence;

import com.gamezone.model.Return;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository responsible for handling the persistence of Return entities
 * in a CSV file located at data/returns.csv.
 *
 * @author Desarrolladora 2
 * @version 2.0
 */
public class ReturnRepository {

    private final String filePath = "data/returns.csv";

    /**
     * Constructs a new ReturnRepository without high-level service dependencies.
     */
    public ReturnRepository() {
        // Persistence layer is decoupled from business services (DIP Fix - V-01)
    }

    /**
     * Loads all return records from the CSV persistence file.
     *
     * @return a list of all registered returns, or an empty list if the file does not exist.
     */
    public List<Return> loadAll() {
        List<Return> returns = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            return returns;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // TODO: Implement parsing logic from CSV row to Return object if required
            }
        } catch (IOException e) {
            System.err.println("Error reading returns data: " + e.getMessage());
        }
        return returns;
    }

    /**
     * Saves the complete list of return records into the CSV persistence file.
     *
     * @param returns the list of Return objects to be persisted.
     */
    public void saveAll(List<Return> returns) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Return ret : returns) {
                writer.println(ret.toString());
            }
        } catch (IOException e) {
            System.err.println("Error writing returns data: " + e.getMessage());
        }
    }
}