# voltMoney

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