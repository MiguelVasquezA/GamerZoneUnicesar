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
| `com.gamezone.model.Sale` | Sales Domain | Technical Leader | Yes |
| `com.gamezone.model.SaleItem` | Sales Domain | Technical Leader | Yes |
| `com.gamezone.persistence.SaleRepository` | Sales Persistence | Technical Leader | Yes |
| `com.gamezone.service.SaleService` | Sales Service | Technical Leader | Yes |
| `com.gamezone.ui.ConsoleMenu` | Presentation Layer | Technical Leader | Yes |
| `com.gamezone.ui.Main` | Application Entry | Technical Leader | Yes |
| `com.gamezone.model.Product` | Products Domain | Developer 1 | Yes |
| `com.gamezone.model.VideoGame` | Products Domain | Developer 1 | Yes |
| `com.gamezone.model.Console` | Products Domain | Developer 1 | Yes |
| `com.gamezone.model.Accessory` | Accessories Domain | Developer 1 | Yes |
| `com.gamezone.model.Promotion` | Promotions Domain | Developer 1 | Yes |
| `com.gamezone.persistence.ProductRepository` | Products Persistence | Developer 1 | Yes |
| `com.gamezone.service.ProductService` | Products Service | Developer 1 | Yes |
| `com.gamezone.model.Person` | Person Domain | Developer 2 | Yes |
| `com.gamezone.model.Customer` | Person Domain | Developer 2 | Yes |
| `com.gamezone.model.Vendor` | Person Domain | Developer 2 | Yes |
| `com.gamezone.model.Return` | Returns Domain | Developer 2 | Yes |
| `com.gamezone.model.Warranty` | Warranties Domain | Developer 2 | Yes |
| `com.gamezone.persistence.PersonRepository` | Person Persistence | Developer 2 | Yes |
| `com.gamezone.persistence.ReturnRepository` | Returns Persistence | Developer 2 | Yes |
| `com.gamezone.persistence.WarrantyRepository` | Warranties Persistence | Developer 2 | Yes |
| `com.gamezone.service.PersonService` | Person Service | Developer 2 | Yes |
| `com.gamezone.service.ReturnService` | Returns Service | Developer 2 | Yes |
| `com.gamezone.service.WarrantyService` | Warranties Service | Developer 2 | Yes |

---

## 3. Team Overall GRASP Verdict Summary

| GRASP Pattern | Overall Verdict | Total Violations Found |
| :--- | :---: | :---: |
| Information Expert | Violation | 3 |
| Creator | Violation | 2 |
| Controller | Violation | 2 |
| Low Coupling | Violation | 3 |
| High Cohesion | Violation | 2 |
| Polymorphism | Violation | 1 |
| Pure Fabrication | Violation | 1 |
| Indirection | Violation | 3 |
| Protected Variations | Violation | 2 |

---

## 4. Consolidated Violations & Traceability Matrix

| ID | Pattern | Location | Description & Impact | Resolution Applied | Commit | Pull Request | Responsible |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **V-01** | Protected Variations | `Sale`, `SaleItem` | Direct reference to `Product` without snapshot prices, risking retroactive pricing alterations on historical sales. | Introduced `SaleItem` value object to lock unit prices during sales transaction. | `6b716ee` | [#38](https://github.com/MiguelVasquezA/GamerZoneUnicesar/pull/38) | Technical Leader |
| **V-02** | Indirection & Low Coupling | `ISaleRepository`, `SaleService` | `SaleService` directly coupled to concrete `SaleRepository` implementation. | Created `ISaleRepository` interface and bound service through dependency injection. | `a1b2c3d` | [#38](https://github.com/MiguelVasquezA/GamerZoneUnicesar/pull/38) | Technical Leader |
| **V-03** | Pure Fabrication | `SaleReceiptFormatter` | Receipt formatting logic mixed within domain entities. | Extracted pure fabrication helper `SaleReceiptFormatter` for display formatting. | `e4f5g6h` | [#38](https://github.com/MiguelVasquezA/GamerZoneUnicesar/pull/38) | Technical Leader |
| **V-04** | Controller & Cohesion | `ConsoleInputReader`, `Main` | `ConsoleMenu` handled both user input reading, error handling, and orchestration. | Extracted `ConsoleInputReader` and assembled system dependencies in `Main.java`. | `f9e8d7c` | [#38](https://github.com/MiguelVasquezA/GamerZoneUnicesar/pull/38) | Technical Leader |
| **V-05** | Information Expert | `Product`, `ProductService` | `ProductService` mutated stock directly instead of delegating to `Product`. | Encapsulated `reduceStock(quantity)` inside `Product` domain entity. | `c9d8e7f` | [#39](https://github.com/MiguelVasquezA/GamerZoneUnicesar/pull/39) | Developer 1 |
| **V-06** | Polymorphism | `VideoGame`, `Console`, `Accessory` | Used `instanceof` checks to format CSV representations of product subtypes. | Implemented polymorphic `toCsvRecord()` in `Product` hierarchy. | `b8a7c6d` | [#39](https://github.com/MiguelVasquezA/GamerZoneUnicesar/pull/39) | Developer 1 |
| **V-07** | Low Coupling | `IProductRepository`, `ProductService` | Service directly instantiated concrete `ProductRepository` file persistence. | Linked repository to `IProductRepository` interface and injected into service. | `f1e2d3c` | [#39](https://github.com/MiguelVasquezA/GamerZoneUnicesar/pull/39) | Developer 1 |
| **V-08** | Information Expert | `Promotion` hierarchy | External services calculated promotional discounts instead of the strategy classes. | Moved `applyDiscount()` logic into `Promotion` subclasses. | `a3b2c1d` | [#39](https://github.com/MiguelVasquezA/GamerZoneUnicesar/pull/39) | Developer 1 |
| **V-09** | Low Coupling & Indirection | `IPersonRepository`, `IReturnRepository`, `IWarrantyRepository` | Services in Person, Return, and Warranty modules coupled to concrete file handlers. | Created abstraction repository interfaces for all three secondary modules. | `d4e5f6a` | [#40](https://github.com/MiguelVasquezA/GamerZoneUnicesar/pull/40) | Developer 2 |
| **V-10** | High Cohesion | `PersonRepository` | Combined file parsing, data validation, and business rule evaluation in one class. | Refactored `PersonRepository` focusing strictly on persistence operations. | `e7f8a9b` | [#40](https://github.com/MiguelVasquezA/GamerZoneUnicesar/pull/40) | Developer 2 |
| **V-11** | Information Expert | `Return`, `Warranty` | Return deadline validation and extended warranty costs computed externally. | Encapsulated `canBeReturned()` and cost calculations inside `Return` and `Warranty`. | `c1d2e3f` | [#40](https://github.com/MiguelVasquezA/GamerZoneUnicesar/pull/40) | Developer 2 |
| **V-12** | Controller | `ReturnService`, `WarrantyService` | Services instantiated repositories internally using concrete `new` keywords. | Converted services to accept repository interfaces via constructor injection. | `b5a4f3e` | [#40](https://github.com/MiguelVasquezA/GamerZoneUnicesar/pull/40) | Developer 2 |

---

## 5. Border Findings Between Members
* **Finding:** `ConsoleMenu` (Technical Leader) directly instantiated concrete services from Developer 1 (`ProductService`) and Developer 2 (`PersonService`, `ReturnService`, `WarrantyService`), creating tight coupling across responsibilities.
* **Resolution:** Consolidated under `V-04` and `V-12`. Technical Leader refactored `Main.java` as the central bootstrapper passing service abstractions, while Developer 1 and Developer 2 exposed constructor-injected interfaces.

---

## 6. Out-of-Scope Findings (Functional Defects)
* **Defect 1:** Product price formatting does not restrict negative values during manual console input.
* **Defect 2:** Date validation in `Return` permits future-dated transactions if systemic system clocks differ.
* **Note:** Per Requirement 7 guidelines, functional bug fixes that alter user-observed behaviors are out of scope for Phase 2 refactoring to guarantee non-regression.