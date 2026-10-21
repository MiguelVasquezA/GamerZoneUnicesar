# SOLID Refactoring Log & Behavior Preservation Record

**Repository:** GamerZoneUnicesar  
**Branch:** `feature/solid-refactor`  
**Target File:** `docs/solid-refactor-log.md`

---

## 1. Non-Regression Verification Strategy

| Menu Option / Operation | Verification Test Input Data | Expected Output Result | Baseline Status | Post-Refactor Status |
| :--- | :--- | :--- | :--- | :--- |
| **1. Register Sale** | Customer ID, Product ID, Quantity | Sale registered, receipt printed, stock decremented in `product.txt` | PASS | PASS |
| **2. Apply Promotion** | Sale with Category/Percentage promo | Correct total amount with applied discount breakdown | PASS | PASS |
| **3. Process Return** | Valid Sale ID, Product ID | Return logged, stock restored in `product.txt` | PASS | PASS |
| **4. Warranty Assignment** | Product ID, Extended Warranty option | Extended warranty fee added, warranty record persisted | PASS | PASS |
| **5. Exception Handling** | Invalid Product ID / Out-of-stock qty | Specific Exception caught, friendly error message in console | PASS | PASS |

---

## 2. Refactoring Change Log

| Class / File | Changes Applied | Violation ID | Principle Applied | Commit / PR | Responsible Member |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `ISaleRepository` | Created interface abstraction for sale persistence | `V-05` | DIP | Pending | Technical Leader |
| `IProductRepository` | Created interface abstraction for product persistence | `V-08` | DIP | Pending | Developer 1 |
| `PromotionStrategy` | Created interface for promotion strategies | `V-03` | OCP | Pending | Technical Leader |
| `SaleService` | Refactored dependencies to interfaces and extracted strategies | `V-01`, `V-03`, `V-05` | SRP, OCP, DIP | Pending | Technical Leader |
| `ProductService` | Injected `IProductRepository` abstraction | `V-08` | DIP | Pending | Developer 1 |
| `ConsoleInputReader` | Extracted CLI terminal input parsing from `ConsoleMenu` | `V-02` | SRP | Pending | Technical Leader |
| `ConsoleMenu` | Bound controller to high-level service interfaces | `V-04`, `V-06` | ISP, DIP | Pending | Technical Leader |