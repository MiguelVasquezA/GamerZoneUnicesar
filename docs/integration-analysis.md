# Integration Analysis - Requirements 1 to 4 Integration

This document describes the technical adjustments (A1–A7) made during the integration of Accessories, Promotions, Warranties, and Returns modules into a unified system.

---

## A1 - Category Discount for Accessories
* **Type:** New Feature
* **Issue/Context:** The original category discount only supported `VIDEOGAME` and `CONSOLE`. With the Accessories module integrated, promotional discounts needed to support accessory items.
* **Root Cause:** Hardcoded checks restricted target categories to games and consoles.
* **Solution:** Updated `CategoryDiscount.calculateDiscount` to handle `ACCESSORY` instances, validated category types in `PromotionService`, added `ACCESSORY` prompts in `ConsoleMenu`, and added active accessory promotion data to `data/promotions.csv`.

---

## A2 - Circular Dependency Resolution in Warranty Module
* **Type:** Bug Fix
* **Issue/Context:** A circular dependency cycle occurred (`SaleService` → `WarrantyService` → `WarrantyRepository` → `SaleService`) during persistence loading.
* **Root Cause:** `WarrantyRepository` required a reference to `SaleService` to resolve sale instances while reading stored warranties.
* **Solution:** Decoupled `WarrantyRepository` by persisting and reading only primitive identifiers (`saleId`, `productId`). Moved entity reference resolution to `WarrantyService` by injecting `WarrantyRepository`, `SaleRepository`, and `ProductService`.

---

## A3 - Unified Sale Registration Flow
* **Type:** Refactor
* **Issue/Context:** Independent modifications to `SaleService.registerSale` created ambiguity regarding execution order (e.g., whether discounts applied before or after warranty costs).
* **Root Cause:** Uncoordinated method extensions across requirements.
* **Solution:** Reorganized `SaleService.registerSale` into a strict sequential pipeline:
    1. Validate non-empty item list.
    2. Validate stock for products and accessories.
    3. Create transaction and calculate subtotal.
    4. Evaluate best active promotion on subtotal only.
    5. Issue console basic/extended warranties and compute additional costs.
    6. Calculate final total (`subtotal - discount + warrantyCost`).
    7. Decrement product/accessory inventory.
    8. Persist sale and warranty records.
       Updated `Sale.generateReceipt` to display subtotal, applied promotion discount, extended warranty charges, and total final amount.

---

## A4 - Accessory Inventory Restoration on Return
* **Type:** Bug Fix
* **Issue/Context:** Processing returns invoked `ProductService.restoreStock`, which ignored accessory stock updates.
* **Root Cause:** Lack of polymorphism or delegation handling accessory instances in return processing.
* **Solution:** Added `restoreStock` method to `AccessoryService`, updated `ReturnService` to delegate inventory restoration based on item dynamic type, and updated `ReturnRepository` to resolve accessory references during data loading.

---

## A5 - Proportional Refund for Discounted Sales
* **Type:** Bug Fix
* **Issue/Context:** Returning items from a sale with promotional discounts refunded full list prices, resulting in store revenue loss.
* **Root Cause:** `Return.calculateRefundAmount` summed raw item prices without adjusting for total sale discounts.
* **Solution:** Implemented proportional discount distribution using formula: `itemRefund = listPrice - (listPrice * totalDiscount / itemsSubtotal)`. Updated `Return.generateReturnReceipt` to print list price, proportional discount, and net refund for each returned item.

---

## A6 - Monthly Balance Financial Reporting
* **Type:** Bug Fix
* **Issue/Context:** Financial balance reports omitted return deductions and failed to aggregate total gross sales containing extended warranties or discounts.
* **Root Cause:** `generateMonthlyBalance` calculated raw values without deducting return totals.
* **Solution:** Implemented `calculateMonthlySales` and `calculateMonthlyReturns` in `ReturnService`. Updated `generateMonthlyBalance` to calculate net balance as `monthlySales - monthlyReturns`. Updated `ConsoleMenu` to present gross sales, total returns, and net balance.

---

## A7 - Warranty Cancellation on Console Returns
* **Type:** New Feature
* **Issue/Context:** Returned consoles retained active warranties, allowing invalid claims post-refund.
* **Root Cause:** Absence of warranty lifecycle synchronization upon item return.
* **Solution:** Added `cancelWarranties(productId, saleId)` in `WarrantyService` to revoke active warranties and return refundable extended warranty fees. Integrated warranty cancellation into `ReturnService.registerReturn` and incorporated refunded warranty amounts into `Return` calculations and receipt generation.