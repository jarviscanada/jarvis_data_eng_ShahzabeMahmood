# Stock Quote App

## Introduction
The Stock Quote App is a terminal-based trading platform that allows users to simulate stock trading. It integrates with the **Alpha Vantage API** to fetch real-time and historical stock market data, supporting CRUD operations for stock quotes and user positions. The app uses **JDBC** to connect with a **PostgreSQL** database, **Maven** for build automation, and **Docker** for containerization to ensure easy deployment across environments.

## Implementation

### ER Diagram
![ER Diagram](./assets/er-diagram.png)

The **Entity-Relationship Diagram (ERD)** visualizes the relationships between the main components of the system:

- **StockQuote:** Represents real-time stock data, including stock symbols, prices, and other key metrics.
- **Position:** Represents the user's stock holdings, including the quantity and cost of each stock.
- **User:** Represents the user profiles in the system.

These entities are linked through foreign keys and are used to manage CRUD operations in the database.

## Design Patterns

The app implements two key design patterns: **DAO (Data Access Object)** and **Repository**.

- **DAO Pattern:** The DAO pattern abstracts the data access logic from the business logic. It encapsulates all the database interactions into separate DAO classes that provide methods for performing CRUD operations. This helps in ensuring that the business logic remains independent of the underlying database details. For example, the `QuoteDAO` handles operations related to stock data, while `PositionDAO` handles CRUD operations related to user positions.

- **Repository Pattern:** The repository pattern provides a higher-level abstraction over the DAO. It offers an interface through which the application interacts with the underlying data store. This separation allows for easy swapping of database technologies or adding new data sources without impacting the core logic of the application. The repository manages objects like `Quote` and `Position`, making it easy to retrieve, store, update, and delete the respective data.

Both patterns ensure the application is modular, maintainable, and easier to scale.

## Test

The app is tested using **JUnit** and **Mockito** for unit and integration testing. Here's how we test it:

1. **Database Setup:** A local instance of **PostgreSQL** is used for integration tests. The database schema is set up with tables for `Quote` and `Position`.
2. **Test Data Setup:** Before running tests, mock data is inserted into the database to ensure that there is meaningful data to query.
3. **Query Results:** The core functionality, such as fetching stock data from the database or creating new positions, is tested using queries to ensure that the data is correctly written, updated, and deleted from the database. **Mockito** is used to mock the database interactions during unit tests, allowing us to verify the logic without relying on a real database instance.

Unit tests are written for each DAO and service layer to verify individual components, while integration tests ensure that the entire system, including database interactions, functions correctly.

---
Feel free to explore, clone, or contribute to the project!
