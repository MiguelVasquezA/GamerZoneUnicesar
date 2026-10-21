# AI Usage Log - Developer 1

## Overview
This document logs the authorized use of AI assistance during the development, auditing, and refactoring phases for the Product and Return modules in GamerZoneUnicesar. The AI was used strictly as an auxiliary tool for syntax checks, exception handling patterns, terminal Git commands, and English technical vocabulary.

---

## Log Entries - Requirement 7: GRASP Patterns

### Entry 1: Java File Persistence Syntax Check
* **Date:** 2026-09-08
* **Topic:** CSV line parsing and string splitting in Java.
* **Prompt Summary:** Asked for standard syntax using `String.split()` with safe delimiter handling for text files.
* **AI Assistance Received:** Provided syntax examples for parsing lines safely and handling potential array index boundary errors.
* **Applied Outcome:** Implemented safe CSV parsing logic within the persistence mechanisms of the Product module.

### Entry 2: Polymorphic Method Signatures Syntax
* **Date:** 2026-10-18
* **Topic:** Declaring abstract methods in Java superclasses.
* **Prompt Summary:** Consulted on the syntax for overriding abstract methods in entity hierarchies (`Product`, `VideoGame`, `Console`).
* **AI Assistance Received:** Confirmed standard Java syntax for abstract method declarations and `@Override` annotations.
* **Applied Outcome:** Applied uniform method signatures across product sub-classes.

---

## Log Entries - Requirement 8: SOLID Principles

### Entry 3: Phase 1 (Audit) - Technical English Vocabulary
* **Date:** 2026-10-18
* **Topic:** Technical wording for individual audit markdown documents.
* **Prompt Summary:** Requested technical English terminology to describe boundary dependencies and persistence coupling.
* **AI Assistance Received:** Provided standard terms such as *tight coupling*, *subtyping contracts*, and *concrete instantiation*.
* **Applied Outcome:** Applied vocabulary when writing `docs/solid-audit/developer1-audit.md`.

### Entry 4: Phase 2 (Refactoring) - Constructor Injection Syntax
* **Date:** 2026-10-21
* **Topic:** Dependency Inversion implementation syntax.
* **Prompt Summary:** Asked for standard Java syntax for constructor dependency injection with interfaces.
* **AI Assistance Received:** Clarified standard constructor binding syntax (`IProductRepository`).
* **Applied Outcome:** Updated `ProductService.java` to accept repository interfaces via constructor.

### Entry 5: Phase 2 (Refactoring) - Git Untracked Metadata Cleanup
* **Date:** 2026-10-21
* **Topic:** Resolving workspace branch switching locks in Git.
* **Prompt Summary:** Consulted on terminal commands to clear local `.idea/misc.xml` files blocking branch checkout.
* **AI Assistance Received:** Provided terminal cleanup commands (`git clean -fd`, `git restore --staged`).
* **Applied Outcome:** Cleared local untracked conflicts and updated `.gitignore` rules.