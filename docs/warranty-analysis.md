# Architectural Analysis - Warranty Module (Requirement 4)

## 1. Class Hierarchy and Polymorphism
The `Warranty` abstract class defines common attributes (`id`, `product`, `sale`, `startDate`, `endDate`) and abstract methods (`getDurationInMonths()`, `getWarrantyType()`, `getAdditionalCost()`). Polymorphism allows each concrete subclass (`BasicWarranty` and `ExtendedWarranty`) to define its own duration and cost rules without code duplication. The base constructor invokes `getDurationInMonths()` dynamically to calculate the `endDate`.

## 2. Business Rule Location and Type Verification
The decision to assign basic warranties exclusively to consoles resides in the Service Layer (`SaleService`). Java's `instanceof` operator (or pattern matching for `instanceof`) is used to verify if a `Product` instance is specifically a `Console`. This keeps business decisions decoupled from data transfer and model definitions.

## 3. Expiration Date Calculation
Each subclass defines its duration via `getDurationInMonths()` (6 for basic, 12 for extended). The calculation `startDate.plusMonths(getDurationInMonths())` is executed inside the base `Warranty` constructor so that every instantiated warranty object is immediately guaranteed to be in a valid and consistent state.

## 4. Extended Warranty Cost Calculation
The additional cost (10% of the product price) is calculated in `ExtendedWarranty.getAdditionalCost()`. In `SaleService.registerSale`, when processing requested extended warranties, this cost is retrieved and added to the sale's total amount before finalizing the transaction.

## 5. Expiring Warranties Query Location
The `listWarrantiesExpiringSoon(int daysAhead)` method is located in `WarrantyService`. It depends on `WarrantyRepository` to retrieve persisted records and filters them using `isActive()` and date comparison methods. This respects layered architecture by keeping business filtering out of UI and persistence components.