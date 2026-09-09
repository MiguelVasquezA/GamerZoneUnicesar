# Technical Analysis - GameZoneUnicesar

## Project Overview
GameZoneUnicesar is a desktop application designed for managing inventory, customers, sellers, and sales transactions for a video game store, following a strict layered architecture pattern.

## Architectural Layers
The system is divided into four decoupled layers to maintain a clean separation of concerns:
1. **Model Layer (`com.gamezone.model`)**: Contains abstract and concrete business entities (`Product`, `VideoGame`, `Console`, `Person`, `Customer`, `Seller`, `Sale`). It contains **no business logic** or file-handling routines. All attributes are strictly **private** and accessed via public getters and setters.
2. **Persistence Layer (`com.gamezone.persistence`)**: Handles data storage and retrieval via repository classes (`ProductRepository`, `PersonRepository`, `SaleRepository`). It interacts directly with the Model layer.
3. **Service Layer (`com.gamezone.service`)**: Contains the core business logic and rules of the application (`ProductService`, `PersonService`, `SaleService`). It acts as a bridge between the UI and Persistence layers.
4. **UI Layer (`com.gamezone.ui`)**: Manages user interaction through a console-based interface (`MainConsoleUI`). It depends solely on the Service layer.

## Guided Analysis Questions

### People in the System
1. **Common & Specific Attributes:** Common attributes (`id`, `name`, `phone`) are defined in the base class `Person`. Specific attributes like `email` and `purchaseHistory` belong to `Customer`, while `employeeCode` and `shift` belong to `Seller`. This distinction is modeled using generalization/specialization inheritance.
2. **Generic Person Class:** A generic `Person` should not be instantiated because it represents an abstract entity in the business domain. Therefore, `Person` is declared as an `abstract` class to prevent direct instantiation.

### Products in the System
3. **Product Characteristics:** Common attributes (`id`, `title`, `price`, `stock`) are placed in the base class `Product`. `VideoGame` includes specific attributes (`platform`, `genre`, `rating`), whereas `Console` adds (`brand`, `model`, `generation`).
4. **Polymorphic Behavior:** The behavior to generate descriptions is declared as an `abstract` method `getDescription()` in the base `Product` class. Subclasses override this method to provide custom implementations, leveraging Object-Oriented Polymorphism.

### Sales & Entity Relationships
5. **Relationships:** A `Sale` has an **association** with `Customer` and `Seller`, and an **aggregation** relationship with a collection of `Product` objects, as products exist independently of individual sales.
6. **Total Calculation:** The `Sale` class is responsible for calculating its total price by summing the individual prices of its associated products, ensuring internal data cohesion.

### Business Constraints
7. **Minimum Product Constraint:** Validated within `SaleService` prior to processing the transaction, ensuring that the product list is not empty before registering a sale.
8. **Inventory Auto-Update:** When a sale is registered, `SaleService` communicates with `ProductService` to decrement stock levels in `ProductRepository` for each sold item.

### Layered Architecture Rules
9. **Layer Responsibilities:**
    - **Model:** Defines pure data structures and entities.
    - **Persistence:** Handles reading and writing data files.
    - **Service:** Encapsulates business logic and validations.
    - **UI:** Manages console input and display menus.
10. **Persistence Isolation:** Keeping file I/O out of domain entities enforces the Single Responsibility Principle and keeps model classes testable and reusable.
11. **Allowed Dependencies:** Top-down execution flow: `UI` $\rightarrow$ `Service` $\rightarrow$ `Persistence` & `Model`. Upward or circular dependencies are strictly forbidden.