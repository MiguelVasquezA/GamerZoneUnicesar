# Team Consolidated GRASP Audit Report

## 1. Audit Reference & Metadata
* **Project Name:** GamerZone Unicesar
* **Repository:** MiguelVasquezA/GamerZoneUnicesar
* **Consolidation Commit Hash:** `develop` branch HEAD
* **Technical Leader:** Miguel Angel Vasquez Aguirre
* **Team Members:** Technical Leader, Developer 1, Developer 2

---

## 2. Code Coverage Mapping Table

| Class / Package | Primary Module | Assigned Auditor | Audited |
| :--- | :--- | :--- | :---: |
| `com.gamezone.model.Sale` | Sales | Technical Leader | Yes |
| `com.gamezone.persistence.SaleRepository` | Sales Persistence | Technical Leader | Yes |
| `com.gamezone.service.SaleService` | Sales Service | Technical Leader | Yes |
| `com.gamezone.ui.ConsoleMenu` | Presentation Layer | Technical Leader | Yes |
| `com.gamezone.ui.Main` | Application Entry | Technical Leader | Yes |
| `com.gamezone.model.Product` | Products Domain | Developer 1 | Yes |
| `com.gamezone.model.VideoGame` | Products Domain | Developer 1 | Yes |
| `com.gamezone.model.Console` | Products Domain | Developer 1 | Yes |
| `com.gamezone.model.Accessory` | Accessories Domain | Developer 1 | Yes |
| `com.gamezone.model.Controller` | Accessories Domain | Developer 1 | Yes |
| `com.gamezone.model.Cable` | Accessories Domain | Developer 1 | Yes |
| `com.gamezone.model.Memory` | Accessories Domain | Developer 1 | Yes |
| `com.gamezone.persistence.ProductRepository` | Products Persistence | Developer 1 | Yes |
| `com.gamezone.service.ProductService` | Products Service | Developer 1 | Yes |

---

## 3. Team Overall GRASP Verdict Summary

| GRASP Pattern | Overall Verdict | Total Violations Found |
| :--- | :---: | :---: |
| Information Expert | Violation | 2 |
| Creator | Violation | 2 |
| Controller | Violation | 1 |
| Low Coupling | Violation | 2 |
| High Cohesion | Violation | 2 |
| Polymorphism | Violation | 1 |
| Pure Fabrication | Violation | 1 |
| Indirection | Violation | 2 |
| Protected Variations | Violation | 2 |

---

## 4. Consolidated Violations List

### Violation V-01
* **Pattern:** Information Expert
* **Source Report:** `leader-audit.md` (V-01)
* **Location:** `com.gamezone.model.Sale` (constructor & `calculateTotal()`)
* **Description:** Precalculated total amounts and discounts are injected into `Sale` rather than having `Sale` compute its net total.
* **Assigned Refactorer:** Technical Leader

### Violation V-02
* **Pattern:** Creator
* **Source Report:** `leader-audit.md` (V-02)
* **Location:** `com.gamezone.model.Sale` (constructor receiving `List<Product>`)
* **Description:** External classes instantiate product collections and inject them whole instead of delegating item aggregation control to `Sale`.
* **Assigned Refactorer:** Technical Leader

### Violation V-03
* **Pattern:** Controller
* **Source Report:** `leader-audit.md` (V-03)
* **Location:** `com.gamezone.ui.ConsoleMenu`
* **Description:** `ConsoleMenu` directly handles UI loops, input parsing, error displays, and system orchestration.
* **Assigned Refactorer:** Technical Leader

### Violation V-04
* **Pattern:** High Cohesion
* **Source Report:** `leader-audit.md` (V-04)
* **Location:** `com.gamezone.model.Sale.generateReceipt()`
* **Description:** `Sale` contains console formatting logic for printing receipts.
* **Assigned Refactorer:** Technical Leader

### Violation V-05
* **Pattern:** Indirection & Low Coupling
* **Source Report:** `leader-audit.md` (V-06)
* **Location:** `com.gamezone.service.SaleService` & `com.gamezone.persistence.SaleRepository`
* **Description:** `SaleService` depends directly on concrete `SaleRepository` instead of a repository interface.
* **Assigned Refactorer:** Technical Leader

### Violation V-06
* **Pattern:** Protected Variations
* **Source Report:** `leader-audit.md` (V-07)
* **Location:** `com.gamezone.model.Sale` (`List<Product> products`)
* **Description:** `Sale` stores direct references to `Product` without line item value objects (`SaleItem`) capturing locked historical prices.
* **Assigned Refactorer:** Technical Leader

### Violation V-07
* **Pattern:** Low Coupling & Indirection
* **Source Report:** `developer1-audit.md` (V-DEV1-02, V-DEV1-05)
* **Location:** `com.gamezone.service.ProductService`
* **Description:** `ProductService` directly depends on concrete file persistence `ProductRepository`.
* **Assigned Refactorer:** Developer 1

### Violation V-08
* **Pattern:** High Cohesion & Polymorphism
* **Source Report:** `developer1-audit.md` (V-DEV1-03, V-DEV1-04)
* **Location:** `com.gamezone.persistence.ProductRepository`
* **Description:** Uses `instanceof` checks and inline parsing to handle product subtype serialization.
* **Assigned Refactorer:** Developer 1

---

## 5. Border Findings Between Members
* **Finding:** `ConsoleMenu` (Technical Leader) directly instantiates and calls methods from `ProductService` (Developer 1) without abstraction boundaries.
* **Resolution:** Consolidated under `V-03`. The Technical Leader will refactor `ConsoleMenu` to interact with service interfaces, while Developer 1 exposes clean service boundaries.

---

## 6. Out-of-Scope Findings (Functional Defects)
* **Defect 1:** Product price formatting does not restrict negative values during manual input.
* **Note:** Per Requirement 7 guidelines, functional bug fixes that alter user-observed behaviors are out of scope for Phase 2 refactoring to guarantee non-regression.