# Integrated System Class Diagram - GamerZone Unicesar

```mermaid
classDiagram
    %% MODEL LAYER
    class Product {
        <<abstract>>
        #String id
        #double price
        #int stock
        #String title
    }
    class VideoGame
    class Console
    class Accessory {
        <<abstract>>
        #List~String~ compatibleConsoles
    }
    class Controller
    class Cable
    class Memory

    class Sale {
        -String saleId
        -String client
        -String seller
        -String date
        -double totalAmount
        -double discountAmount
        -String appliedPromotionName
        -List~Product~ products
        +generateReceipt() String
    }

    class Promotion {
        <<abstract>>
        #String id
        #String name
        #LocalDate startDate
        #LocalDate endDate
        +calculateDiscount(Sale sale)* double
        +isActive(LocalDate date) boolean
    }
    class PercentageDiscount
    class CategoryDiscount
    class BulkPurchaseDiscount

    class Warranty {
        <<abstract>>
        #String id
        #Product product
        #Sale sale
        #LocalDate startDate
        #LocalDate endDate
        +getDurationInMonths()* int
        +getAdditionalCost()* double
        +isActive(LocalDate date) boolean
    }
    class BasicWarranty
    class ExtendedWarranty

    class Return {
        -String id
        -Sale sale
        -List~Product~ returnedItems
        -String reason
        -LocalDate returnDate
        -double refundAmount
        +calculateRefundAmount() double
        +generateReturnReceipt() String
    }

    %% INHERITANCE
    Product <|-- VideoGame
    Product <|-- Console
    Product <|-- Accessory
    Accessory <|-- Controller
    Accessory <|-- Cable
    Accessory <|-- Memory

    Promotion <|-- PercentageDiscount
    Promotion <|-- CategoryDiscount
    Promotion <|-- BulkPurchaseDiscount

    Warranty <|-- BasicWarranty
    Warranty <|-- ExtendedWarranty

    %% SERVICE LAYER
    class ProductService
    class AccessoryService
    class PromotionService
    class WarrantyService
    class SaleService
    class ReturnService

    %% PERSISTENCE LAYER
    class ProductRepository
    class AccessoryRepository
    class PromotionRepository
    class WarrantyRepository
    class SaleRepository
    class ReturnRepository

    %% UI LAYER
    class ConsoleMenu

    %% RELATIONSHIPS & DEPENDENCIES
    SaleService --> ProductService
    SaleService --> AccessoryService
    SaleService --> SaleRepository
    SaleService --> PromotionService
    SaleService --> WarrantyService

    ReturnService --> ReturnRepository
    ReturnService --> SaleService
    ReturnService --> ProductService
    ReturnService --> AccessoryService
    ReturnService --> WarrantyService

    WarrantyService --> WarrantyRepository
    WarrantyService --> SaleRepository
    WarrantyService --> ProductService

    ConsoleMenu --> ProductService
    ConsoleMenu --> AccessoryService
    ConsoleMenu --> SaleService
    ConsoleMenu --> PromotionService
    ConsoleMenu --> ReturnService
    ConsoleMenu --> WarrantyService