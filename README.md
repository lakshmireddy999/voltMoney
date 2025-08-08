# voltMoney

This project is a UI Automation Framework built using Java, Selenium WebDriver, TestNG, and Maven.
It follows the Page Object Model (POM) design pattern to ensure maintainability, scalability, and reusability of test code.

🚀 Key Features
    1.Page Object Model (POM) design for structured and maintainable code.

    2.Reusable Utilities & Helper Functions for common actions like waits, element interactions, and validations.

    3.Custom Logging using Log4j for better debugging and execution tracking.

    4.Automated Reporting with Extent Reports for visually rich test execution results.

    5.Screenshot Capture on Failures to assist in quick defect analysis.

    6.Configurable Execution via Maven command:

🛠 Tech Stack
    1.Language: Java

    2.Test Framework: TestNG

    3.Automation Tool: Selenium WebDriver

    4.Build Tool: Maven

    5.Reporting: Extent Reports

    6.Logging: Log4j

Reporting
 - Test execution reports are automatically generated in HTML format using Extent Reports.
 - Screenshots are attached to failed test cases for easier debugging.

## 🛠 Prerequisites

- Java 17 or later installed
- Maven 3.8+ installed
- Google Chrome (latest stable) for Selenium tests
- ChromeDriver managed by WebDriverManager (already in project)

## ▶ Running the Test Suite

### 1. Run with default `testng1.xml`

```bash
mvn test -D testng.suite.xml=testng1 -D maven.test.failure.ignore=true

## Reports
 - Reports can be found in extent-report/

 ## headlessMode
 - headlessmode value options are available at Configuration/config.properties file
