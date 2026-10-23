# GamerZone Unicesar - Integrated Management System

An object-oriented Java console application designed to manage products, accessories, customers, sellers, sales, promotional discounts, warranties, product returns, and a cross-cutting validation and exception-handling framework under a clean 4-layer architecture (Service-Repository Pattern).

---

## Integrated System Features

- **Product & Accessory Management**: Support for Video Games, Consoles, and Accessories (Controllers, Cables, Memory units) with real-time console compatibility checks.
- **Person Registry**: Unified entity management distinguishing Customers and Sellers.
- **Promotions Engine**:
  - **General Percentage Discount**: Universal percentage discount applied across items (`PercentageDiscount`).
  - **Category Discount**: Targeted percentage discount for Video Games, Consoles, or Accessories (`CategoryDiscount`).
  - **Bulk Purchase Discount**: Volume-based discounts triggered when buying a minimum item count (`BulkPurchaseDiscount`).
  - **Best Promotion Rule**: Evaluates active promotions dynamically on sales subtotals and applies the one yielding maximum savings (`findBestPromotionFor`).
- **Unified Sales Processing**:
  - Sequential execution pipeline: stock validation, subtotal calculation, promotion evaluation, warranty issuance, and total calculation.
  - Formatted English sale receipts displaying subtotal, applied promotion name/discount, warranty fees, and final total.
- **Warranty Module**:
  - **Basic Warranty**: Automatic 6-month coverage at no extra cost for console purchases.
  - **Extended Warranty**: Optional 12-month coverage covering accidental damage (10% additional fee).
  - Interactive queries for active warranties, expiring warranties (30 days), and search by Product/Sale ID.
- **Returns & Financials**:
  - Proportional refund calculations on discounted sales preventing revenue losses.
  - Automatic inventory restoration for products and accessories.
  - Automated warranty revocation on returned consoles.
  - Financial reporting generating monthly gross sales, total returns, and net balance.
- **Robust Exception Handling & Validation (Requirement 6)**:
  - Custom domain exception hierarchy with standardized system error codes (`GZ-ERR-XXX`).
  - Decoupled, dedicated validation components enforcing pre-conditions, stock limits, and unique identification constraint checks.
  - Polymorphic exception translation and central catch handling ensuring continuous, error-resilient console UI execution.

## Requerimiento 7: Auditoría y Cumplimiento SOLID (Code Review)
- **Análisis y Refactorización Estructural:** Auditoría de código sobre las clases del modelo y servicios para identificar violaciones a los principios SOLID.
- **Correcciones Principales Aplicadas:**
  - **Single Responsibility Principle (SRP):** Desacoplamiento de la lógica de presentación/formateo de comprobantes fuera de los modelos (`Return`, `Warranty`) hacia formateadores o servicios dedicados.
  - **Open/Closed Principle (OCP):** Extensibilidad mediante jerarquías abstractas e interfaces en módulos de promociones, garantías y accesorios sin modificar el código base.
  - **Liskov Substitution Principle (LSP):** Garantía de sustitución correcta en las jerarquías de productos (`Accessory` / `Product`) y garantías (`BasicWarranty` / `ExtendedWarranty`).
  - **Dependency Inversion Principle (DIP):** Inyección de dependencias de repositorios y servicios a través de sus construcciones en lugar de instanciaciones rígidas.

---

## Requerimiento 8: Despliegue y Pruebas Integradas (CI/CD)
- **Persistencia Binaria / Objeto Serializable:** Implementación completa de la interfaz `java.io.Serializable` en todas las entidades del dominio (`SaleItem`, `Return`, `Warranty`, `BasicWarranty`, `ExtendedWarranty`) para asegurar la persistencia en archivos planos/binarios sin errores de I/O.
- **Manejo de Transacciones e Integración Final:**
  - Sincronización automática del ciclo de vida entre Ventas, Garantías y Devoluciones.
  - Anulación en cascada de garantías activas al procesar devoluciones parciales o totales de ventas.
  - Restauración automática e inteligente de inventario para productos generales y accesorios en sus respectivos servicios.
  - Generación de reportes consolidados y balance financiero neto mensual (Ventas totales vs. Reembolsos).
- **Pruebas de Flujo Completo (E2E):** Cobertura de escenarios integrados desde el menú interactivo por consola en `ConsoleMenu.java`.

---

## Layered Architecture & Project Structure

The project strictly follows the required **UI → Service → Persistence → Model** dependency hierarchy alongside dedicated validation and custom exception packages:

```text
src/main/java/com/gamezone/
├── exceptions/      # Custom domain exception hierarchy (GameZoneException, ResourceNotFoundException, etc.)
├── model/           # Business domain entities (Product, Sale, Warranty, Return, Promotion, etc.)
├── persistence/     # File handlers, text/CSV repositories, and data storage logic
├── service/         # Core business logic layer (SaleService, ReturnService, WarrantyService, etc.)
├── validation/      # Dedicated static validation classes (ProductValidator, PersonValidator, SaleValidator)
└── ui/            # Interactive console interface (ConsoleMenu) and application entry point (Main)
