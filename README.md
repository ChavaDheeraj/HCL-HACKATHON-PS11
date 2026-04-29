# NinjaShop Automation Framework

This project is a Java Selenium automation framework for the TutorialsNinja demo store. It uses TestNG for execution, Maven for build management, Apache POI for Excel-driven test data, and Extent Reports for execution reporting.

The framework validates core e-commerce flows including registration, login, search, product viewing, cart operations, checkout blocking behavior on the live demo site, and form validation scenarios.

## Tech Stack

- Java 11
- Maven
- Selenium WebDriver 4.21.0
- TestNG 7.10.2
- WebDriverManager 5.8.0
- Apache POI 5.2.5
- Extent Reports 5.1.1

## Project Structure

```text
NinjaShop-main/
|-- pom.xml
|-- testng.xml
|-- src/
|   |-- main/java/com/srm/ninjashop/
|   |   |-- config/
|   |   |-- driver/
|   |   |-- listeners/
|   |   |-- pages/
|   |   `-- utils/
|   `-- test/
|       |-- java/com/srm/ninjashop/
|       |   |-- model/
|       |   |-- tests/
|       |   `-- utils/
|       `-- resources/
|           |-- config.properties
|           `-- NinjaShop.xlsx
|-- build/
|-- reports/
`-- screenshots/
```

## Framework Design

- `DriverFactory` manages browser startup and teardown using `ThreadLocal<WebDriver>` for parallel-safe execution.
- `ConfigReader` loads browser, base URL, timeout, headless mode, and test data file configuration from `config.properties`.
- Page Object Model is used to isolate locators and page behavior from test logic.
- `BaseTest` centralizes setup, teardown, reusable user registration, login helpers, and screenshot capture.
- `ExcelUtils` reads registration and login data from `src/test/resources/NinjaShop.xlsx`.
- `TestListener` integrates TestNG with Extent Reports and failure screenshots.

## Test Coverage

### Authentication

- User registration using Excel-backed data
- Valid and invalid login scenarios using `@DataProvider`
- Logout verification

### Product

- Keyword search validation
- Top category navigation
- Product detail validation
- Empty search message validation

### Cart

- Add product to cart
- Update cart quantity
- Remove product from cart
- Header cart count validation

### Checkout

- Logged-in checkout attempt
- Guest checkout attempt
- Verification of stock-blocking behavior shown by the live demo site

### Validation

- Empty registration form validation
- Invalid email format validation

## Configuration

The framework reads test configuration from `src/test/resources/config.properties`.

Current values in the project:

```properties
browser=chrome
baseUrl=https://tutorialsninja.com/demo
timeout=20
headless=false
testDataPath=NinjaShop.xlsx
```

You can change:

- `browser` to `chrome` or `firefox`
- `headless` to `true` for non-UI execution
- `timeout` to tune page load behavior

## How To Run

Run the full suite:

```bash
mvn clean test
```

Run with the configured TestNG suite:

```bash
mvn test
```

The suite definition is in `testng.xml` and currently runs:

- `AuthenticationTests`
- `ProductTests`
- `CartTests`
- `CheckoutTests`
- `ValidationTests`

## Outputs

Generated outputs include:

- `build/` for Maven build artifacts
- `build/surefire-reports/` for TestNG and Surefire reports
- `reports/` for Extent HTML reports
- `screenshots/` for evidence captured during selected scenarios and failures

## Assumptions And Notes

- The framework targets the live TutorialsNinja demo site, so some behaviors depend on current site data and stock availability.
- Checkout tests are intentionally written around the stock-block condition currently exposed by the live site.
- Excel test data is used as the input template, while unique users are generated at runtime to avoid duplicate registration failures.

## Suggested Improvements

- Add smoke, regression, and sanity TestNG groups
- Introduce explicit wait utility abstractions for more reusable synchronization
- Externalize browser and environment selection through Maven profiles or system properties
- Add CI execution with report publishing
- Add negative cart and checkout validation scenarios
- Add retry or resiliency strategy for unstable live-site behaviors
