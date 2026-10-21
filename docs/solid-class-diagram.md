# SOLID Refactoring Class Diagrams

This document contains the class diagrams representing the system architecture **Before** and **After** the SOLID principles refactoring phase (Requirement 8).

---

## 1. Class Diagram - Before Refactoring (Phase 1 State)

```mermaid
classDiagram
    class Main {
        +main(args: String[])
    }

    class ConsoleMenu {
        -SaleService saleService
        -ProductService productService
        -AccessoryService accessoryService
        -ReturnService returnService
        -WarrantyService warrantyService
        +start()
        +handleOption(option: int)
        +registerSale()
        +registerReturn()
    }

    class SaleService {
        -SaleRepository saleRepository
        -ProductService productService
        -AccessoryService accessoryService
        -WarrantyService warrantyService
        -PersonService personService
        +registerSale(sale: Sale)
        +calculateTotalWithPromotions(sale: Sale)
        +getSales()
    }

    class ProductService {
        -ProductRepository productRepository
        +getProductById(id: String)
        +updateStock(id: String, qty: int)
        +restoreStock(id: String, qty: int)
    }

    class SaleRepository {
        -String filePath
        +save(sale: Sale)
        +loadAll()
    }

    class ProductRepository {
        -String filePath
        +saveAll(products: List)
        +loadAll()
        +findProductById(id: String)
    }

    class Sale {
        -String id
        -List items
        -double totalAmount
        +calculateTotal()
        +addItem(item: SaleItem)
    }

    Main --> ConsoleMenu : instantiates
    Main --> SaleService : instantiates
    ConsoleMenu ..> SaleService : directly couples (V-04, V-06)
    ConsoleMenu ..> ProductService : directly couples (V-04, V-06)
    SaleService ..> SaleRepository : direct dependency (V-05)
    SaleService ..> ProductService : handles stock directly
    SaleService ..> Sale : calculates discounts internally (V-01, V-03)
    ProductService ..> ProductRepository : direct dependency (V-08)
    ProductRepository ..> ProductRepository : mixes parsing and storage (V-07)

    note for ConsoleMenu "Violations: V-02 (SRP - CLI I/O mix), V-04 (ISP), V-06 (DIP)"
    note for SaleService "Violations: V-01 (SRP - discount & warranty calculations), V-03 (OCP - hardcoded promotion checks), V-05 (DIP)"
    note for ProductService "Violations: V-08 (DIP - concrete repository)"
    note for ProductRepository "Violations: V-07 (SRP - parsing + file I/O)"