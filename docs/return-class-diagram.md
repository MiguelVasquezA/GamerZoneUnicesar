# Return Module Class Diagram

```mermaid
classDiagram
    class Return {
        -String id
        -LocalDate returnDate
        -Sale sale
        -List~Product~ returnedProducts
        -String reason
        -double refundAmount
        +Return(String id, LocalDate returnDate, Sale sale, List~Product~ returnedProducts, String reason)
        +calculateRefundAmount() double
        +generateReturnReceipt() String
        +getId() String
        +getReturnDate() LocalDate
        +getSale() Sale
        +getReturnedProducts() List~Product~
        +getReason() String
        +getRefundAmount() double
    }

    class Sale {
        -String id
        -String date
        -List~Product~ products
        -double total
        +canBeReturned() boolean
        +getId() String
        +getDate() String
    }

    class ProductService {
        -List~Product~ products
        +restoreStock(String productId, int quantity) boolean
        +reduceStock(String productId, int quantity) boolean
    }

    class ReturnService {
        -ReturnRepository returnRepository
        -SaleService saleService
        -ProductService productService
        +processReturn(...) Return
        +listAllReturns() List~Return~
    }

    class ReturnRepository {
        -String filePath
        +save(Return returnObj) boolean
        +findAll() List~Return~
    }

    Return "1" --> "1" Sale : references
    Return "1" --> "*" Product : contains
    ReturnService "1" --> "1" ReturnRepository : uses
    ReturnService "1" --> "1" ProductService : uses