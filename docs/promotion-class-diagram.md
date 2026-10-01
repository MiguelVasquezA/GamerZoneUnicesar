# Promotion Module Class Diagram

The following diagram illustrates the architecture and design patterns implemented for the **Promotions Engine** in GamerZone. It highlights the inheritance hierarchy of promotions, repository persistence, and integration into the service layer.

```mermaid
classDiagram
    class Promotion {
        <<abstract>>
        # String id
        # String name
        # LocalDate startDate
        # LocalDate endDate
        + getId() String
        + getName() String
        + getStartDate() LocalDate
        + getEndDate() LocalDate
        + isActive(LocalDate currentDate) boolean
        + calculateDiscount(Sale sale)* double
    }

    class PercentageDiscount {
        - double percentage
        + getPercentage() double
        + calculateDiscount(Sale sale) double
    }

    class CategoryDiscount {
        - double percentage
        - String targetCategory
        + getPercentage() double
        + getTargetCategory() String
        + calculateDiscount(Sale sale) double
    }

    class BulkPurchaseDiscount {
        - double percentage
        - int minQuantity
        + getPercentage() double
        + getMinQuantity() int
        + calculateDiscount(Sale sale) double
    }

    class PromotionRepository {
        - String filePath
        + loadAll() List~Promotion~
        + saveAll(List~Promotion~ promotions) void
    }

    class PromotionService {
        - PromotionRepository repository
        + registerPercentageDiscount(String id, String name, LocalDate start, LocalDate end, double percentage) void
        + registerCategoryDiscount(String id, String name, LocalDate start, LocalDate end, double percentage, String category) void
        + registerBulkPurchaseDiscount(String id, String name, LocalDate start, LocalDate end, double percentage, int minQuantity) void
        + listAllPromotions() List~Promotion~
        + listActivePromotions() List~Promotion~
        + findById(String id) Promotion
        + findBestPromotionFor(Sale sale) Promotion
    }

    class SaleService {
        - AccessoryService accessoryService
        - ProductService productService
        - SaleRepository saleRepository
        - PromotionService promotionService
        + registerSale(String customerId, String sellerId, List~Product~ items) Sale
    }

    Promotion <|-- PercentageDiscount
    Promotion <|-- CategoryDiscount
    Promotion <|-- BulkPurchaseDiscount

    PromotionService --> PromotionRepository : uses
    PromotionService ..> Promotion : manages
    SaleService --> PromotionService : evaluates promotions via