# Developer 2 - Individual SOLID Audit Report

## 1. Audit Scope
This audit covers the code developed and maintained under the responsibility of Developer 2 for the **GameZone Unicesar** system, based on the current state of the `develop` branch (incorporating previous GRASP refactoring):
- **Persons Module:** `Person`, `Customer`, `Seller`, `PersonFileHandler`, `PersonService`.
- **Accessories Module:** `AccessoryRepository`, `AccessoryService`.
- **Promotions Module:** `PromotionRepository`, `PromotionService`.
- **Returns Module:** `ReturnRepository`, `ReturnService` (including monthly balance generation).
- **Warranties Module:** `WarrantyRepository`, `WarrantyService`.
- **Cross-cutting Concerns:** Validation and exception handling classes in services and repositories developed by Developer 2.

---

## 2. Evaluation of SOLID Principles

### **1. Single Responsibility Principle (SRP)**
- **Verdict:** Correct (`correct`)
- **Evidence:**
    - `PersonFileHandler` is strictly dedicated to object serialization and file I/O for person records, keeping it separate from business rules handled by `PersonService`.
    - `ReturnRepository` manages storage, whereas `ReturnService` orchestrates business validations and calculations (such as return time windows and monthly financial balance).
- **Explanation:** Each class possesses a single reason to change, cleanly isolating persistence concerns from business logic coordination.

### **2. Open/Closed Principle (OCP)**
- **Verdict:** Correct (`correct`)
- **Evidence:**
    - The promotions subsystem (`Promotion`, `PercentageDiscount`, `CategoryDiscount`, `BulkPurchaseDiscount`) allows new discount types to be introduced by extending the base class or implementing strategies without altering `PromotionService`.
- **Explanation:** The module is open for extension but closed for modification, relying heavily on polymorphism.

### **3. Liskov Substitution Principle (LSP)**
- **Verdict:** Correct (`correct`)
- **Evidence:**
    - Subclasses of `Person` (`Customer`, `Seller`) and promotion types properly override abstract contracts (such as `getDescription()` or `calculateDiscount()`) without throwing unexpected unsupported operations or violating base expectations.
- **Explanation:** Any derived class can be substituted wherever the base abstraction is expected without breaking application behavior.

### **4. Interface Segregation Principle (ISP)**
- **Verdict:** Not Applicable (`not applicable`)
- **Evidence:** The module architecture heavily relies on concrete repository classes and service abstractions communicating directly through domain models and file handlers rather than large bloated interfaces.
- **Explanation:** There are no oversized interfaces forcing client classes to depend on methods they do not use.

### **5. Dependency Inversion Principle (DIP)**
- **Verdict:** Correct (`correct`)
- **Evidence:**
    - Services such as `WarrantyService` and `ReturnService` depend on repository abstractions or clean constructor-injected components rather than instantiating low-level file writers directly.
- **Explanation:** High-level modules coordinate business behavior through abstractions, ensuring loose coupling across layers.