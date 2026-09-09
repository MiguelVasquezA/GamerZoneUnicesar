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