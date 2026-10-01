package com.gamezone.service;

import com.gamezone.model.Promotion;
import com.gamezone.model.PercentageDiscount;
import com.gamezone.model.CategoryDiscount;
import com.gamezone.model.BulkPurchaseDiscount;
import com.gamezone.model.Sale;
import com.gamezone.persistence.PromotionRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Service class for managing promotions business logic.
 */
public class PromotionService {
    private PromotionRepository repository;

    /**
     * Constructs a PromotionService injecting the repository.
     *
     * @param repository The promotion repository.
     */
    public PromotionService(PromotionRepository repository) {
        this.repository = repository;
    }

    /**
     * Registers a new percentage discount promotion.
     *
     * @param id          The promotion identifier.
     * @param name        The promotion name.
     * @param start       The start date.
     * @param end         The end date.
     * @param percentage  The percentage discount value.
     */
    public void registerPercentageDiscount(String id, String name, LocalDate start, LocalDate end, double percentage) {
        List<Promotion> promotions = repository.loadAll();
        promotions.add(new PercentageDiscount(id, name, start, end, percentage));
        repository.saveAll(promotions);
    }

    /**
     * Registers a new category discount promotion.
     *
     * @param id          The promotion identifier.
     * @param name        The promotion name.
     * @param start       The start date.
     * @param end         The end date.
     * @param percentage  The percentage discount value.
     * @param category    The target category.
     */
    public void registerCategoryDiscount(String id, String name, LocalDate start, LocalDate end, double percentage, String category) {
        List<Promotion> promotions = repository.loadAll();
        promotions.add(new CategoryDiscount(id, name, start, end, percentage, category));
        repository.saveAll(promotions);
    }

    /**
     * Registers a new bulk purchase discount promotion.
     *
     * @param id           The promotion identifier.
     * @param name         The promotion name.
     * @param start        The start date.
     * @param end          The end date.
     * @param percentage   The percentage discount value.
     * @param minQuantity  The minimum quantity required.
     */
    public void registerBulkPurchaseDiscount(String id, String name, LocalDate start, LocalDate end, double percentage, int minQuantity) {
        List<Promotion> promotions = repository.loadAll();
        promotions.add(new BulkPurchaseDiscount(id, name, start, end, percentage, minQuantity));
        repository.saveAll(promotions);
    }

    /**
     * Returns all registered promotions.
     *
     * @return List of all promotions.
     */
    public List<Promotion> listAllPromotions() {
        return repository.loadAll();
    }

    /**
     * Returns only the promotions active on the current date.
     *
     * @return List of active promotions.
     */
    public List<Promotion> listActivePromotions() {
        LocalDate today = LocalDate.now();
        return repository.loadAll().stream()
                .filter(p -> p.isActive(today))
                .collect(Collectors.toList());
    }

    /**
     * Finds a promotion by its identifier.
     *
     * @param id The promotion identifier to search.
     * @return The found promotion, or null if not found.
     */
    public Promotion findById(String id) {
        return repository.loadAll().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Evaluates active promotions for a sale and returns the one granting the highest discount.
     *
     * @param sale The sale to evaluate.
     * @return The best promotion, or null if none apply or max discount is zero.
     */
    public Promotion findBestPromotionFor(Sale sale) {
        List<Promotion> activePromotions = listActivePromotions();
        Promotion bestPromotion = null;
        double maxDiscount = 0.0;

        for (Promotion promo : activePromotions) {
            double currentDiscount = promo.calculateDiscount(sale);
            if (currentDiscount > maxDiscount) {
                maxDiscount = currentDiscount;
                bestPromotion = promo;
            }
        }

        return maxDiscount > 0 ? bestPromotion : null;
    }
}
