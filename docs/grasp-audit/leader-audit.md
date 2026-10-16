# Technical Leader - Individual GRASP Audit Report

## Audit Scope
* **Auditor:** Miguel Angel Vasquez Aguirre (Technical Leader)
* **Audited Classes:**
  * `com.gamezone.model.Sale`
  * `com.gamezone.persistence.SaleRepository`
  * `com.gamezone.service.SaleService`
  * `com.gamezone.ui.ConsoleMenu`
  * `com.gamezone.ui.Main`

---

## 1. Information Expert
* **Verdict:** Violation

### Violation V-01
* **Pattern:** Information Expert
* **Module:** Sales / Requirement 1 & Requirement 4
* **Evidence:** `com.gamezone.model.Sale` (constructor lines 43-48 and `calculateTotal()` method lines 67-75).
* **Explanation:** `Sale` receives precalculated total amounts (`totalAmount`) and discount amounts (`discountAmount`) injected directly into its constructor. Although `Sale` holds the collection of `Product` items, calculating business discounts and net totals is orchestrated outside the domain model inside `SaleService`.
* **Consequence:** If discount rules change, modifications are forced upon application services instead of encapsulating pricing logic inside the entity holding the sales data.
* **Proposed Solution:** Refactor `Sale` to compute its own subtotal and net amount, exposing methods like `applyDiscount()` or `calculateFinalTotal()`.
* **Code from another member:** No

---

## 2. Creator
* **Verdict:** Violation

### Violation V-02
* **Pattern:** Creator
* **Module:** Sales / Requirement 1
* **Evidence:** `com.gamezone.model.Sale` constructor (line 43) and `setProducts()` (line 143).
* **Explanation:** `Sale` encapsulates an aggregation of `Product` items. However, the complete `List<Product>` is constructed externally in `ConsoleMenu` / `SaleService` and injected whole, preventing `Sale` from controlling its item aggregation lifecycle.
* **Consequence:** `Sale` cannot validate or enforce constraints when products are added individually during a sale transaction.
* **Proposed Solution:** Encapsulate product addition inside `Sale` via an `addProduct(Product product)` method.
* **Code from another member:** No

---

## 3. Controller
* **Verdict:** Violation

### Violation V-03
* **Pattern:** Controller
* **Module:** View / Requirement 1
* **Evidence:** `com.gamezone.ui.ConsoleMenu` (lines 80–180).
* **Explanation:** `ConsoleMenu` acts as a monolithic UI handler that receives user input, validates system entries, prompts for interactive feedback, and directly coordinates workflows for sales, returns, and products instead of delegating system events to specialized application controllers.
* **Consequence:** High coupling between user interface logic and application orchestration, making it difficult to replace the console menu with a Web or GUI interface.
* **Proposed Solution:** Refactor `ConsoleMenu` to act purely as a view/presentation component and delegate system actions to domain services or dedicated controller handlers.
* **Code from another member:** No

---

## 4. Low Coupling
* **Verdict:** Correct
* **Evidence:** `com.gamezone.model.Sale` imports and structural field definitions.
* **Explanation:** `Sale` depends only on Java standard types (`java.util.List`, `java.io.Serializable`) and the `Product` entity. It maintains no references to persistence or UI layers, keeping its coupling minimal and appropriate for a domain entity.

---

## 5. High Cohesion
* **Verdict:** Violation

### Violation V-04
* **Pattern:** High Cohesion
* **Module:** Sales / Requirement 1
* **Evidence:** `com.gamezone.model.Sale.generateReceipt()` (line 175).
* **Explanation:** `Sale` contains formatting logic to construct plain-text receipts with decorative console borders (`=========================================`). This mixes core domain state representation with presentation concerns.
* **Consequence:** Altering receipt formatting (e.g., printing as PDF or HTML) requires modifying domain entity source code.
* **Proposed Solution:** Extract formatting logic into a dedicated view formatter or receipt printer class (`ReceiptFormatter`).
* **Code from another member:** No

---

## 6. Polymorphism
* **Verdict:** Not Applicable
* **Justification:** The audited sales domain classes do not require handling pluggable behavioral variants or type-based conditional branching in the current system design.

---

## 7. Pure Fabrication
* **Verdict:** Violation

### Violation V-05
* **Pattern:** Pure Fabrication
* **Module:** View & Services / Requirement 1
* **Evidence:** Lack of specialized UI helper fabrications and input parsers in `com.gamezone.ui`.
* **Explanation:** `ConsoleMenu` directly parses string inputs, manages loop controls, and handles error presentation without utilizing artificial fabrication classes designed to cleanly segregate console parsing from workflow management.
* **Consequence:** Monolithic UI classes with inflated responsibilities.
* **Proposed Solution:** Introduce pure fabrications (e.g., `ConsoleInputReader` or `ConsolePrinter`) to handle user input parsing cleanly.
* **Code from another member:** No

---

## 8. Indirection
* **Verdict:** Violation

### Violation V-06
* **Pattern:** Indirection & Low Coupling
* **Module:** Persistence / Requirement 1
* **Evidence:** Direct usage of concrete `SaleRepository` inside `SaleService` (lines 20-30) and `com.gamezone.ui.Main`.
* **Explanation:** `SaleService` depends directly on the concrete file-serialization implementation `SaleRepository` rather than referencing an abstraction or interface.
* **Consequence:** Replacing the file storage mechanism with a relational database or memory repository forces modifications in `SaleService`.
* **Proposed Solution:** Introduce an `ISaleRepository` interface to decouple service logic from persistence mechanisms.
* **Code from another member:** No

---

## 9. Protected Variations
* **Verdict:** Violation

### Violation V-07
* **Pattern:** Protected Variations
* **Module:** Sales / Requirement 1
* **Evidence:** `private List<Product> products;` in `com.gamezone.model.Sale` (line 23).
* **Explanation:** `Sale` references `Product` directly in a list. It lacks an intermediary value object representing a line item (quantity, historical price locked at the time of purchase).
* **Consequence:** Future changes to product unit prices corrupt historical sale records, and item quantities per sale line cannot be tracked cleanly.
* **Proposed Solution:** Encapsulate sold items using a `SaleItem` line item wrapper to protect `Sale` against product attribute variations over time.
* **Code from another member:** No