# Accessory Module Architectural Analysis

This document addresses the five guiding analysis questions for integrating the new accessory module into the GameZone Unicesar system.

---

## 1. Product Hierarchy Integration

**Decision:** The `Accessory` class extends the existing `Product` class as an abstract subclass.

**Justification:**
* **Code Reuse:** Inheriting from `Product` allows `Accessory` to directly reuse common fields such as `id`, `title`, `price`, and `stockQuantity`, as well as standard getters and setters.
* **Polymorphism & Model Consistency:** An accessory is a sellable entity within the store. Extending `Product` allows accessories to be seamlessly stored in lists of type `List<Product>` across the sales module without requiring major rewrites of existing transactional logic.

---

## 2. Shared vs. Specific Attributes

### Common Attributes
* **Inherited from `Product`:** `id`, `title`, `price`, `stockQuantity`.
* **Shared by all `Accessory` types:** `compatibleConsoleIds` (a list of compatible console IDs).

### Specific Attributes
* **`Controller`:** `connectionType` (e.g., Wireless or Wired).
* **`Cable`:** `length` (in meters) and `connectorType` (e.g., HDMI, USB, Optical).
* **`Memory`:** `capacity` (in gigabytes) and `memoryType` (e.g., SD, microSD, Internal Card).

### Hierarchy Representation
* `Product` *(Abstract Superclass)*
    * `Accessory` *(Abstract Subclass extending Product)*
        * `Controller` *(Concrete Subclass extending Accessory)*
        * `Cable` *(Concrete Subclass extending Accessory)*
        * `Memory` *(Concrete Subclass extending Accessory)*

---

## 3. Console Compatibility Representation

**Design & Persistence Representation:**
* Compatibility is modeled as an attribute of the **`Accessory`** entity.
* The `Accessory` class holds a collection of compatible console IDs (e.g., `List<String> compatibleConsoleIds`).
* In the persistence layer (`accessories.csv`), compatibility is stored as a formatted string within the accessory record (e.g., a comma-separated list of console IDs inside the row).

**Relationship Direction:**
* It is a **unidirectional relationship** managed by `Accessory`. Consoles do not need to store references to accessories, keeping the existing `Console` entity decoupled and unchanged.

---

## 4. `SaleService` Modifications

To support sales containing both traditional products (consoles/games) and accessories without breaking existing behavior:

1. **Unified Sale Processing:** `SaleService.registerSale` should accept items typed as `Product` (or handle unified lists), allowing both standard products and accessories to be processed in the same transaction.
2. **Polymorphic Stock Validation & Calculation:** Standard rules for price calculation and stock checking apply uniformly via `Product` base methods.
3. **Delegated Inventory Update:** During sale confirmation, `SaleService` checks the runtime type of each product:
    * Standard products delegate stock reduction to `ProductService`.
    * Accessory products delegate stock reduction to `AccessoryService`.

---

## 5. Architectural Layer Placement

The new accessory classes are distributed strictly across the existing four-layer architecture:

* **`model` Package:** `Accessory` (abstract), `Controller`, `Cable`, and `Memory`.
    * *Responsibility:* Domain entities, attributes, encapsulations, and business data structure.
* **`persistence` Package:** `AccessoryRepository`.
    * *Responsibility:* Direct file I/O operations with `data/accessories.csv`, data parsing using discriminators, and persistence handling.
* **`service` Package:** `AccessoryService`.
    * *Responsibility:* Business logic, filtering by type/compatibility, inventory updates, and orchestration between repository and UI layers.
* **`ui` Package:** Extension of `ConsoleMenu`.
    * *Responsibility:* User interaction, input prompts, and display formatting in Spanish.

---