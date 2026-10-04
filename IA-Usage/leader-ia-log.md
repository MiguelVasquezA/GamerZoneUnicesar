# AI Usage Log - Technical Leader

## Overview
This document logs the architectural consultation, structural validations, and technical guidance requested from AI tools during the analysis, design, and integration phases of the **GameZone Unicesar** project. The AI was used strictly as a technical advisor to review design patterns, validate multi-layer dependencies, and clarify system integration rules.

---

## Log Entries

### Entry 1: Layered Architecture & Unidirectional Dependencies
* **Date:** 2026-09-05
* **Topic:** Multi-Tier Architecture & Layer Responsibilities
* **Prompt Summary:** Requested technical guidance on how to properly structure the 4 required system layers (`model`, `persistence`, `service`, `ui`) and enforce clean unidirectional dependencies according to Java standards.
* **AI Assistance / Guidance Received:**
    * Clarified the separation of concerns: domain models hold state, persistence manages file I/O operations, services encapsulate business logic/orchestration, and UI manages user input/output.
    * Confirmed allowable dependency flow (`ui` -> `service` -> `model` & `persistence`), ensuring `model` remains completely decoupled from file handling and console logic.
* **Applied Outcome:** Defined the base package layout and architectural rules for the entire team before commencing design documentation.

---

### Entry 2: Domain Hierarchy & Abstract Class Constraints
* **Date:** 2026-09-05
* **Topic:** Object-Oriented Analysis & Class Specialization
* **Prompt Summary:** Consulted on whether base entities like `Person` and `Product` should be instantiable and how to model abstract behavior such as custom product descriptions.
* **AI Assistance / Guidance Received:**
    * Advised declaring `Person` and `Product` as abstract base classes to prevent invalid direct instantiation.
    * Recommended declaring abstract methods (e.g., `getDescription()`) in the base class to mandate concrete implementations in subclasses (`Game`, `Console`).
* **Applied Outcome:** Formulated technical rationale and answered the OOP design questions for `docs/analysis.md`.

---

### Entry 3: Transactional Rules & Stock Deduction Mechanics
* **Date:** 2026-09-07
* **Topic:** Sales Transaction Logic & Inventory Integrity
* **Prompt Summary:** Consulted on the proper layer placement for inventory validation logic during a sale transaction.
* **AI Assistance / Guidance Received:**
    * Indicated that stock availability verification and auto-deduction should reside strictly within the `service` layer (`SaleService`), not inside UI controllers or entity models.
    * Advised enforcing business invariants, such as requiring at least one valid product line item per sale transaction.
* **Applied Outcome:** Established the transactional flow for registering sales and updating stock levels safely across modules.

---

### Entry 4: UML Notation & Mermaid Syntax Validation
* **Date:** 2026-09-07
* **Topic:** Architectural Diagrams & Visual Standards
* **Prompt Summary:** Asked for standard UML syntax verification in Mermaid markdown to represent class relationships and layered dependencies cleanly.
* **AI Assistance / Guidance Received:**
    * Provided syntax guidelines for standard UML relationship types in Mermaid: inheritance (`<|--`), aggregation (`o--`), composition (`*--`), and dependency (`..>`).
* **Applied Outcome:** Formatted and validated `docs/hierarchy-diagram.md`, `docs/class-diagram.md`, and `docs/layers-diagram.md`.

---

### Entry 5: Console UI Orchestration & Service Injection
* **Date:** 2026-09-08
* **Topic:** User Interface Design & Service Integration
* **Prompt Summary:** Requested best practices for designing a text-based UI menu that interacts with multiple service modules (`PersonService`, `ProductService`, `SaleService`) without leaking persistence logic to the user layer.
* **AI Assistance / Guidance Received:**
    * Suggested injecting service instances directly into the UI controller/menu handler to maintain full decoupling from persistence repositories.
    * Recommended structuring interactive prompts to validate inputs before invoking service-layer methods.
* **Applied Outcome:** Designed the main menu interaction flow and defined the UI delegation logic for `MainConsoleUI`.

---

### Entry 6: Application Bootstrap & Cross-Module Startup
* **Date:** 2026-09-08
* **Topic:** Entry Point Design & System Initialization
* **Prompt Summary:** Consulted on standard approaches for bootstrapping a layered Java application and initializing data storage upon application startup.
* **AI Assistance / Guidance Received:**
    * Recommended placing the application bootstrapper in `Main.java`, responsible for instantiating persistence repositories, wiring services, and starting the UI loop.
    * Suggested handling file initialization checks gracefully at startup to prevent application crashes on missing data files.
* **Applied Outcome:** Structured `Main.java` initialization sequence and prepared the final system integration strategy.

---

### Entry 7: Inventory Restoration Strategy & Method Encapsulation
* **Date:** 2026-09-12
* **Topic:** Stock Replenishment Logic in ProductService
* **Prompt Summary:** Requested architectural guidance on how to safely restore inventory stock during a return transaction without duplicating update code across services.
* **AI Assistance / Guidance Received:**
  * Advised encapsulating stock updates within `ProductService` via explicit methods (`restoreStock` or `updateStock`), preventing external classes from modifying product collections directly.
  * Recommended ensuring persistence operations (`save` / `saveAll`) are triggered immediately after stock adjustment to maintain file storage synchronization.
* **Applied Outcome:** Implemented defensive inventory restoration in `ProductService.java` to support return operations seamlessly.

---

### Entry 8: Domain Relationship Analysis for Return Module
* **Date:** 2026-09-18
* **Topic:** OOP Entity Associations & Composition Rules
* **Prompt Summary:** Consulted on the proper domain modeling relationship between `Return`, `Sale`, and `Product` entities.
* **AI Assistance / Guidance Received:**
  * Clarified that `Return` holds an Association with `Sale` since sales exist independently of returns, rejecting inheritance or strong composition.
  * Recommended representing returned items as a dedicated `List<Product>` attribute to support partial returns without mutating original sale records.
* **Applied Outcome:** Validated domain entity structure and authored technical responses in `docs/return-analysis.md`.

---

### Entry 9: Terminal Merge Locks & Local Branch Recovery
* **Date:** 2026-09-24
* **Topic:** Git Workflow Troubleshooting & Branch Synchronization
* **Prompt Summary:** Requested step-by-step guidance to exit stuck Vim text editors during terminal git merges and recover branch history safely.
* **AI Assistance / Guidance Received:**
  * Provided standard Vim navigation commands (`:wq` / `:q!`) to clear merge prompt locks in IntelliJ terminal.
  * Advised using `git checkout develop -- <file>` to selectively sync conflicted shared files without losing local feature branch progress.
* **Applied Outcome:** Restored terminal responsiveness and cleanly synchronized local `feature/return-module` with remote repository updates.

---

### Entry 10: Service Method Signature Alignment in Console UI
* **Date:** 2026-09-29
* **Topic:** UI Refactoring & Multi-Developer Method Resolution
* **Prompt Summary:** Consulted on resolving compilation errors in `ConsoleMenu.java` caused by mismatched method signatures from teammates' service implementations (e.g., `registerReturn` vs `processReturn`, `viewAllReturns` vs `listAllReturns`, and `getSaleId` vs `getId`).
* **AI Assistance / Guidance Received:**
  * Guided the adaptation of `ConsoleMenu.java` to match exact method signatures exposed by `ReturnService` (`registerReturn`, `viewAllReturns`) and `Sale` (`getSaleId`).
  * Advised wrapping service calls in `try-catch` blocks to display clean error feedback without interrupting the main console execution loop.
* **Applied Outcome:** Successfully integrated `manageReturnsMenu()` in `ConsoleMenu.java` with complete exception handling and zero compilation errors.

---

### Entry 11: Return Architecture Class Diagram (Mermaid)
* **Date:** 2026-10-01
* **Topic:** Technical Documentation & Visual Architecture
* **Prompt Summary:** Requested a standard Mermaid class diagram representation reflecting the exact fields, methods, and relationships between `Return`, `Sale`, `ReturnService`, `ProductService`, and `ReturnRepository`.
* **AI Assistance / Guidance Received:**
  * Formatted a compliant Mermaid `classDiagram` snippet with accurate cardinalities (1-to-1 and 1-to-many) and typed parameter lists matching the codebase.
* **Applied Outcome:** Created and committed `docs/return-class-diagram.md` for project architecture review.

---

### Entry 12: Stream API Financial Balance Validation
* **Date:** 2026-10-02
* **Topic:** Business Logic Review & Financial Calculation Integrity
* **Prompt Summary:** Requested review of the stream-based monthly balance calculation in `ReturnService` to ensure accurate net income generation.
* **AI Assistance / Guidance Received:**
  * Reviewed Java Stream API operations using `LocalDate.getMonthValue()` and `LocalDate.getYear()`, confirming double precision accumulation when subtracting total refunds from total sales.
* **Applied Outcome:** Verified mathematical accuracy of `generateMonthlyBalance` in `ReturnService.java`.

---

### Entry 13: Technical Leader Deliverables Audit & PR Finalization
* **Date:** 2026-10-02
* **Topic:** Conventional Commits Standard & Pull Request Integration
* **Prompt Summary:** Consulted on conducting a final verification of Technical Leader deliverables against project rubrics before merging into `develop`.
* **AI Assistance / Guidance Received:**
  * Validated that all 5 required technical leader tasks (stock restoration, UI integration, Mermaid diagram, README updates, and AI logs) were committed using proper Conventional Commit tags (`feat`, `docs`, `refactor`).
  * Provided Pull Request merge guidelines (using standard Merge Commit over Rebase to preserve branch topology).
* **Applied Outcome:** Finalized atomic commit history and successfully submitted Pull Request from `feature/return-module` to `develop`.