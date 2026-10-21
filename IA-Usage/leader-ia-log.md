# AI Usage Log - Technical Leader

## Overview
This document logs the authorized use of AI assistance during the leadership, orchestration, audit consolidation, and refactoring phases for GamerZoneUnicesar. AI tools were utilized strictly as an auxiliary tool for terminal environment configuration, Mermaid diagram syntax validation, Git Flow guidance, and documentation formatting, in compliance with course policies.

---

## Log Entries - Requirement 7: GRASP Patterns

### Entry 1: Terminal Menu Input Loop Syntax
* **Date:** 2026-10-08
* **Topic:** Console Scanner buffer clearing.
* **Prompt Summary:** Consulted on handling `Scanner.nextLine()` buffer clearing after reading numeric integers.
* **AI Assistance Received:** Clarified standard patterns to prevent skipped input lines in CLI menus.
* **Applied Outcome:** Ensured clean menu input handling in `ConsoleMenu.java`.

### Entry 2: Git Merge Conflict Resolution Guidance
* **Date:** 2026-10-12
* **Topic:** Resolving merge conflicts in feature branches.
* **Prompt Summary:** Asked for terminal commands to abort or complete a stuck Git merge session.
* **AI Assistance Received:** Provided standard Git commands (`git merge --abort`, `git status`).
* **Applied Outcome:** Resolved branch integration issues safely during team PR merges.

---

## Log Entries - Requirement 8: SOLID Principles

### Entry 3: Phase 1 (Audit) - Mermaid Class Diagram Syntax
* **Date:** 2026-10-18
* **Topic:** Class diagram generation using Mermaid syntax.
* **Prompt Summary:** Requested correct Mermaid syntax for illustrating interface implementation (`..|>`) and dependency notes.
* **AI Assistance Received:** Provided syntax examples for class relations and annotation boxes in Mermaid.
* **Applied Outcome:** Drafted class diagrams in `docs/solid-class-diagram.md`.

### Entry 4: Phase 1 (Audit) - Document Structuring Guidance
* **Date:** 2026-10-18
* **Topic:** English formatting for consolidated audit reports.
* **Prompt Summary:** Consulted on standard Markdown section layout for multi-member consolidation documents.
* **AI Assistance Received:** Suggested clear heading structures and table formats for cross-member coverage.
* **Applied Outcome:** Structured `docs/solid-audit/consolidated-audit.md` and `docs/solid-analysis.md`.

### Entry 5: Phase 2 (Refactoring) - IDE Java SDK Reference Reset
* **Date:** 2026-10-21
* **Topic:** Troubleshooting IntelliJ IDEA JDK configuration warnings.
* **Prompt Summary:** Asked how to fix "Project JDK is not defined" warnings after clearing local `.idea` folders.
* **AI Assistance Received:** Explained steps to re-bind project SDK settings within Project Structure menus.
* **Applied Outcome:** Restored IDE compiler references and ensured clean project execution with `mvn clean compile`.