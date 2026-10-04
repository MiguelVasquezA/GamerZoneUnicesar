# Class Diagram - Warranty Module Architecture

```mermaid
classDiagram
    class Warranty {
        <<abstract>>
        -String id
        -Product product
        -Sale sale
        -LocalDate startDate
        -LocalDate endDate
        +getDurationInMonths()* int
        +getWarrantyType()* String
        +getAdditionalCost()* double
        +isActive(LocalDate date) boolean
        +generateWarrantyCertificate() String
    }

    class BasicWarranty {
        +getDurationInMonths() int
        +getWarrantyType() String
        +getAdditionalCost() double
    }

    class ExtendedWarranty {
        +getDurationInMonths() int
        +getWarrantyType() String
        +getAdditionalCost() double
    }

    class WarrantyRepository {
        -String FILE_PATH
        +loadAll(List~Product~ products, List~Sale~ sales) List~Warranty~
        +saveAll(List~Warranty~ warranties) void
    }

    class WarrantyService {
        -WarrantyRepository warrantyRepository
        -ProductService productService
        -SaleService saleService
        -List~Warranty~ warranties
        +loadData() void
        +saveData() void
        +assignBasicWarranty(Product product, Sale sale, LocalDate startDate) BasicWarranty
        +assignExtendedWarranty(Product product, Sale sale, LocalDate startDate) ExtendedWarranty
        +findWarrantyByProduct(String productId, String saleId) Warranty
        +listAllWarranties() List~Warranty~
        +listActiveWarranties() List~Warranty~
        +listWarrantiesExpiringSoon(int daysAhead) List~Warranty~
    }

    Warranty <|-- BasicWarranty
    Warranty <|-- ExtendedWarranty
    WarrantyRepository "1" -- "many" Warranty : persists
    WarrantyService "1" o-- "1" WarrantyRepository : relies on
    WarrantyService "1" o-- "many" Warranty : manages