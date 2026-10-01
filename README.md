# GamerZone Unicesar - Management System

An object-oriented Java console application designed to manage products, accessories, customers, sellers, sales, and promotional discounts under clean architecture principles (Service-Repository Pattern).

---

## 🚀 Features

* **Product Management**: Support for Video Games and Consoles.
* **Accessory Management**: Track Controllers, Cables, and Memory units with console compatibility checks.
* **Person Management**: Separate tracking for Customers and Sellers.
* **Promotions Engine**:
  * **General Percentage Discount**: Standard percentage off for all products (`PercentageDiscount`).
  * **Category Discount**: Targeted discounts applied specifically to games or consoles (`CategoryDiscount`).
  * **Bulk Purchase Discount**: Volume-based discounts applied when purchasing a minimum quantity of items (`BulkPurchaseDiscount`).
  * **Best Promotion Rule**: Automatically evaluates active promotions for a sale and applies the one that yields the maximum discount (`findBestPromotionFor`).
* **Sales Processing**: Full transaction flow that updates item inventory, calculates total amounts, applies active discounts, and prints a formatted receipt in English.

---

## 🛠️️ Architecture & Project Structure

The project follows a **Layered Service-Repository Architecture**:

```text
src/main/java/com/gamezone/
├── model/           # Business Domain Models (Sale, Promotion, Product, Person, etc.)
├── persistence/     # File handlers and repository data access logic
├── service/         # Core business logic layer (SaleService, PromotionService, etc.)
└── ui/              # Interactive Console UI (ConsoleMenu) and Main Entry Point
