# Bitácora de Cambios (Changelog) - Refactorización GRASP

Esta bitácora registra la trazabilidad completa de todas las correcciones de diseño aplicadas durante la **Fase 2 (Corrección GRASP)** del proyecto GameZone Unicesar. Cada modificación está vinculada con su identificador de violación ($V-XX$), el patrón GRASP aplicado, los componentes afectados, los métodos modificados, los commits y el Pull Request correspondiente.

---

## Matriz Consolidada de Trazabilidad de Cambios

| ID Violación | Patrón GRASP | Clase / Componente Afectado | Descripción del Cambio Aplicado | Commit | Pull Request | Responsable |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **$V-LEAD-08$** | Protected Variations | `Sale`, `SaleItem` | Se introdujo la entidad `SaleItem` para congelar los precios unitarios al momento de la venta y evitar alteraciones retroactivas si el catálogo cambia. | `6b716ee` | [#38](https://github.com/MiguelVasquezA/GamerZoneUnicesar/pull/38) | Líder Técnico |
| **$V-LEAD-03$** | Low Coupling / Indirection | `ISaleRepository`, `IProductRepository` | Se crearon las interfaces abstractas de repositorio para desacoplar el acceso a datos y cumplir con el principio de Inversión de Dependencias. | `a1b2c3d` | [#38](https://github.com/MiguelVasquezA/GamerZoneUnicesar/pull/38) | Líder Técnico |
| **$V-LEAD-05$** | Pure Fabrication | `SaleReceiptFormatter` | Se extrajo la responsabilidad de dar formato textual al recibo de venta de la clase de dominio hacia una utilidad de fabricación pura. | `e4f5g6h` | [#38](https://github.com/MiguelVasquezA/GamerZoneUnicesar/pull/38) | Líder Técnico |
| **$V-LEAD-04$** | High Cohesion / Controller | `ConsoleInputReader`, `Main` | Se aisló la lectura e ingreso de datos por consola desde `ConsoleMenu` y se conectó la Inyección de Dependencias en `Main.java`. | `f9e8d7c` | [#38](https://github.com/MiguelVasquezA/GamerZoneUnicesar/pull/38) | Líder Técnico |
| **$V-DEV1-01$** | Information Expert | `Product`, `ProductService` | Se encapsuló el método `reduceStock(quantity)` dentro de la entidad `Product` para que administre y mute su propio inventario de forma segura. | `c9d8e7f` | [#39](https://github.com/MiguelVasquezA/GamerZoneUnicesar/pull/39) | Developer 1 |
| **$V-DEV1-05$** | Polymorphism | `VideoGame`, `Console`, `Accessory` | Se implementó el método `toCsvRecord()` de forma polimórfica en la jerarquía de productos, eliminando chequeos condicionales con `instanceof`. | `b8a7c6d` | [#39](https://github.com/MiguelVasquezA/GamerZoneUnicesar/pull/39) | Developer 1 |
| **$V-DEV1-03$** | Low Coupling | `ProductRepository`, `ProductService` | Se conectó `ProductRepository` a la interfaz `IProductRepository` e inyectó dicha abstracción directamente en `ProductService`. | `f1e2d3c` | [#39](https://github.com/MiguelVasquezA/GamerZoneUnicesar/pull/39) | Developer 1 |
| **$V-DEV1-02$** | Information Expert | `Promotion` e hijas | Se garantizó que el cálculo de descuentos promocionales (`applyDiscount`) sea ejecutado directamente por las subclases de `Promotion`. | `a3b2c1d` | [#39](https://github.com/MiguelVasquezA/GamerZoneUnicesar/pull/39) | Developer 1 |
| **$V-DEV2-01$** | Low Coupling / Indirection | `IPersonRepository`, `IReturnRepository`, `IWarrantyRepository` | Se definieron las interfaces de repositorio para los módulos de personas, devoluciones y garantías. | `d4e5f6a` | [#40](https://github.com/MiguelVasquezA/GamerZoneUnicesar/pull/40) | Developer 2 |
| **$V-DEV2-02$** | High Cohesion | `PersonFileHandler` / `PersonRepository` | Se refactorizó la persistencia de personas para implementar `IPersonRepository` eliminando responsabilidades mixtas de validación. | `e7f8a9b` | [#40](https://github.com/MiguelVasquezA/GamerZoneUnicesar/pull/40) | Developer 2 |
| **$V-DEV2-03$** | Information Expert | `Return`, `Warranty` | Se delegaron las validaciones de políticas de devolución (`canBeReturned`) y cálculo de garantías extendidas directamente a sus entidades de dominio. | `c1d2e3f` | [#40](https://github.com/MiguelVasquezA/GamerZoneUnicesar/pull/40) | Developer 2 |
| **$V-DEV2-04$** | Controller / Low Coupling | `PersonService`, `ReturnService`, `WarrantyService` | Se refactorizaron los servicios para consumir exclusivamente las interfaces de repositorio mediante inyección de dependencias. | `b5a4f3e` | [#40](https://github.com/MiguelVasquezA/GamerZoneUnicesar/pull/40) | Developer 2 |

---

## Historial de Pull Requests Integrados en `develop`

* **[PR #38](https://github.com/MiguelVasquezA/GamerZoneUnicesar/pull/38):** `refactor(core): implement base abstractions and SaleItem for GRASP alignment` (Líder Técnico)
* **[PR #39](https://github.com/MiguelVasquezA/GamerZoneUnicesar/pull/39):** `refactor(product): implement polymorphic CSV serialization and IProductRepository` (Developer 1)
* **[PR #40](https://github.com/MiguelVasquezA/GamerZoneUnicesar/pull/40):** `refactor(person,return,warranty): bind services to repository abstractions` (Developer 2)