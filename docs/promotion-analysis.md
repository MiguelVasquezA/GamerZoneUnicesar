# Promotion Module Guided Analysis

## 1. Class Hierarchy and Polymorphism
The promotion system uses an abstract base class `Promotion` that encapsulates shared attributes (`id`, `name`, `startDate`, `endDate`) and the common method `isActive(LocalDate)`. Polymorphism is achieved through the abstract method `calculateDiscount(Sale)`. Each concrete subclass (`PercentageDiscount`, `CategoryDiscount`, and `BulkPurchaseDiscount`) provides its own specific implementation of this method. This allows the rest of the application (such as `PromotionService` and `SaleService`) to process discounts uniformly via the parent type without knowing the specific concrete classes.

## 2. Abstract Method Justification
The `calculateDiscount(Sale)` method is declared as `abstract` in `Promotion` because a generic promotion does not have a default calculation strategy. Declaring it abstract enforces a contract at compile-time, guaranteeing that any non-abstract subclass extending `Promotion` MUST provide a concrete implementation for calculating the discount amount.

## 3. Placement of Best Promotion Business Logic
The logic for selecting the highest discount (`findBestPromotionFor`) is located in `PromotionService`. This adheres strictly to Layered Architecture principles, placing domain rules inside the service layer. Placing this logic in `Sale` would violate the Single Responsibility Principle (SRP) by overloading domain models with service orchestration. Placing it in `ConsoleMenu` would leak business logic into the presentation/UI layer.

## 4. Modifications to Sale Class and Backward Compatibility
The `Sale` class was updated with two fields: `appliedPromotionName` and `discountAmount`, along with updates to `generateReceipt()` to format and display these values. These modifications are strictly additive. If no promotion applies, the fields remain null/zero, and the receipt prints standard amounts without breaking existing system functionality or dependencies.

## 5. Promotion Validity Verification
Validity verification occurs in `Promotion.isActive(LocalDate)` to validate whether a specific promotion date range is open. However, `PromotionService` orchestrates this by filtering all loaded promotions against `LocalDate.now()` during runtime queries. This cleanly separates the model's responsibility (checking its own date range) from the service's responsibility (querying valid domain objects).