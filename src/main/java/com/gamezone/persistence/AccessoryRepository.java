package com.gamezone.persistence;

import com.gamezone.model.Accessory;
import com.gamezone.model.Controller;
import com.gamezone.model.Cable;
import com.gamezone.model.Memory;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Repository class responsible for persisting and loading accessories from a CSV file.
 */
public class AccessoryRepository {
    private static final String FILE_PATH = "data/accessories.csv";

    /**
     * Loads all accessories from the CSV file.
     * @return List of Accessory objects, or an empty list if the file does not exist.
     */
    public List<Accessory> loadAll() {
        List<Accessory> accessories = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return accessories;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 5) continue;

                String type = parts[0];
                String id = parts[1];
                String title = parts[2];
                double price = Double.parseDouble(parts[3]);
                int stock = Integer.parseInt(parts[4]);

                if ("CONTROLLER".equals(type)) {
                    String connectionType = parts[5];
                    List<String> compatibleConsoles = new ArrayList<>(Arrays.asList(parts).subList(6, parts.length));
                    accessories.add(new Controller(id, price, stock, title, compatibleConsoles, connectionType));
                } else if ("CABLE".equals(type)) {
                    double length = Double.parseDouble(parts[5]);
                    String connectorType = parts[6];
                    List<String> compatibleConsoles = new ArrayList<>();
                    accessories.add(new Cable(id, price, stock, title, compatibleConsoles, connectorType, length));
                } else if ("MEMORY".equals(type)) {
                    int capacity = Integer.parseInt(parts[5]);
                    String memoryType = parts[6];
                    List<String> compatibleConsoles = new ArrayList<>();
                    if (parts.length > 7) {
                        compatibleConsoles.addAll(Arrays.asList(parts).subList(7, parts.length));
                    }
                    accessories.add(new Memory(id, price, stock, title, compatibleConsoles, capacity, memoryType));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accessories;
    }

    /**
     * Saves all accessories to the CSV file.
     * @param accessories The list of accessories to save.
     */
    public void saveAll(List<Accessory> accessories) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Accessory acc : accessories) {
                StringBuilder sb = new StringBuilder();
                if (acc instanceof Controller) {
                    Controller c = (Controller) acc;
                    sb.append("CONTROLLER,").append(c.getId()).append(",")
                            .append(c.getTitle()).append(",").append(c.getPrice()).append(",")
                            .append(c.getStock()).append(",").append(c.getConnectionType());
                    if (c.getCompatibleConsoleIds() != null) {
                        for (String console : c.getCompatibleConsoleIds()) {
                            sb.append(",").append(console);
                        }
                    }
                } else if (acc instanceof Cable) {
                    Cable cb = (Cable) acc;
                    sb.append("CABLE,").append(cb.getId()).append(",")
                            .append(cb.getTitle()).append(",").append(cb.getPrice()).append(",")
                            .append(cb.getStock()).append(",").append(cb.getLength()).append(",")
                            .append(cb.getConnectorType());
                } else if (acc instanceof Memory) {
                    Memory m = (Memory) acc;
                    sb.append("MEMORY,").append(m.getId()).append(",")
                            .append(m.getTitle()).append(",").append(m.getPrice()).append(",")
                            .append(m.getStock()).append(",").append(m.getCapacity()).append(",")
                            .append(m.getMemoryType());
                    if (m.getCompatibleConsoleIds() != null) {
                        for (String console : m.getCompatibleConsoleIds()) {
                            sb.append(",").append(console);
                        }
                    }
                }
                writer.println(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}