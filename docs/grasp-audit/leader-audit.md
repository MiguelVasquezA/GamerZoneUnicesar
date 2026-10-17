# Technical Leader - Individual GRASP Audit Report

## Audit Scope
* **Auditor:** Technical Leader (Miguel Angel Vasquez Aguirre)
* **Audited Classes (8 Classes Total):**
  * **Sales & Domain:** `com.gamezone.model.Sale`, `com.gamezone.model.SaleItem`, `com.gamezone.model.Customer`, `com.gamezone.model.Employee`
  * **Sales Persistence:** `com.gamezone.persistence.SaleRepository`
  * **Sales Service:** `com.gamezone.service.SaleService`
  * **Presentation Layer:** `com.gamezone.ui.ConsoleMenu`
  * **Application Entry:** `com.gamezone.ui.Main`

---

## 1. Information Expert
* **Verdict:** Violation

### Violation V-LEAD-01
* **Pattern:** Information Expert
* **Module:** Sales / Requirement 3
* **Evidence:** `com.gamezone.model.Sale` (constructor and `calculateTotal()` method)
* **Explanation:** `Sale` receives precalculated total and discount amounts from external callers during construction instead of taking responsibility for calculating its own net total from its collection of items.
* **Consequence:** Breaks encapsulation by placing domain pricing logic in external UI or service controllers rather than inside the `Sale` entity that holds the item data.
* **Proposed Solution:** Delegate total calculation internally to `Sale.calculateTotal()`, summing the subtotals of its `SaleItem` list.
* **Code from another member:** No

---

## 2. Creator
* **Verdict:** Violation

### Violation V-LEAD-02
* **Pattern:** Creator
* **Module:** Sales / Requirement 3
* **Evidence:** `com.gamezone.model.Sale`
* **Explanation:** Direct lists of `Product` instances created externally are passed into `Sale`. The `Sale` aggregate does not record or create its own `SaleItem` instances when products are added.
* **Consequence:** Coupling increases because callers must manage individual line items before creating a sale transaction.
* **Proposed Solution:** Make `Sale` the creator of `SaleItem` instances via an `addItem(Product product, int quantity)` method.
* **Code from another member:** Yes (Involves `Product` managed by Developer 1)

---

## 3. Controller
* **Verdict:** Violation

### Violation V-LEAD-03
* **Pattern:** Controller
* **Module:** Presentation / Integration
* **Evidence:** `com.gamezone.ui.ConsoleMenu`
* **Explanation:** `ConsoleMenu` directly handles console user input loops, formats text outputs, validates input constraints, and orchestrates domain service calls directly.
* **Consequence:** `ConsoleMenu` becomes a bloated "Bloated Controller" with poor cohesion and low reusability across different UI interfaces.
* **Proposed Solution:** Separate user interface presentation from system event handling by introducing dedicated UI handlers or application controllers.
* **Code from another member:** Yes (Involves `ProductService` managed by Developer 1 and `ReturnService` managed by Developer 2)

---

## 4. Low Coupling
* **Verdict:** Violation

### Violation V-LEAD-04
* **Pattern:** Low Coupling
* **Module:** Sales & Service Layer
* **Evidence:** `com.gamezone.service.SaleService` (direct field `private final SaleRepository repository;`)
* **Explanation:** `SaleService` is directly bound to the concrete file-based persistence class `SaleRepository` instead of depending on an abstraction.
* **Consequence:** Any change in file storage or migration to a relational database breaks `SaleService`.
* **Proposed Solution:** Introduce an `ISaleRepository` interface to decouple service logic from storage implementations.
* **Code from another member:** No

---

## 5. High Cohesion
* **Verdict:** Violation

### Violation V-LEAD-05
* **Pattern:** High Cohesion
* **Module:** Sales Domain
* **Evidence:** `com.gamezone.model.Sale.generateReceipt()`
* **Explanation:** `Sale` contains console-specific text formatting logic for printing receipts alongside its core business state.
* **Consequence:** Modifying receipt layout or output format forces changes inside the core domain model class.
* **Proposed Solution:** Extract receipt formatting responsibilities into a dedicated pure fabrication formatter (e.g., `ReceiptPrinter` or `SaleReceiptFormatter`).
* **Code from another member:** No

---

## 6. Polymorphism
* **Verdict:** Correct
* **Evidence:** `com.gamezone.model.Customer` and `com.gamezone.model.Employee` (overriding base behavior from `Person`).
* **Explanation:** Person sub-types polymorphically override identification and display methods without requiring conditional `instanceof` checks in the service layer.

---

## 7. Pure Fabrication
* **Verdict:** Violation

### Violation V-LEAD-06
* **Pattern:** Pure Fabrication
* **Module:** Presentation & Utility
* **Evidence:** Lack of UI input parsers and receipt formatters.
* **Explanation:** Business domain entities and UI classes perform auxiliary utility tasks (such as string formatting and input parsing) directly because no synthetic utility classes exist.
* **Consequence:** Lowers cohesion across domain and presentation classes.
* **Proposed Solution:** Fabricate utility classes like `InputConsoleReader` and `ReceiptFormatter`.
* **Code from another member:** No

---

## 8. Indirection
* **Verdict:** Violation

### Violation V-LEAD-07
* **Pattern:** Indirection
* **Module:** Sales Persistence
* **Evidence:** Absence of repository interfaces in `com.gamezone.persistence`.
* **Explanation:** `SaleService` communicates directly with concrete persistence without an interface mediator.
* **Consequence:** Limits unit testing capabilities and prevents mocking storage layers.
* **Proposed Solution:** Introduce `ISaleRepository` as an intermediary abstraction.
* **Code from another member:** No

---

## 9. Protected Variations
* **Verdict:** Violation

### Violation V-LEAD-08
* **Pattern:** Protected Variations
* **Module:** Sales & Products
* **Evidence:** `com.gamezone.model.Sale` referencing `List<Product>` directly without price snapshots.
* **Explanation:** `Sale` holds direct references to `Product`. If a product's price is modified in the catalog, historical sales records change retroactively.
* **Consequence:** Historical transaction totals and audit reports become inconsistent over time.
* **Proposed Solution:** Encapsulate historical transaction data using `SaleItem` to lock in unit prices at the moment of sale.
* **Code from another member:** Yes (Involves `Product` managed by Developer 1)