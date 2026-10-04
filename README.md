# GamerZone Unicesar - Management System

An object-oriented Java console application designed to manage products, accessories, persons, sales, promotional discounts, product returns, and warranties under clean architecture principles (Service-Repository Pattern).

---

## 🚀 Features & Modules

* **Product Management**: Support for Video Games and Consoles.
* **Accessory Management**: Register and filter Controllers, Cables, and Memory units with real-time console compatibility checks.
* **Person Management**: Unified registry and distinction between Customers and Sellers.
* **Promotions Engine**:
  * **General Percentage Discount**: Standard percentage discount applied across transactions (`PercentageDiscount`).
  * **Category Discount**: Targeted discounts applied specifically to games or consoles (`CategoryDiscount`).
  * **Bulk Purchase Discount**: Volume-based discounts triggered when buying a minimum item quantity (`BulkPurchaseDiscount`).
  * **Best Promotion Rule**: Evaluates all active promotions for a sale and automatically applies the one yielding the maximum discount.
* **Sales Processing**: Comprehensive transaction pipeline updating item inventory, calculating totals, applying optimal discounts, issuing console warranties, and printing receipts.
* **Returns Management**: Registration and processing of product returns within warranty or standard time windows, adjusting inventory and generating return vouchers.
* **Warranty Module**:
  * **Basic Warranty**: Automatically issued for Console sales (6-month coverage at no extra cost).
  * **Extended Warranty**: Optional 12-month coverage covering accidental damage (adds a 10% fee based on product price).
  * **Warranty Queries**: Interactive listing of active warranties, warranties expiring within 30 days, and search by Product/Sale ID.

---

## 🛠 Architecture & Project Structure

The application strictly adheres to a **4-Layered Service-Repository Architecture**:

```text
src/main/java/com/gamezone/
├── model/           # Business domain models (Product, Sale, Warranty, Return, Promotion, etc.)
├── persistence/     # File storage handlers and CSV/Text repository implementations
├── service/         # Business logic layer (SaleService, WarrantyService, ReturnService, etc.)
└── ui/              # Console user interface (ConsoleMenu) and application entry point (Main)
