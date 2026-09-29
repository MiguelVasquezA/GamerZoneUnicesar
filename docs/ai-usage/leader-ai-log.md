# AI Interaction Log - Technical Leader

**Role:** Technical Leader  
**Module:** Sales & System Integration (`com.gamezone.model.Sale`, `SaleRepository`, `SaleService`, `MainConsoleUI`, `Main`)  
**Student Name:** Miguel Ángel Vásquez Aguirre

---

## 1. Interaction Summary

This log documents the usage of Artificial Intelligence as a technical partner during the analysis, architecture configuration, Git Flow setup, directory restructuring, and implementation planning for Workshop 2 (*GameZoneUnicesar*).

---

## 2. Detailed AI Prompts & Responses

### Prompt 1: Project Architecture & Vertical Module Distribution
* **User Query:** Clarification on how Git feature branches should be structured and whether files from other modules should exist in `feature/sales-module`.
* **AI Guidance:** Confirmed that each feature branch (`feature/sales-module`, `feature/product-module`, `feature/person-module`) originates from `develop` and retains the full project base to ensure code compilation and continuous integration.
* **Outcome Applied:** Kept all layer packages and existing stub classes in `feature/sales-module` so that `MainConsoleUI.java` and `Main.java` compile without breaking.

---

### Prompt 2: Directory Restructuring for AI Log Guidelines
* **User Query:** Verification of the project folder tree against the official workshop specification (`docs/ai-usage/`).
* **AI Guidance:** Corrected the placement of `ai-usage/` inside `docs/` and formatted the log filename to lowercase (`leader-ai-log.md`).
* **Outcome Applied:** Deleted duplicate/misformatted log files (`leader-IA-Usage.md`) and executed Git commands to standardize file naming.

---

### Prompt 3: Technical Analysis Documentation (`docs/analysis.md`)
* **User Query:** Review and refinement of the 11 guided analysis questions in English regarding OOP principles (Inheritance, Polymorphism, Abstraction, Encapsulation, Aggregation vs. Association) and Layered Architecture rules.
* **AI Guidance:** Provided structured, fully-reasoned answers in English matching the domain entities (`Product`, `VideoGame`, `Console`, `Person`, `Customer`, `Seller`, `Sale`).
* **Outcome Applied:** Updated `docs/analysis.md` and committed the changes to `feature/sales-module`.

---

### Prompt 4: Handling Team Dependencies & Unfinished Modules
* **User Query:** How to proceed with developing `MainConsoleUI` and `Main` when Developer 2 (Person Module) has not finished their code yet.
* **AI Guidance:** Recommended creating lightweight stub classes (`Person.java`, `Customer.java`, `Seller.java`, `PersonService.java`) with empty method signatures in `feature/sales-module` to allow UI development without blocking.
* **Outcome Applied:** Unblocked UI development without waiting for external feature branch merges.

---

### Prompt 5: Managing Git Branch Cleaning & Remotes in IntelliJ
* **User Query:** How to delete obsolete layer-based local branches (`feature/model`, `feature/persistence`, etc.) and fetch new feature branches (`feature/product-module`) from teammates.
* **AI Guidance:** Instructed the use of `git fetch --prune` and `git branch -D` for local cleanup, explaining remote tracking branches in IntelliJ's Git panel.
* **Outcome Applied:** Cleaned up local repository state leaving only `main`, `develop`, and `feature/sales-module`.

---

### Prompt 6: Pull Request Workflow & Conventional Commits
* **User Query:** Guidelines on when and how to open Pull Requests from `feature/sales-module` into `develop` and formatting PR titles.
* **AI Guidance:** Advised on opening PRs after atomic commits, using Conventional Commit formats (e.g., `refactor(docs): organize ai-usage directory`), and verifying base branch targets in GitHub.
* **Outcome Applied:** Merged initial structural refactoring into `develop` for teammates to sync.

---

### Prompt 7: IntelliJ Sync & Merge Conflict Avoidance
* **User Query:** Choosing between "Merge incoming changes" and "Rebase" during IntelliJ's project update dialog.
* **AI Guidance:** Recommended selecting "Merge incoming changes" to preserve the accurate linear history and avoid unnecessary rebase conflicts across shared branches.
* **Outcome Applied:** Safely pulled updates from `origin/develop` into `feature/sales-module`.

---

### Prompt 8: Local vs Remote Branch Synchronization State
* **User Query:** Understanding why teammate branches (`feature/product-module`) exist on GitHub but do not automatically show up in IntelliJ's local branch list.
* **AI Guidance:** Explained the distinction between local branches and remote-tracking references, providing `git fetch --all` to retrieve new origin branches.
* **Outcome Applied:** Successfully fetched teammate branches to track team progress.

---

### Prompt 9: Domain Model Design & Sales Calculations
* **User Query:** Rules regarding total sale calculations and stock validation in the `Sale` and `SaleService` classes.
* **AI Guidance:** Clarified that the `Sale` entity calculates its total price by summing product unit prices, while `SaleService` validates inventory levels before persisting.
* **Outcome Applied:** Enforced business rules directly within the domain and service layers.

---

### Prompt 10: Final Audit & Log Reconstruction
* **User Query:** Restoring and auditing the AI Interaction Log to ensure 10 entries covering the full workflow according to the rubric.
* **AI Guidance:** Synthesized all interaction milestones into a compliant, structured 10-prompt Markdown document.
* **Outcome Applied:** Generated and pushed `docs/ai-usage/leader-ai-log.md`.

---

## 3. Reflection on AI Usage

Using AI as a technical partner accelerated architectural decision-making, ensured strict compliance with UML and Git Flow standards, and maintained adherence to project guidelines without violating design constraints.