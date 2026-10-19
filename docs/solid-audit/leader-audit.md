# SOLID Principles Audit Report - Technical Leader

**Author:** Miguel Vasquez (Technical Leader)  
**Repository:** GamerZoneUnicesar  
**Branch Audited:** `develop`  
**Target File:** `docs/solid-audit/leader-audit.md`

---

## 1. Scope Section

This audit covers the source code modules, requirements, and classes under the direct responsibility of the Technical Leader (Miguel Vasquez), including core domain models, execution bootstrap classes, service orchestrations, and UI integration entry points.

| Module / Area | Requirement | Audited Classes & Files | Audited Methods / Methods Scope |
| :--- | :--- | :--- | :--- |
| **Domain & Core Models** | Workshop 1 & Req 2 | [`Sale.java`](https://github.com/MiguelVasquezA/GamerZoneUnicesar/blob/main/src/main/java/com/gamezone/model/Sale.java) | `Sale(...)`, `addItem()`, `calculateTotal()`, getters/setters |
| **Sales & Business Services** | Workshop 1, Req 1, 2, 4 | [`SaleService.java`](https://github.com/MiguelVasquezA/GamerZoneUnicesar/blob/main/src/main/java/com/gamezone/service/SaleService.java) | `registerSale()`, `getSales()`, `getSaleById()`, `calculateTotalWithPromotions()` |
| **UI & Console Interface** | Workshop 1, Req 1, 2, 3, 4, 6 | [`ConsoleMenu.java`](https://github.com/MiguelVasquezA/GamerZoneUnicesar/blob/main/src/main/java/com/gamezone/ui/ConsoleMenu.java) | `start()`, `handleOption()`, `registerSale()`, `registerReturn()`, exception blocks |
| **Application Bootstrapper** | Workshop 1 & Req 7 | [`Main.java`](https://github.com/MiguelVasquezA/GamerZoneUnicesar/blob/main/src/main/java/com/gamezone/ui/Main.java) | `main(String[] args)` |

---

## 2. Single Responsibility Principle (SRP)

* **Question Answered:** *Does this class have only one reason to change?*

### 2.1 Evaluation Summary
* **[`Sale.java`](https://github.com/MiguelVasquezA/GamerZoneUnicesar/blob/main/src/main/java/com/gamezone/model/Sale.java):** **CORRECT.** Following GRASP refactoring, the receipt string generation was delegated to `ReceiptFormatter`. `Sale` holds state and calculates item totals.
* **[`SaleService.java`](https://github.com/MiguelVasquezA/GamerZoneUnicesar/blob/main/src/main/java/com/gamezone/service/SaleService.java):** **VIOLATION.** Violates SRP because it coordinates sale registration while directly calculating promotional discounts, extended warranty surcharges, and stock validations.
* **[`ConsoleMenu.java`](https://github.com/MiguelVasquezA/GamerZoneUnicesar/blob/main/src/main/java/com/gamezone/ui/ConsoleMenu.java):** **VIOLATION.** Violates SRP because it manages terminal user input/output while orchestrating complex business UI workflows and formatting error messages.

---

## 3. Open/Closed Principle (OCP)

* **Question Answered:** *Can a new variant be added without modifying code that already works?*

### 3.1 Evaluation Summary
* **[`SaleService.java`](https://github.com/MiguelVasquezA/GamerZoneUnicesar/blob/main/src/main/java/com/gamezone/service/SaleService.java):** **VIOLATION.** Hardcodes discount calculation logic and warranty additions directly inside methods. Adding a new promotional strategy requires modifying `SaleService`.
* **[`ConsoleMenu.java`](https://github.com/MiguelVasquezA/GamerZoneUnicesar/blob/main/src/main/java/com/gamezone/ui/ConsoleMenu.java):** **VIOLATION.** Uses fixed `switch-case` constructs for menu choices. Adding a new menu feature requires modifying existing `handleOption` control flow.

---

## 4. Liskov Substitution Principle (LSP)

* **Question Answered:** *Can each subclass be used in place of its base class without altering expected behavior?*

### 4.1 Evaluation Summary
* **Verdict:** **NOT APPLICABLE.**
* **Justification:** The core classes under the Technical Leader's responsibility (`Sale`, `SaleService`, `ConsoleMenu`, `Main`) do not inherit from base classes or implement custom inheritance hierarchies. No behavioral subtyping contracts are overridden or violated.

---

## 5. Interface Segregation Principle (ISP)

* **Question Answered:** *Do clients depend only on the operations they actually use?*

### 5.1 Evaluation Summary
* **[`ConsoleMenu.java`](https://github.com/MiguelVasquezA/GamerZoneUnicesar/blob/main/src/main/java/com/gamezone/ui/ConsoleMenu.java):** **VIOLATION.** `ConsoleMenu` directly depends on monolithic concrete service classes (`SaleService`, `ProductService`, `AccessoryService`, `ReturnService`, `WarrantyService`) instead of narrow, role-specific interfaces.

---

## 6. Dependency Inversion Principle (DIP)

* **Question Answered:** *Do high-level modules depend on abstractions, and not on concrete implementations?*

### 6.1 Evaluation Summary
* **[`SaleService.java`](https://github.com/MiguelVasquezA/GamerZoneUnicesar/blob/main/src/main/java/com/gamezone/service/SaleService.java):** **VIOLATION.** High-level business logic in `SaleService` directly instantiates or depends on concrete persistence classes (e.g., `SaleRepository`) and concrete services instead of interface abstractions.
* **[`ConsoleMenu.java`](https://github.com/MiguelVasquezA/GamerZoneUnicesar/blob/main/src/main/java/com/gamezone/ui/ConsoleMenu.java):** **VIOLATION.** The UI controller depends directly on concrete implementations of services rather than service interfaces.

---

## 7. Consolidated Violations Table

| ID | Principle | Module / Class | Evidence (Lines / Methods) | Explanation | Consequence | Proposed Solution | Code of Another Member |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **V-01** | **SRP** | `SaleService` | `registerSale()`, lines 45-80 | `SaleService` handles sale validation, discount calculation, extended warranty pricing, and persistence triggering in one method. | Any change in promotion