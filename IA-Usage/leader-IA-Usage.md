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