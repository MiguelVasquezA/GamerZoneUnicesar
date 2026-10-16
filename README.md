# GamerZone Unicesar - Integrated Management System

An object-oriented Java console application designed to manage products, accessories, customers, sellers, sales, promotional discounts, warranties, and product returns under a clean 4-layer architecture (Service-Repository Pattern).

---

## 🚀 Integrated System Features

* **Product & Accessory Management**: Support for Video Games, Consoles, and Accessories (Controllers, Cables, Memory units) with real-time console compatibility checks.
* **Person Registry**: Unified entity management distinguishing Customers and Sellers.
* **Promotions Engine**:
  * **General Percentage Discount**: Universal percentage discount applied across items (`PercentageDiscount`).
  * **Category Discount**: Targeted percentage discount for Video Games, Consoles, or Accessories (`CategoryDiscount`).
  * **Bulk Purchase Discount**: Volume-based discounts triggered when buying a minimum item count (`BulkPurchaseDiscount`).
  * **Best Promotion Rule**: Evaluates active promotions dynamically on sales subtotals and applies the one yielding maximum savings (`findBestPromotionFor`).
* **Unified Sales Processing**:
  * Sequential execution pipeline: stock validation, subtotal calculation, promotion evaluation, warranty issuance, and total calculation.
  * Formatted English sale receipts displaying subtotal, applied promotion name/discount, warranty fees, and final total.
* **Warranty Module**:
  * **Basic Warranty**: Automatic 6-month coverage at no extra cost for console purchases.
  * **Extended Warranty**: Optional 12-month coverage covering accidental damage (10% additional fee).
  * Interactive queries for active warranties, expiring warranties (30 days), and search by Product/Sale ID.
* **Returns & Financials**:
  * Proportional refund calculations on discounted sales preventing revenue losses.
  * Automatic inventory restoration for products and accessories.
  * Automated warranty revocation on returned consoles.
  * Financial reporting generating monthly gross sales, total returns, and net balance.

---

## 🛠 Layered Architecture & Project Structure

The project strictly follows the required **UI → Service → Persistence → Model** dependency hierarchy:

```text
src/main/java/com/gamezone/
├── model/           # Business domain entities (Product, Sale, Warranty, Return, Promotion, etc.)
├── persistence/     # File handlers, text/CSV repositories, and data storage logic
├── service/         # Core business logic layer (SaleService, ReturnService, WarrantyService, etc.)
└── ui/              # Interactive console interface (ConsoleMenu) and application entry point (Main)