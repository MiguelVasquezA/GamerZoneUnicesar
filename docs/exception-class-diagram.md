# Exception and Validation Class Diagram

```mermaid
classDiagram
    class Throwable {
        <<Java API>>
    }

    class Exception {
        <<Java API>>
    }

    class RuntimeException {
        <<Java API>>
    }

    Throwable <|-- Exception
    Exception <|-- RuntimeException

    class GameZoneException {
        -String errorCode
        +GameZoneException(String message)
        +GameZoneException(String message, String errorCode)
        +GameZoneException(String message, String errorCode, Throwable cause)
        +getErrorCode() String
    }

    RuntimeException <|-- GameZoneException

    class ResourceNotFoundException {
        +ResourceNotFoundException(String resourceType, String resourceId)
    }

    class BusinessRuleException {
        +BusinessRuleException(String message)
    }

    class InvalidDataException {
        +InvalidDataException(String fieldName, String description)
    }

    class PersistenceException {
        +PersistenceException(String message, Throwable cause)
    }

    GameZoneException <|-- ResourceNotFoundException
    GameZoneException <|-- BusinessRuleException
    GameZoneException <|-- InvalidDataException
    GameZoneException <|-- PersistenceException

    class ProductValidator {
        +validateProductData(String id, String title, double price, int stock)$
        +validateProductExists(Product product, String productId)$+validateStockAvailability(Product product, int requestedQuantity)$
    }

    class PersonValidator {
        +validatePersonData(String id, String name, String phone)$
        +validatePersonExists(Person person, String personId)$+validateUniquePerson(List~Person~ existing, String newId)$
    }

    class SaleValidator {
        +validateSaleData(Object customer, Object seller, List~Product~ products)$+validateSaleExists(Sale sale, String saleId)$
    }

    ProductValidator ..> InvalidDataException
    ProductValidator ..> ResourceNotFoundException
    ProductValidator ..> BusinessRuleException

    PersonValidator ..> InvalidDataException
    PersonValidator ..> ResourceNotFoundException
    PersonValidator ..> BusinessRuleException

    SaleValidator ..> InvalidDataException
    SaleValidator ..> ResourceNotFoundException
    SaleValidator ..> BusinessRuleException