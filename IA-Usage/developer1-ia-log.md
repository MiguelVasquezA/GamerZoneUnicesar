# AI Usage Log - Developer 1 (Product Module)

## Overview

This document logs the use of AI assistance during the development of the Product module (Product, VideoGame, Console, ProductRepository, ProductService) for the GameZone Unicesar project. The AI was used for conceptual review, code correction, and Git workflow guidance — not for generating design decisions or unreviewed code.

## Log Entries

### Entry 1: Inheritance and Encapsulation Review

**Date:** 2026-09-08

**Topic:** Class hierarchy design (Product, VideoGame, Console)

**Prompt Summary:** Asked for review of the VideoGame and Console classes after writing them, to check compliance with the workshop's rules on encapsulation, abstract classes, and constructor consistency.

**AI Assistance Received:** Identified a mismatch between constructor parameter order and JavaDoc documentation in VideoGame and Console. Flagged a swapped-parameter risk in the Console constructor (model vs. generation) that could silently corrupt data without a compiler error. Pointed out missing `@return` JavaDoc tags on getters, required by the workshop's documentation rules.

**Applied Outcome:** Corrected the Console constructor parameter order and completed missing JavaDoc across Product, VideoGame, and Console.

---

### Entry 2: File Persistence Pattern Explanation

**Date:** 2026-09-08

**Topic:** ProductRepository design (save/load via CSV)

**Prompt Summary:** Asked for an explanation of how file-based persistence should work for a class hierarchy (Product/VideoGame/Console), and how to reconstruct different subclasses from a single text file.

**AI Assistance Received:** Explained the pattern of prefixing each CSV line with a type discriminator (VIDEOGAME/CONSOLE) to reconstruct the correct subclass on load, and the responsibility split between save() (object to text) and load() (text to object).

**Applied Outcome:** Implemented ProductRepository with save/load methods following this pattern, understanding the logic rather than copying it unreviewed.

---

### Entry 3: Service Layer Responsibilities

**Date:** 2026-09-08

**Topic:** ProductService business rules

**Prompt Summary:** Asked for clarification on how the service layer should coordinate the in-memory product list with the repository, and how stock validation should work before a sale.

**AI Assistance Received:** Clarified that service methods must update the in-memory list first, then persist via the repository, and that stock checks (hasStock) should exist in ProductService so SaleService (leader's module) can call them before confirming a sale.

**Applied Outcome:** Implemented registerVideoGame, registerConsole, listProducts, hasStock, and reduceStock in ProductService.

---

### Entry 4: Git Workflow Troubleshooting

**Date:** 2026-09-08

**Topic:** Branch creation, commit errors, and empty file templates

**Prompt Summary:** Asked for step-by-step guidance cloning the repository, creating the feature/product-module branch, and troubleshooting why two commits only showed 13 line insertions.

**AI Assistance Received:** Guided through git clone, checkout, and branch creation commands. Helped diagnose that ProductRepository.java and ProductService.java had been committed as empty NetBeans class templates (unsaved files), by inspecting `git show --stat` output.

**Applied Outcome:** Re-saved both files with full implementations and committed the fix (`fix: replace empty class templates with full implementation`), then opened the Pull Request from feature/product-module to develop.

---

### Entry 5: Return Eligibility Business Rules Analysis

**Date:** 2026-09-15

**Topic:** Date validation logic and temporal boundary constraints in Sale class

**Prompt Summary:** Asked for guidance on how to evaluate eligibility for product returns based on calendar day differences without introducing external heavy dependencies or failing on formatted date strings.

**AI Assistance Received:** Explained the approach of parsing date strings safely using `java.time.LocalDate` and using `ChronoUnit.DAYS.between` to validate the 30-day constraint. Emphasized defensive programming to handle time components (ISO 'T' separators) and prevent `DateTimeParseException` from crashing the application flow.

**Applied Outcome:** Designed and implemented the `canBeReturned()` method in `Sale.java` with safe string parsing and boundary checking.

---

### Entry 6: Model Domain Extension and Architectural Consistency

**Date:** 2026-09-22

**Topic:** Domain model design for Return entity and its relation to Sale

**Prompt Summary:** Consulted on the best OOP representation for returned items within a sale transaction, specifically whether partial returns require inheritance, composition, or association.

**AI Assistance Received:** Clarified that `Return` holds an Association relationship with `Sale` since both have independent lifecycles. Advised representing returned items as a dedicated `List<Product>` collection attribute within `Return` to support partial returns without mutating the original sale record structure.

**Applied Outcome:** Formulated the internal structure of `Return.java` and authored the conceptual answers for the `docs/return-analysis.md` architecture document.

---

### Entry 7: Git Workflow and Branching Conflict Recovery

**Date:** 2026-09-28

**Topic:** Terminal stuck in Vim editor and accidental commit recovery

**Prompt Summary:** Requested assistance to exit Vim interface during a stuck merge session in IntelliJ terminal, and how to safely move a commit accidentally placed on `develop` over to `feature/return-module`.

**AI Assistance Received:** Provided Vim exit commands (`:wq`) and explained the root cause. Recommended copying the specific domain file directly across branches (`git checkout develop -- <path>`) rather than performing a complex cherry-pick on merge commits, followed by resetting `develop` to maintain clean branch history.

**Applied Outcome:** Restored terminal access, safely aligned `feature/return-module` with the updated `Sale.java` changes, and reset local `develop` to match `origin/develop`.

---

### Entry 8: Code Quality, JavaDoc Standardization and Final Integration

**Date:** 2026-10-02

**Topic:** JavaDoc completion and Conventional Commits compliance for Return module

**Prompt Summary:** Requested a review on standardizing JavaDoc tags and splitting changes into atomic, conventionally named commits to meet the minimum team requirements.

**AI Assistance Received:** Outlined the standard JavaDoc formatting for public domain methods (`calculateRefundAmount`, `generateReturnReceipt`) and recommended breaking down documentation, refactoring, and analysis answers into distinct Conventional Commits (`feat`, `refactor`, `docs`).

**Applied Outcome:** Refactored `Return.java` and `Sale.java` documentation, updated analysis files, and completed the required atomic commit sequence on `feature/return-module`.

---

### Entry 9: Boundary Value Analysis for Return Date Windows

**Date:** 2026-10-02

**Topic:** Edge-case validation strategies for sale eligibility window

**Prompt Summary:** Consulted on how to handle boundary dates (e.g., exact 30th day vs. 31st day) in Java `time` API to guarantee business rule enforcement without off-by-one errors.

**AI Assistance Received:** Advised on using `ChronoUnit.DAYS.between(saleDate, today)` to ensure exact day count logic regardless of time zones, and explicitly checking `daysBetween <= 30` rather than loose string comparisons.

**Applied Outcome:** Verified and refined `canBeReturned()` in `Sale.java` to handle edge-case dates smoothly.

---

### Entry 10: Null-Safety and Defensive Programming Patterns

**Date:** 2026-10-02

**Topic:** Prevention of `NullPointerException` during receipts generation

**Prompt Summary:** Asked for best practices when generating text receipts from entities that might have optional or missing attributes.

**AI Assistance Received:** Suggested inline ternary null checks (`sale != null ? sale.getId() : "N/A"`) inside `generateReturnReceipt()` to prevent sudden application crashes if uninitialized objects are passed.

**Applied Outcome:** Refactored receipt formatting in `Return.java` with complete defensive checks.

---

### Entry 11: Unit Testing Strategies for Partial Return Calculations

**Date:** 2026-10-02

**Topic:** Calculation logic testing for `calculateRefundAmount`

**Prompt Summary:** Requested guidance on verifying total sum precision when dealing with list filtering in Java domain models.

**AI Assistance Received:** Recomended iterating over non-null elements in the `returnedProducts` list and using `double` accumulation, while validating edge cases such as empty return lists returning `0.0`.

**Applied Outcome:** Confirmed mathematically sound logic inside `calculateRefundAmount()` within `Return.java`.

---

### Entry 12: Pull Request Conflict Resolution Standards

**Date:** 2026-10-02

**Topic:** Branch synchronization best practices for shared feature branches

**Prompt Summary:** Asked how to pull latest changes from teammates working on `feature/return-module` without risking merge loops or uncommitted local work loss.

**AI Assistance Received:** Recommended staging or committing local progress first before running `git pull origin feature/return-module`, resolving any potential conflicts in IDE editor, and pushing cleanly.

**Applied Outcome:** Standardized workflow for synchronizing changes with Developer 2 and Technical Leader.

---

### Entry 13: Conventional Commits Compliance Audit

**Date:** 2026-10-02

**Topic:** Reviewing commit messages against Conventional Commits standard

**Prompt Summary:** Requested a check on commit message structures to ensure full compliance with the project rubric (`feat`, `refactor`, `docs`, `fix`).

**AI Assistance Received:** Provided examples of proper scope tag usage (e.g., `feat(model)`, `docs(analysis)`, `refactor(model)`) to match clean git history requirements.

**Applied Outcome:** Formatted all commit commands according to Conventional Commits standards before pushing to remote repository.

---

### Entry 14: Final Architectural Verification & Checklist

**Date:** 2026-10-02

**Topic:** Four-layer architecture compliance check for Developer 1 deliverables

**Prompt Summary:** Consulted on whether `Return.java` and `Sale.java` adhere strictly to the `model` layer responsibilities without leaking UI or file persistence logic.

**AI Assistance Received:** Confirmed that `Return` and `Sale` only hold domain state and internal calculations, keeping CSV file operations strictly inside `ReturnRepository` and user prompts inside `ConsoleMenu`.

**Applied Outcome:** Finalized Developer 1 code boundaries before opening the Pull Request.
