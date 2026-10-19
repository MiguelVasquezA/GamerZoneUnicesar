# Developer 2 - Individual GRASP Audit Report

## 1. Audit Scope

This audit covers the code developed under the responsibility of Developer 2 for the **GameZone Unicesar** system, organized by module and requirement:

- **Persons Module (Workshop 1):** `Person`, `Customer`, `Seller`, `PersonFileHandler`, `PersonService`.
- **Accessories Module (Requirement 1):** `AccessoryRepository`, `AccessoryService`.
- **Promotions Module (Requirement 2):** `PromotionRepository`, `PromotionService`.
- **Returns Module (Requirement 3):** `ReturnRepository`, `ReturnService` (including monthly balance generation).
- **Warranties Module (Requirement 4):** `WarrantyRepository`, `WarrantyService`.
- **Cross-cutting Concerns:** Use of exceptions and validators in services and repositories developed by Developer 2.

---

## 2. Evaluation of GRASP Patterns

### 1. Creator

- **Verdict:** Correct (`correct`)
- **Evidence:**
    - `AccessoryRepository.loadAll()`, `PromotionRepository.loadAll()`, and `WarrantyRepository.loadAll(...)` instantiate concrete subclasses (`Controller`, `Cable`, `Memory`, `PercentageDiscount`, `CategoryDiscount`, `BulkPurchaseDiscount`, `BasicWarranty`, `ExtendedWarranty`) based on the type discriminator read from their respective CSV files.
    - The creation of specific store user instances (`Customer` and `Seller`) is delegated to upper layers when user input is captured, keeping domain classes decoupled from heavy external dependencies.
- **Explanation:** The repository classes contain the raw data records and the context needed to determine which specialized subclass to construct, aligning correctly with the Creator pattern.
- **Consequence:** High cohesion in object creation and clear separation between data storage formats and domain logic.

### 2. Information Expert

- **Verdict:** Correct (`correct`)
- **Evidence:**
    - `Customer` and `Seller` contain and manage their own specific attributes (`email`, `purchaseHistory`, `employeeCode`, `workShift`) and implement `getDescription()` because they possess the exact data required.
    - `PromotionService.findBestPromotionFor(Sale sale)` queries active promotions and delegates the calculation logic directly to the promotion instances via `promo.calculateDiscount(sale)`.
    - `AccessoryService` manages its internal list of accessories and handles search, stock updates, and reductions.
- **Explanation:** Responsibilities are assigned to the classes that have the necessary state and information to fulfill them (e.g., domain models calculating their specific behaviors).
- **Consequence:** Encapsulation of domain logic within domain classes, preventing anemic models and making the system easily extensible.

### 3. Low Coupling

- **Verdict:** Violation (`violation`) / Correct in Persons and Promotions Modules
- **ID:** `V-ACC-01`
- **Module:** Returns (Requirement 3) / `ReturnService.java`
- **Evidence:** In `ReturnService.registerReturn()`, lines checking inventory routing:

    ```java
    if (accessoryService.findById(product.getId()) != null) {
        accessoryService.restoreStock(product.getId(), 1);
    } else {
        productService.updateStock(product.getId(), product.getStock() + 1);
    }
    ```

- **Explanation:** While the Persons and Promotions modules maintain low coupling (separating `PersonService` via `PersonFileHandler` and `PromotionService` via its repository), `ReturnService` explicitly needs to know about and manage the separation between `AccessoryService` and `ProductService` to restore inventory, increasing structural coupling.
- **Consequence:** If a new type of inventory item is introduced later, `ReturnService` must be modified to add another conditional check.
- **Solution Proposal (Pure Fabrication / Polymorphism):** Introduce a unified inventory management interface or handle stock restoration polymorphically through a common product/item abstraction so services don't need type-checking conditionals.
- **Other Integrant Code:** No (`false`).

### 4. High Cohesion

- **Verdict:** Correct (`correct`)
- **Evidence:**
    - Every class has a single, well-defined responsibility: `Person` handles identity attributes; `Customer` and `Seller` manage role data; `PersonFileHandler` handles serialization.
    - Services (`AccessoryService`, `PromotionService`, `ReturnService`, `WarrantyService`) focus strictly on business logic coordination, while repositories (`AccessoryRepository`, `PromotionRepository`, `ReturnRepository`, `WarrantyRepository`) focus solely on file I/O operations and CSV parsing.
- **Explanation:** Responsibilities are cleanly divided into distinct packages and layers.
- **Consequence:** Improved maintainability, readability, and ease of unit testing.

### 5. Polymorphism

- **Verdict:** Correct (`correct`)
- **Evidence:**
    - The abstract method `getDescription()` declared in `Person` is overridden polymorphically in `Customer` and `Seller`.
    - `PromotionService` evaluates different discount types (`PercentageDiscount`, `CategoryDiscount`, `BulkPurchaseDiscount`) uniformly through the polymorphic method `calculateDiscount(Sale sale)`.
- **Explanation:** Behavior varies depending on the type of object without requiring explicit `instanceof` checks or type-switching conditionals.
- **Consequence:** Respects the Open/Closed Principle (OCP); new types or promotion rules can be added without modifying the coordination services.

### 6. Pure Fabrication

- **Verdict:** Correct (`correct`)
- **Evidence:** `PersonFileHandler`, `AccessoryRepository`, `PromotionRepository`, `ReturnRepository`, and `WarrantyRepository` act as pure fabrications to handle data persistence to files and CSV storage, keeping this infrastructure responsibility out of domain model classes.
- **Explanation:** These utility-like classes are introduced to achieve low coupling and high cohesion where domain classes cannot naturally assume storage tasks.
- **Consequence:** Clean architecture separation and decoupling of business rules from persistence mechanisms.

### 7. Indirection

- **Verdict:** Correct (`correct`)
- **Evidence:**
    - `PersonService` provides indirection by decoupling the presentation layer from the persistence implementation (`PersonFileHandler`).
    - `WarrantyRepository.loadAll(ProductService productService, SaleRepository saleRepository)` uses injected service and repository references to resolve cross-object relationships by ID during CSV loading.
- **Explanation:** Direct circular dependencies between model objects and repositories are avoided by using intermediate service layers to resolve associations.
- **Consequence:** Loose coupling between persistent entities and safer object graph reconstruction.

### 8. Protected Variations

- **Verdict:** Protected (`correct`)
- **Evidence:**
    - The use of the abstract class `Person` protects the rest of the application from variations in specific types of users.
    - The `Promotion` hierarchy uses abstract methods and shared interfaces (`isActive`, `calculateDiscount`), protecting `PromotionService` from changes in how individual discount strategies compute their values.
- **Explanation:** Stable interfaces protect high-level modules from variations in specific subclass implementations.
- **Consequence:** The system is robust against future modifications in business policies and data structures.

### 9. Controller

- **Verdict:** Not Applicable (`not applicable`)
- **Evidence:** The classes developed under Developer 2 operate as domain services, models, and persistence handlers, not as entry points for handling raw console or UI events.
- **Explanation:** Event handling and menu coordination are handled by `ConsoleMenu` and `Main` (Líder Técnico), meaning there is no point of application for the Controller pattern in this specific scope.