# Developer 1 - Individual GRASP Audit Report

## Audit Scope
* **Auditor:** Developer 1
* **Audited Classes:**
    * `com.gamezone.model.Product`
    * `com.gamezone.model.VideoGame`
    * `com.gamezone.model.Console`
    * `com.gamezone.model.Accessory`
    * `com.gamezone.model.Controller`
    * `com.gamezone.model.Cable`
    * `com.gamezone.model.Memory`
    * `com.gamezone.persistence.ProductRepository`
    * `com.gamezone.service.ProductService`

---

## 1. Information Expert
* **Verdict:** Violation

### Violation V-DEV1-01
* **Pattern:** Information Expert
* **Module:** Products / Requirement 1
* **Evidence:** `com.gamezone.service.ProductService`
* **Explanation:** Stock modification and validation logic is handled directly in `ProductService` instead of delegating state mutation (like stock reduction) to `Product`, which holds the `stock` attribute.
* **Consequence:** Business validation logic for inventory state is scattered in service methods instead of being encapsulated inside the entity.
* **Proposed Solution:** Encapsulate stock modifications within methods on `Product` (e.g., `reduceStock(int quantity)`).
* **Code from another member:** No

---

## 2. Creator
* **Verdict:** Correct
* **Evidence:** `com.gamezone.service.ProductService`
* **Explanation:** `ProductService` acts as a domain coordinator that aggregates products into memory and manages persistent storage, making it an appropriate place to handle product registration inputs.

---

## 3. Controller
* **Verdict:** Not Applicable
* **Justification:** The audited classes belong to domain entities, services, and persistence layers; none of them process UI input events directly.

---

## 4. Low Coupling
* **Verdict:** Violation

### Violation V-DEV1-02
* **Pattern:** Low Coupling
* **Module:** Products / Requirement 1
* **Evidence:** `com.gamezone.service.ProductService` (reference to `ProductRepository`).
* **Explanation:** `ProductService` depends directly on the concrete class `ProductRepository` instead of depending on an abstract repository interface.
* **Consequence:** Swapping file-based persistence for a relational database will force breaking changes inside `ProductService`.
* **Proposed Solution:** Introduce an `IProductRepository` interface for dependency inversion.
* **Code from another member:** No

---

## 5. High Cohesion
* **Verdict:** Violation

### Violation V-DEV1-03
* **Pattern:** High Cohesion
* **Module:** Persistence / Requirement 1
* **Evidence:** `com.gamezone.persistence.ProductRepository`
* **Explanation:** `ProductRepository` mixes file reading/writing responsibilities with parsing logic to convert CSV text lines into concrete instances (`VideoGame`, `Console`, `Accessory`).
* **Consequence:** Any change in file line formatting forces modifications in the repository persistence class.
* **Proposed Solution:** Delegate parsing and formatting responsibilities to dedicated mapper/formatter utility classes.
* **Code from another member:** No

---

## 6. Polymorphism
* **Verdict:** Violation

### Violation V-DEV1-04
* **Pattern:** Polymorphism
* **Module:** Products & Persistence / Requirement 1
* **Evidence:** `com.gamezone.persistence.ProductRepository` (conditional logic checking concrete types).
* **Explanation:** Conditional type checking (`instanceof`) is used to decide how to format or serialize `Console`, `VideoGame`, or `Accessory` objects into persistent lines.
* **Consequence:** Adding a new product type requires modifying conditional structures inside the persistence code.
* **Proposed Solution:** Declare polymorphic methods (e.g., `toCsvRecord()`) in `Product` and override them in subclasses.
* **Code from another member:** No

---

## 7. Pure Fabrication
* **Verdict:** Not Applicable
* **Justification:** All classes audited represent domain concepts or standard application components without requiring artificial fabrications in this module.

---

## 8. Indirection
* **Verdict:** Violation

### Violation V-DEV1-05
* **Pattern:** Indirection
* **Module:** Persistence / Requirement 1
* **Evidence:** Absence of repository interfaces in `com.gamezone.persistence`.
* **Explanation:** Services consume concrete persistence classes directly without an interface acting as a decoupling mediator.
* **Consequence:** Low testability and tight coupling between domain services and file I/O classes.
* **Proposed Solution:** Introduce `IProductRepository` as an interface bridge.
* **Code from another member:** No

---

## 9. Protected Variations
* **Verdict:** Violation

### Violation V-DEV1-06
* **Pattern:** Protected Variations
* **Module:** Products / Requirement 1
* **Evidence:** Direct exposure and modification of prices on `Product`.
* **Explanation:** Product unit prices can be updated directly without maintaining historical price records for previously processed transactions.
* **Consequence:** Modifying a product price retroactively changes historical reporting for sales that contained that product.
* **Proposed Solution:** Encapsulate pricing changes and preserve item price snapshots during sales creation.
* **Code from another member:** No