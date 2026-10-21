# AI Usage Log - Developer 2

## Overview
This document logs the authorized use of AI assistance during the development, auditing, and refactoring phases for Person, Accessory, and Promotion modules in GamerZoneUnicesar. The AI was used exclusively for debugging runtime exceptions, reviewing Git commands, and verifying Java Collection manipulation syntax.

---

## Log Entries - Requirement 7: GRASP Patterns

### Entry 1: Java Collections Lambda/Stream Syntax
* **Date:** 2026-09-08
* **Topic:** Filtering collections by entity attributes.
* **Prompt Summary:** Asked for standard Java Stream API syntax to filter lists by object IDs.
* **AI Assistance Received:** Provided basic examples of list filtering using `stream().filter()` and `findFirst()`.
* **Applied Outcome:** Utilized standard list filtering in service lookup methods.

### Entry 2: Custom Exception Handling Syntax
* **Date:** 2026-09-22
* **Topic:** Creating custom checked exceptions.
* **Prompt Summary:** Consulted on extending `Exception` to construct domain-specific validation exceptions.
* **AI Assistance Received:** Showed constructor super-calls (`super(message)`) for custom exception classes.
* **Applied Outcome:** Standardized exception classes within the validation packages.

---

## Log Entries - Requirement 8: SOLID Principles

### Entry 3: Phase 1 (Audit) - Markdown Table Formatting
* **Date:** 2026-10-18
* **Topic:** Documenting audit findings in Markdown.
* **Prompt Summary:** Asked for standard Markdown syntax to format audit tables with multiple columns.
* **AI Assistance Received:** Provided clean Markdown table templates for violation logs.
* **Applied Outcome:** Formatted `docs/solid-audit/developer2-audit.md` cleanly.

### Entry 4: Phase 2 (Refactoring) - Interface Realization Syntax
* **Date:** 2026-10-21
* **Topic:** Implementing interface contracts in service classes.
* **Prompt Summary:** Asked for Java syntax when a class implements multiple interface contracts.
* **AI Assistance Received:** Provided standard syntax for `implements ContractA, ContractB`.
* **Applied Outcome:** Applied interface implementations across assigned service classes.

### Entry 5: Phase 2 (Refactoring) - Git Branch Rebase and Sync
* **Date:** 2026-10-21
* **Topic:** Synchronizing local feature branches with remote `develop`.
* **Prompt Summary:** Requested steps to update local branches safely without losing working directory changes.
* **AI Assistance Received:** Outlined standard `git fetch` and `git pull origin develop` steps.
* **Applied Outcome:** Maintained smooth local branch synchronization during refactoring.