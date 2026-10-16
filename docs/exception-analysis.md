# Architectural Analysis: Exception Handling & Validation Framework

## 1. Exception Hierarchy Design Strategy
The system adopts a custom, domain-specific exception hierarchy extending `RuntimeException` (unchecked exceptions). The root exception, `GameZoneException`, encapsulates a standard HTTP-like `errorCode` alongside the descriptive error message.

By categorizing system failures into explicit subclasses (`ResourceNotFoundException`, `BusinessRuleException`, `InvalidDataException`, and `PersistenceException`), the architecture maintains clear semantic boundaries between data format violations, domain rule enforcement, resource existence checks, and technical I/O failures.

## 2. Checked vs. Unchecked Exception Trade-offs
Checked exceptions enforce compile-time error handling, forcing caller methods to either declare or catch failures. However, in a multi-layered architecture, checked exceptions create tight coupling between low-level persistence mechanisms and high-level controllers.

Unchecked exceptions (extending `RuntimeException`) were selected to streamline layer communication without cluttering intermediate business service signatures with boilerplate `throws` clauses. Technical checked exceptions (such as `IOException`) are intercepted at the repository layer and wrapped into unchecked `PersistenceException` instances.

## 3. Separation of Concerns in Validation Logic
Input validation and domain integrity rules are decoupled from entity definitions and service orchestrators through dedicated static validator classes (`ProductValidator`, `PersonValidator`, `SaleValidator`).

* **Entities (`com.gamezone.model`)**: Pure data containers holding system state.
* **Validators (`com.gamezone.validation`)**: Stateless validation components enforcing pre-conditions, non-null constraints, and range checks.
* **Services (`com.gamezone.service`)**: Business flow managers delegating checks to validators before executing state transformations.

## 4. Exception Translation Pattern
Low-level infrastructure failures (such as `IOException` during CSV parsing or file writing) are caught within the `com.gamezone.persistence` layer. Rather than allowing raw I/O stack traces to bubble up to the presentation layer, repositories perform **Exception Translation**: wrapping the root cause `IOException` inside a domain-meaningful `PersistenceException`. This preserves technical details for logging while supplying user-friendly diagnostic messages to the console menu.

## 5. Console User Experience & Resiliency
The `ConsoleMenu` implements a centralized exception handling mechanism using a polymorphic `try-catch` block inside its interactive loop. When domain exceptions are thrown during command execution, the UI displays clear, formatted error messages with error codes (e.g., `[GZ-ERR-404]`) without exposing technical stack traces or crashing the JVM. This design ensures system stability, enabling continuous user interaction even after operational or input failures.