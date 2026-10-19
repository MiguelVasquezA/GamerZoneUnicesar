# Developer 1 - Individual GRASP Audit Report

## Audit Scope
* **Auditor:** Developer 1
* **Audited Classes (18 Classes Total):**
    * **Products Domain:** `com.gamezone.model.Product`, `com.gamezone.model.VideoGame`, `com.gamezone.model.Console`
    * **Accessories Domain:** `com.gamezone.model.Accessory`, `com.gamezone.model.Controller`, `com.gamezone.model.Cable`, `com.gamezone.model.Memory`
    * **Promotions Domain:** `com.gamezone.model.Promotion`, `com.gamezone.model.PercentageDiscount`, `com.gamezone.model.CategoryDiscount`, `com.gamezone.model.BulkPurchaseDiscount`
    * **Warranties Domain:** `com.gamezone.model.Warranty`, `com.gamezone.model.BasicWarranty`, `com.gamezone.model.ExtendedWarranty`
    * **Persistence Layer:** `com.gamezone.persistence.ProductRepository`
    * **Service Layer:** `com.gamezone.service.ProductService`
    * **Validation & Exceptions:** `com.gamezone.validation.ProductValidator`, `com.gamezone.exceptions.InvalidProductDataException`

---

## 1. Information Expert
* **Verdict:** Violation

### Violation V-DEV1-01
* **Pattern:** Information Expert
* **Module:** Products & Service Layer
* **Evidence:** `com.gamezone.service.ProductService`
* **Explanation:** Stock validation and inventory decrement logic are executed inside `ProductService` instead of delegating state mutation directly to `Product`, which encapsulates the `stock` field.
* **Consequence:** Leaks domain business rules into service layers and weakens entity encapsulation.
* **Proposed Solution:** Delegate inventory modifications to domain methods like `Product.reduceStock(int quantity)`.
* **Code from another member:** No

### Violation V-DEV1-02
* **Pattern:** Information Expert
* **Module:** Promotions & Products
* **Evidence:** `com.gamezone.model.Promotion` and `com.gamezone.service.ProductService`
* **Explanation:** Promotional discount calculations are computed in service helper methods instead of invoking polymorphic discount methods on the concrete `Promotion` subclasses.
* **Consequence:** Adding new promotional rules requires editing service logic instead of encapsulating pricing formulas within promotion objects.
* **Proposed Solution:** Implement `Promotion.applyDiscount(double basePrice)` polymorphically.
* **Code from another member:** No

---

## 2. Creator
* **Verdict:** Correct
* **Evidence:** `com.gamezone.service.ProductService`
* **Explanation:** `ProductService` aggregates active products in memory and manages file storage synchronization, serving as the proper application creator for new product registrations.

---

## 3. Controller
* **Verdict:** Not Applicable
* **Justification:** Audited classes represent domain entities, services, persistence, and validations; none process UI presentation events directly.

---

## 4. Low Coupling
* **Verdict:** Violation

### Violation V-DEV1-03
* **Pattern:** Low Coupling
* **Module:** Persistence & Service
* **Evidence:** `com.gamezone.service.ProductService`
* **Explanation:** `ProductService` relies on a direct concrete reference to `ProductRepository` rather than an interface abstraction.
* **Consequence:** Any migration in storage technology (e.g., from CSV files to a SQL database) forces breaking changes in `ProductService`.
* **Proposed Solution:** Introduce an `IProductRepository` interface for dependency inversion.
* **Code from another member:** No

---

## 5. High Cohesion
* **Verdict:** Violation

### Violation V-DEV1-04
* **Pattern:** High Cohesion
* **Module:** Persistence Layer
* **Evidence:** `com.gamezone.persistence.ProductRepository`
* **Explanation:** `ProductRepository` mixes file I/O operations with line parsing and deserialization logic for `VideoGame`, `Console`, and `Accessory` instances.
* **Consequence:** Formatting changes in data storage force modifications directly inside the data access class.
* **Proposed Solution:** Separate line parsing responsibilities into a dedicated parser component.
* **Code from another member:** No

---

## 6. Polymorphism
* **Verdict:** Violation

### Violation V-DEV1-05
* **Pattern:** Polymorphism
* **Module:** Products, Accessories & Persistence
* **Evidence:** `com.gamezone.persistence.ProductRepository` and `com.gamezone.validation.ProductValidator`
* **Explanation:** Conditional `instanceof` checks are used to determine specific CSV serialization and data validation rules for `Console`, `VideoGame`, `Accessory`, or `ExtendedWarranty`.
* **Consequence:** Adding a new product or accessory type requires modifying conditional structures across repositories and validators.
* **Proposed Solution:** Declare abstract methods (e.g., `toCsvRecord()`) in base classes and override them polymorphically.
* **Code from another member:** No

---

## 7. Pure Fabrication
* **Verdict:** Correct
* **Evidence:** `com.gamezone.validation.ProductValidator`
* **Explanation:** `ProductValidator` is a pure fabrication created to extract cross-cutting validation rules from entities, maintaining entity cohesion.

---

## 8. Indirection
* **Verdict:** Violation

### Violation V-DEV1-06
* **Pattern:** Indirection
* **Module:** Persistence Layer
* **Evidence:** Absence of repository interfaces in `com.gamezone.persistence`.
* **Explanation:** Services consume concrete file storage repositories directly without an interface acting as a decoupling mediator.
* **Consequence:** Limits testability and prevents mocking data sources.
* **Proposed Solution:** Introduce `IProductRepository` as an interface bridge.
* **Code from another member:** No

---

## 9. Protected Variations
* **Verdict:** Violation

### Violation V-DEV1-07
* **Pattern:** Protected Variations
* **Module:** Products & Sales Integration
* **Evidence:** Direct unit price modifications on `Product` entities.
* **Explanation:** Product prices can be altered without locking in unit price snapshots for past sales transactions.
* **Consequence:** Updating a catalog price retroactively alters amounts in historical transaction reports.
* **Proposed Solution:** Encapsulate pricing history using `SaleItem` snapshotting upon sale creation.
* **Code from another member:** Yes (Involves `SaleItem` managed by Technical Leader)