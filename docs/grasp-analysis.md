# GRASP Audit & Refactoring Analysis

## 1. Violation Criteria & Evidence Requirements
The team established that a code block constitutes a GRASP pattern violation when its design directly contradicts the primary intent of the pattern, creating measurable maintainability or coupling risks.
To validate a verdict for consolidation, every finding must be accompanied by:
* Exact file path and line numbers in the `develop` branch.
* A clear explanation of the design flaw according to GRASP principles.
* Concrete architectural consequences (maintainability, testing, scalability risks).
* A proposed structural refactoring solution.

## 2. Border Findings Between Members
Border findings occur when a class developed by one member directly instantiates or inappropriately interacts with classes owned by another member.
* **Identified Case:** `ConsoleMenu` (Technical Leader) directly instantiates and orchestrates `ProductService` (Developer 1) and `WarrantyService` / `AccessoryService` (Developer 2) alongside `SaleService`.
* **Resolution:** Registered under violation `V-03` (Controller / Low Coupling). The Technical Leader will refactor `ConsoleMenu` to depend on service interfaces, while Developers 1 and 2 define clean service boundaries.

## 3. Handling Multiple Pattern Violations
When a single method or class violates multiple GRASP patterns (e.g., `Sale.generateReceipt()` violating both *High Cohesion* and *Information Expert*):
* The finding is assigned a single primary violation ID to eliminate redundancy in tracking.
* The primary pattern that dictates the architectural fix is designated to guide refactoring (e.g., *High Cohesion* guides moving presentation code out of domain entities).

## 4. Behavior Preservation & Non-Regression
To ensure zero regression in system functionality during Phase 2 refactoring:
* The original console execution workflows, inputs, and console outputs must remain 100% identical from the end-user perspective.
* Data serialization file structures (`data/*.txt`) and error handling output formats must be validated using automated unit tests and manual execution before merging PRs.

## 5. Refactoring Order & Work Coordination
1. **Interfaces & Abstractions (Indirection First):** Define `ISaleRepository`, `IProductRepository`, and service interfaces.
2. **Domain Entities:** Refactor `Sale`, `Product`, and `Person` to enforce *Information Expert*, *Creator*, and *Protected Variations*.
3. **User Interface & Controllers:** Refactor `ConsoleMenu` and `Main` to enforce *Controller* and *High Cohesion*.
4. Work will be integrated sequentially through feature branches with mandatory Pull Request reviews by the Technical Leader.