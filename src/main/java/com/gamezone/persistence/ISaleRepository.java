package com.gamezone.persistence;

import com.gamezone.model.Sale;
import java.util.List;
import java.util.Optional;

/**
 * Abstraction for sales persistence operations to decouple the service layer from concrete storage.
 * Implements Low Coupling and Indirection GRASP patterns.
 */
public interface ISaleRepository {
    /**
     * Persists a sale transaction.
     *
     * @param sale the sale entity to save
     */
    void save(Sale sale);

    /**
     * Retrieves all persisted sale transactions.
     *
     * @return a list of all sales
     */
    List<Sale> findAll();

    /**
     * Finds a sale transaction by its unique identifier.
     *
     * @param id the sale ID
     * @return an Optional containing the sale if found, or empty otherwise
     */
    Optional<Sale> findById(String id);
}
