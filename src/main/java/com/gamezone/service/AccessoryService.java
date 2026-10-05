package com.gamezone.service;

import com.gamezone.model.Accessory;
import com.gamezone.persistence.AccessoryRepository;

import java.util.List;

/**
 * Service responsible for managing accessory inventory, stock updates,
 * reductions, and restorations during sales and returns.
 *
 * @author Desarrolladora 2
 * @version 1.0
 */
public class AccessoryService {

    private final AccessoryRepository repository;
    private final List<Accessory> accessories;

    /**
     * Constructs the AccessoryService and loads initial accessories from repository.
     *
     * @param repository the accessory persistence repository
     */
    public AccessoryService(AccessoryRepository repository) {
        this.repository = repository;
        this.accessories = repository.loadAll();
    }

    /**
     * Returns the full list of registered accessories.
     *
     * @return list of accessories
     */
    public List<Accessory> listAccessories() {
        return accessories;
    }

    /**
     * Finds an accessory by its unique identifier.
     *
     * @param id the accessory ID
     * @return the Accessory object, or null if not found
     */
    public Accessory findById(String id) {
        return accessories.stream()
                .filter(acc -> acc.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Updates the stock of a specific accessory and persists changes.
     *
     * @param id the accessory ID
     * @param newStock the new stock quantity
     */
    public void updateStock(String id, int newStock) {
        Accessory accessory = findById(id);
        if (accessory != null) {
            accessory.setStock(newStock);
            repository.saveAll(accessories);
        }
    }

    /**
     * Reduces the stock of an accessory by a given quantity and persists changes.
     *
     * @param id the accessory ID
     * @param quantity the quantity to subtract
     */
    public void reduceStock(String id, int quantity) {
        Accessory accessory = findById(id);
        if (accessory != null) {
            int updatedStock = Math.max(0, accessory.getStock() - quantity);
            accessory.setStock(updatedStock);
            repository.saveAll(accessories);
        }
    }

    /**
     * Restores stock quantity for an accessory when a return is processed.
     *
     * @param accessoryId ID of the accessory to replenish
     * @param quantity quantity to add back to stock
     * @return true if found and updated, false otherwise
     */
    public boolean restoreStock(String accessoryId, int quantity) {
        if (accessoryId == null || quantity <= 0) {
            return false;
        }
        Accessory accessory = findById(accessoryId);
        if (accessory != null) {
            accessory.setStock(accessory.getStock() + quantity);
            repository.saveAll(accessories);
            return true;
        }
        return false;
    }
}