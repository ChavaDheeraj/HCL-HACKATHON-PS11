# NinjaShop Project Plan And Design

## 1. Purpose

The goal of this project is to build a maintainable Selenium-based automation framework for the TutorialsNinja e-commerce demo application. The framework should support scalable functional testing, reusable page interactions, externalized test data, parallel execution, and clear reporting.

## 2. Project Objectives

- Automate high-value user journeys across the demo store
- Build a reusable Page Object Model based framework
- Support data-driven testing with Excel input
- Enable parallel-safe browser execution
- Produce readable execution reports with screenshots
- Keep test code separated from UI interaction logic

## 3. In-Scope Functional Areas

- User registration
- User login and logout
- Product search
- Category browsing
- Product detail validation
- Cart operations
- Checkout entry behavior
- Registration form validation

## 4. Out Of Scope For The Current Version

- Payment gateway testing
- Admin panel automation
- API automation
- Database validation
- Cross-environment deployment orchestration
- Performance and load testing

## 5. Current Architecture

### 5.1 Layers

The framework follows a layered test automation design:

1. Test Layer
   Contains business-level scenarios written with TestNG.

2. Page Layer
   Encapsulates UI locators, actions, and page-specific validations.

3. Core Framework Layer
   Handles configuration, driver lifecycle, reporting, screenshots, and shared utilities.

4. Test Data Layer
   Supplies structured input from Excel and runtime-generated user identities.

### 5.2 Main Components

- `ConfigReader`
  Loads runtime configuration from classpath properties.

- `DriverFactory`
  Creates and manages browser instances with `ThreadLocal` support for parallel tests.

- `BaseTest`
  Provides common setup, teardown, registration helpers, login helpers, and evidence capture.

- Page Objects
  Represent key application pages such as home, login, register, account, category, product, cart, search, and checkout.

- `ExcelUtils`
  Reads structured user records from the Excel workbook.

- `TestListener`
  Hooks into the TestNG lifecycle and publishes Extent results with screenshot evidence on failure.

## 6. Design Decisions

### 6.1 Page Object Model

Page Object Model is used to:

- reduce locator duplication
- simplify test maintenance
- keep assertions closer to business behavior
- allow tests to read like user journeys

### 6.2 ThreadLocal WebDriver

Parallel execution is enabled through `ThreadLocal<WebDriver>` so each test thread gets its own isolated browser instance.

### 6.3 Externalized Test Data

Excel is used for test-user seed data so testers can update input records without modifying Java test classes.

### 6.4 Runtime-Unique Users

Registration scenarios generate unique users from Excel-based templates to prevent failures caused by existing accounts on the live demo site.

### 6.5 Reporting Strategy

Two reporting paths are used:

- TestNG and Surefire default reports for execution detail
- Extent Reports for presentation-friendly HTML reporting with screenshot evidence

## 7. Test Suite Design

### 7.1 Authentication Suite

Focus:

- successful registration
- valid login
- invalid login
- logout behavior

### 7.2 Product Suite

Focus:

- keyword search
- category navigation
- product detail visibility
- empty search handling

### 7.3 Cart Suite

Focus:

- add-to-cart flow
- quantity update
- remove-from-cart flow
- header cart summary validation

### 7.4 Checkout Suite

Focus:

- checkout entry attempt as authenticated user
- checkout entry attempt as guest
- validation of stock-blocking behavior from the live demo environment

### 7.5 Validation Suite

Focus:

- required-field validation
- invalid email format validation

## 8. Execution Flow

1. TestNG starts the suite from `testng.xml`
2. Listener initializes reporting
3. `BaseTest` opens the configured browser and launches the home page
4. Test methods execute page interactions and assertions
5. Failures trigger screenshot capture through the listener
6. Browser closes after each method
7. Reports are flushed at suite completion

## 9. Risks And Constraints

- The target application is a public live demo site and may change without notice.
- Product availability and stock messages may vary over time.
- UI locator stability depends on the third-party demo application.
- Parallel execution on live environments may amplify flakiness if test data overlaps.
- Browser and driver version mismatch can break local execution if dependencies are stale.

## 10. Improvement Roadmap

### Phase 1: Stabilization

- review locator resilience across all page objects
- replace brittle flows with stronger waits where needed
- separate live-site constraints from actual functional defects

### Phase 2: Coverage Expansion

- add more negative authentication scenarios
- add search filter and sorting scenarios
- add cart edge cases such as zero quantity or multiple products
- add account management flows

### Phase 3: Execution Maturity

- support browser choice via Maven system properties
- add CI pipeline execution
- publish artifacts and HTML reports automatically
- introduce tagging for smoke and regression subsets

### Phase 4: Maintainability

- add utility wrappers for waits and common actions
- standardize assertion messages
- add framework-level logging
- document test data ownership and naming conventions

## 11. Recommended Next Deliverables

- CI-ready execution pipeline
- environment-aware configuration strategy
- more complete negative and edge-case coverage
- suite grouping for fast validation versus full regression
- clearer separation between framework code and business test data

## 12. Success Criteria

The project can be considered successful when:

- the suite runs reliably through Maven and TestNG
- core e-commerce flows are covered by reusable automated tests
- testers can update input data without code changes
- failures produce actionable reports and screenshots
- the framework remains easy to extend for additional user journeys
