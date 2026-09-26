# Accessory Module Class Diagram

The following diagram illustrates the integration of the new accessory module into the GameZone Unicesar architecture, showing inheritance, layer dependencies, and console compatibility relationships.

```mermaid
classDiagram
    %% Model Layer
    class Product {
        <<abstract>>
        - String id
        - String title
        - double price
        - int stockQuantity
        + getId() String
        + getTitle() String
        + getPrice() double
        + getStockQuantity() int
        + setStockQuantity(int quantity) void
        + getDescription()* String
    }

    class Accessory {
        <<abstract>>
        - List~String~ compatibleConsoleIds
        + getCompatibleConsoleIds() List~String~
        + isCompatibleWith(String consoleId) boolean
        + addCompatibleConsole(String consoleId) void
    }

    class Controller {
        - String connectionType
        + getConnectionType() String
        + setConnectionType(String connectionType) void
        + getDescription() String
    }

    class Cable {
        - double length
        - String connectorType
        + getLength() double
        + getConnectorType() String
        + getDescription() String
    }

    class Memory {
        - int capacity
        - String memoryType
        + getCapacity() int
        + getMemoryType() String
        + getDescription() String
    }

    class Sale {
        - String id
        - List~Product~ items
        - double totalAmount
        + addItem(Product item) void
        + calculateTotal() double
    }

    %% Persistence Layer
    class AccessoryRepository {
        - String filePath
        + saveAll(List~Accessory~ accessories) void
        + loadAll() List~Accessory~
    }

    %% Service Layer
    class AccessoryService {
        - AccessoryRepository accessoryRepository
        + registerController(String title, double price, int stock, String connType, List~String~ consoleIds) Controller
        + registerCable(String title, double price, int stock, double length, String connType) Cable
        + registerMemory(String title, double price, int stock, int capacity, String memType, List~String~ consoleIds) Memory
        + listAllAccessories() List~Accessory~
        + listAccessoriesByType(String type) List~Accessory~
        + findAccessoriesCompatibleWith(String consoleId) List~Accessory~
        + findById(String id) Accessory
        + updateStock(String accessoryId, int quantity) void
    }

    class SaleService {
        - ProductService productService
        - AccessoryService accessoryService
        + registerSale(List~Product~ items) Sale
    }

    %% UI Layer
    class ConsoleMenu {
        - SaleService saleService
        - AccessoryService accessoryService
        + displayAccessoryMenu() void
        + displaySaleMenu() void
    }

    %% Inheritance Relationships
    Product <|-- Accessory
    Accessory <|-- Controller
    Accessory <|-- Cable
    Accessory <|-- Memory

    %% Module & Layer Dependencies
    Sale "1" *-- "*" Product : contains
    AccessoryRepository ..> Accessory : persists / loads
    AccessoryService --> AccessoryRepository : uses
    SaleService --> AccessoryService : delegates inventory update
    SaleService --> ProductService : delegates inventory update
    ConsoleMenu --> AccessoryService : calls
    ConsoleMenu --> SaleService : calls