# Technical Analysis - GameZoneUnicesar

## Project Overview
GameZoneUnicesar is a desktop application designed for managing inventory, customers, sellers, and sales transactions for a video game store, following a strict layered architecture pattern.

## Architectural Layers
The system is divided into four decoupled layers to maintain a clean separation of concerns:
1. **Model Layer (`com.gamezone.model`)**: Contains abstract and concrete business entities (`Product`, `VideoGame`, `Console`, `Person`, `Customer`, `Seller`, `Sale`). It contains **no business logic** or file-handling routines. All attributes are strictly **private** and accessed via getters and setters.
2. **Persistence Layer (`com.gamezone.persistence`)**: Handles data storage and retrieval via file handlers (`ProductFileHandler`, `PersonFileHandler`, `SaleFileHandler`). It interacts directly with the Model layer.
3. **Services Layer (`com.gamezone.services`)**: Contains the core business logic and rules of the application (`ProductService`, `PersonService`, `SaleService`). It acts as a bridge between the UI and Persistence layers.
4. **UI Layer (`com.gamezone.ui`)**: Manages user interaction through a console-based interface (`ConsoleMenu`, `Main`). It depends solely on the Services layer.

## Design Constraints & Rules
- **Encapsulation**: All class attributes across entities are set to private (`-`).
- **Inheritance & Polymorphism**: `Product` and `Person` are abstract classes with specialized subclasses utilizing polymorphic methods like `getDescription()`.
- **Strict Dependencies**: The UI layer can only call Services; Services can call Persistence or Model; Persistence handles Model data. No upward dependencies are permitted.