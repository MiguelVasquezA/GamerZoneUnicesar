# SOLID Principles Analysis & Methodology Document

**Repository:** GamerZoneUnicesar  
**Branch:** `develop`  
**Target File:** `docs/solid-analysis.md`

---

## 1. Audit Evaluation Criteria & Minimum Required Evidence

### 1.1 Criteria for Verdict Assignment
The team established a uniform evaluation protocol to classify code fragments into one of three possible verdicts:

1. **Violation (`violation`):** Assigned when a class, method, or structural hierarchy explicitly violates one of the five SOLID design principles, causing tight coupling, low cohesion, or rigidity under predictable extensions.
2. **Correct (`correct`):** Assigned when the evaluated class or method adheres to a specific SOLID principle, demonstrating single-purpose boundaries, abstraction-based extension points, or proper behavioral subtyping.
3. **Not Applicable (`not applicable`):** Assigned when a principle lacks a structural application context within the evaluated code scope (e.g., evaluating LSP on classes that do not use subtyping or class inheritance).

### 1.2 Minimum Required Evidence
To guarantee objectivity and pass team consolidation, every verdict must be accompanied by:
* **Target File & Exact Lines:** The precise file path and line numbers in the current `develop` branch.
* **Code Excerpt:** The exact signature, constructor, or conditional block demonstrating the design flaw or adherence.
* **Architectural Justification:** A technical explanation describing *why* the design breaks the principle and its impact on maintainability or extensibility.

---

## 2. Boundary Findings Across Member Responsibilities

Boundary violations occur when a component assigned to one team member depends inappropriately on concrete implementations or internal workflows maintained by another member.

### 2.1 Concrete Boundary Case: `ConsoleMenu` and `ProductService` / `WarrantyService`
* **Context:** The UI layer (`ConsoleMenu`), under the responsibility of the Technical Leader, directly depends on concrete service classes like `ProductService` (Developer 1) and `WarrantyService` (Developer 2).
* **Identification:** During individual audits, `ConsoleMenu` was flagged for violating ISP and DIP because it depended on full concrete service instances instead of role-specific interfaces.
* **Consolidation Resolution:** In `consolidated-audit.md`, this issue was recorded as a shared boundary finding (e.g., `V-04` and `V-06`). The Technical Leader is responsible for injecting interface contracts in `ConsoleMenu`, while Developer 1 and Developer 2 are responsible for extracting and implementing service interfaces (`IProductStockService`, `IWarrantyService`) in their respective modules.

---

## 3. Relationship Between GRASP Refactoring and SOLID Findings

### 3.1 SOLID Violations in Code Previously Refactored by GRASP
* **Case:** `SaleService` was refactored in Requirement 7 using the GRASP *Controller* and *Information Expert* patterns to orchestrate sales. However, this refactoring left `SaleService` violating **SRP** and **OCP** because it still directly calculates promotional discounts and extended warranty prices using conditional branch logic. GRASP solved initial responsibility assignments, but SOLID requires strategy abstraction.

### 3.2 GRASP Refactorings That Resolved SOLID Violations
* **Case:** In Requirement 7, the generation of text receipts was extracted from `Sale.java` into `ReceiptFormatter.java` using the GRASP *Pure Fabrication* pattern. This GRASP refactoring inherently resolved a **Single Responsibility Principle (SRP)** violation in `Sale.java`, preventing the domain entity from depending on string formatting logic.

---

## 4. Behavior Preservation & Non-Regression Testing Strategy

To guarantee that SOLID refactorings do not alter external application behavior, the team enforces a zero-regression testing protocol:

1. **Execution Scope:** Every CLI menu option (Products, Sales, Returns, Warranties, Promotions, Exception handling) is tested using predefined inputs.
2. **Data Consistency:** File persistence formats (`../data/product.txt`, `sales.txt`, `persons.txt`) must remain 100% identical before and after refactoring.
3. **Verification Stages:**
    * **Stage A (Baseline):** Executed on `develop` prior to Phase 2 refactoring to capture expected output logs.
    * **Stage B (Per PR):** Executed by each member on `feature/solid-refactor` before merging any refactoring Pull Request.
    * **Stage C (Final Integration):** Executed on `develop` after all member refactorings are merged.

---

## 5. Refactoring Order & Coordination in Shared Branches

### 5.1 Refactoring Execution Order
To prevent merge conflicts and broken compilation paths on `develop`, refactorings are ordered from low-level abstractions to high-level controllers:

1. **Phase 2.1 (Domain & Persistence Abstractions - Dev 1 & Dev 2):** Extract repository interfaces (`IProductRepository`, `ISaleRepository`) and service contracts (`IProductStockService`, `IPromotionService`).
2. **Phase 2.2 (Business Logic Strategies - Leader & Developers):** Refactor `SaleService` using `PromotionStrategy` and `WarrantyService` integrations.
3. **Phase 2.3 (UI & Bootstrapper - Technical Leader):** Update `ConsoleMenu` and `Main.java` to inject the new high-level interface abstractions.

### 5.2 Branch Coordination Protocol
* All Phase 2 work is performed on isolated branches off `develop` (`feature/solid-refactor`).
* Developers push atomic commits tagged with the violation ID (e.g., `refactor: resolve SRP in SaleService (V-01)`).
* Pull Requests require code review approval and clean compilation verification (`mvn clean compile`) before merging into `develop`.