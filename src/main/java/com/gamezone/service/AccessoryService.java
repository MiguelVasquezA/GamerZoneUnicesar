package com.gamezone.service;

import com.gamezone.model.Accessory;
import com.gamezone.model.Controller;
import com.gamezone.model.Cable;
import com.gamezone.model.Memory;
import com.gamezone.persistence.AccessoryRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing business logic related to accessories.
 */
public class AccessoryService {
    private AccessoryRepository repository;
    private List<Accessory> accessories;

    /**
     * Constructs the AccessoryService injecting the repository and loading data.
     * @param repository The accessory repository.
     */
    public AccessoryService(AccessoryRepository repository) {
        this.repository = repository;
        this.accessories = repository.loadAll();
    }

    /**
     * Registers and saves a new controller.
     */
    public void registerController(String id, double price, int stock, String title, List<String> compatibleConsoleIds, String connectionType) {
        Controller controller = new Controller(id, price, stock, title, compatibleConsoleIds, connectionType);
        accessories.add(controller);
        repository.saveAll(accessories);
    }

    /**
     * Registers and saves a new cable.
     */
    public void registerCable(String id, double price, int stock, String title, List<String> compatibleConsoleIds, String connectorType, double length) {
        Cable cable = new Cable(id, price, stock, title, compatibleConsoleIds, connectorType, length);
        accessories.add(cable);
        repository.saveAll(accessories);
    }

    /**
     * Registers and saves a new memory.
     */
    public void registerMemory(String id, double price, int stock, String title, List<String> compatibleConsoleIds, int capacity, String memoryType) {
        Memory memory = new Memory(id, price, stock, title, compatibleConsoleIds, capacity, memoryType);
        accessories.add(memory);
        repository.saveAll(accessories);
    }

    /**
     * Returns all registered accessories.
     */
    public List<Accessory> listAllAccessories() {
        return accessories;
    }

    /**
     * Filters accessories by their specific type class name.
     */
    public List<Accessory> listAccessoriesByType(String type) {
        return accessories.stream()
                .filter(a -> a.getClass().getSimpleName().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    /**
     * Finds accessories compatible with a specific console ID.
     */
    public List<Accessory> findAccessoriesCompatibleWith(String consoleId) {
        return accessories.stream()
                .filter(a -> a.getCompatibleConsoleIds() != null && a.getCompatibleConsoleIds().contains(consoleId))
                .collect(Collectors.toList());
    }

    /**
     * Finds an accessory by its unique identifier.
     */
    public Accessory findById(String id) {
        return accessories.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Updates the stock of an accessory.
     */
    public void updateStock(String accessoryId, int quantity) {
        Accessory acc = findById(accessoryId);
        if (acc != null) {
            acc.setStock(acc.getStock() - quantity);
            repository.saveAll(accessories);
        }
    }
}