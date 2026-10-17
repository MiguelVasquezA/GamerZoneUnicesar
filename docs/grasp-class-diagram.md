# GRASP Audit - Class Diagram (Before Refactoring)

```mermaid
classDiagram
    direction TB

    %% Presentation Layer
    class ConsoleMenu {
        -Scanner scanner
        -SaleService saleService
        +displayMenu()
        +processSale()
        +generateReceipt()
    }

    class Main {
        +main(String[] args)
    }

    %% Service Layer
    class SaleService {
        -SaleRepository repository
        +processSale(Sale sale)
        +calculateTotal(List~Product~ products)
    }

    %% Model / Domain Layer
    class Sale {
        -String id
        -List~Product~ products
        -double totalAmount
        -double discountAmount
        -String appliedPromotionName
        +calculateTotal() double
        +generateReceipt() String
    }

    class Product {
        -String id
        -String name
        -double price
    }

    %% Persistence Layer
    class SaleRepository {
        -String filePath
        +save(Sale sale)
        +findAll() List~Sale~
    }

    %% Relationships
    Main ..> ConsoleMenu : instantiates
    Main ..> SaleService : instantiates
    Main ..> SaleRepository : instantiates
    ConsoleMenu --> SaleService : calls
    SaleService --> SaleRepository : tightly coupled to concrete
    SaleService --> Sale : manipulates
    Sale *-- "1..*" Product : contains direct list

    %% GRASP Violation Notes
    note for Sale "V-01 (Information Expert): Receives precalculated totals\nV-02 (Creator): List injected whole from outside\nV-04 (High Cohesion): Formats text receipt\nV-07 (Protected Variations): Direct list of Product instead of SaleItem"
    note for ConsoleMenu "V-03 (Controller): Orchestrates UI & system events directly\nV-05 (Pure Fabrication): Lacks helper parsers/printers"
    note for SaleService "V-06 (Indirection): Directly coupled to concrete SaleRepository"