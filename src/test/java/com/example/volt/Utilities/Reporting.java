package com.example.volt.Utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewName;
import com.example.volt.Helper.LoggerHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.javafaker.Faker;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.testng.IClass;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.*;

public class Reporting implements ITestListener {
    private static final ThreadLocal<ExtentTest> extentTestThreadLocal = new ThreadLocal<>();
    private static final String reportDirectory = System.getProperty("user.dir") + "/extent-report/";
    private static final ExtentReports extentReports;
    private static final Logger logger;
    private static final Set<String> failedAndSkippedTests;
    private static final ThreadLocal<Map<String, Object>> resultJson = new ThreadLocal<>();
    private static final List<Map<String, Object>> jsonArray = new ArrayList<>();
    private static final String reportName = (new Faker()).number().digits(15);

    static {
        String consolidatedReportPath = reportDirectory + "Report_" + reportName + ".html";
        FileHelper.clearDirectory(reportDirectory);
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(consolidatedReportPath);
        htmlReporter.viewConfigurer().viewOrder()
                .as(new ViewName[] { ViewName.DASHBOARD, ViewName.CATEGORY, ViewName.TEST, ViewName.EXCEPTION })
                .apply();
        htmlReporter.config().setDocumentTitle("Automation Test Report");
        htmlReporter.config().setReportName("Volt Money");
        htmlReporter.config().setEncoding("UTF-8");
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");
        extentReports = new ExtentReports();
        extentReports.setSystemInfo("OS", System.getProperty("os.name"));
        extentReports.setSystemInfo("Java", System.getProperty("java.version"));
        extentReports.attachReporter(htmlReporter);
        logger = LoggerHelper.getLogger(Reporting.class);
        failedAndSkippedTests = new HashSet<>();
    }

    @Override
    public synchronized void onTestStart(ITestResult result) {
        String parametersFragment = "";
        if (result.getParameters() != null && result.getParameters().length > 0) {
            parametersFragment += " [" + getParameters(result) + "]";
        }
        String className = getTrimmedClassName(result.getTestClass());
        logger.info("*** Starting : " + className + " ***");
        StringBuilder testName = new StringBuilder();

        testName = new StringBuilder(className + " -> " + result.getMethod().getMethodName() + parametersFragment);
        ExtentTest extentTest = extentReports.createTest(testName.toString());
        resultJson.set(new HashMap<>());
        resultJson.get().put("test", testName);
        resultJson.get().put("class", result.getTestClass().getName());
        resultJson.get().put("startTime", new SimpleDateFormat("MM.dd.yyyy hh:mm:ss a").format(new Date()));
        String category = result.getTestClass().getName().substring(28,
                result.getTestClass().getName().lastIndexOf("."));
        extentTest.assignCategory(category);
        resultJson.get().put("category", category);
        extentTestThreadLocal.set(extentTest);

    }

    @Override
    public synchronized void onTestSuccess(ITestResult result) {
        logger.info("*** Passed: " + getTrimmedClassName(result.getTestClass()) + " ***");
        extentTestThreadLocal.get().pass(MarkupHelper.createLabel("Test Case Passed", ExtentColor.GREEN));
        resultJson.get().put("status", "Pass");
        resultJson.get().put("endTime", new SimpleDateFormat("MM.dd.yyyy hh:mm:ss a").format(new Date()));
        jsonArray.add(resultJson.get());
    }

    @Override
    public synchronized void onTestFailure(ITestResult result) {
        failedAndSkippedTests.add(result.getTestClass().getName());
        logger.info("*** Failed: " + getTrimmedClassName(result.getTestClass()) + " ***");
        extentTestThreadLocal.get().generateLog(Status.FAIL, "");
        resultJson.get().put("status", "Fail");
        resultJson.get().put("endTime", new SimpleDateFormat("MM.dd.yyyy hh:mm:ss a").format(new Date()));
        String consoleLogs = "";
        WebDriver driver = getDriver(result);
        if (isBrowserActive(driver)) {
            String screenshotPath = captureScreenshot(driver);
            resultJson.get().put("screenshot", screenshotPath);
            extentTestThreadLocal.get().addScreenCaptureFromPath(screenshotPath);
            consoleLogs = captureConsoleLogs(driver);
        }
        extentTestThreadLocal.get().info(result.getThrowable());
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        result.getThrowable().printStackTrace(pw);
        resultJson.get().put("error", sw.getBuffer());
        if (!consoleLogs.isEmpty()) {
            extentTestThreadLocal.get().info(MarkupHelper.createCodeBlock(consoleLogs));
        }
        jsonArray.add(resultJson.get());
    }

    @Override
    public synchronized void onTestSkipped(ITestResult result) {
        failedAndSkippedTests.add(result.getTestClass().getName());
        logger.info("*** Skipped: " + getTrimmedClassName(result.getTestClass()) + " ***");
        extentTestThreadLocal.get().generateLog(Status.SKIP, "");
        resultJson.get().put("status", "Skip");
        resultJson.get().put("endTime", new SimpleDateFormat("MM.dd.yyyy hh:mm:ss a").format(new Date()));
        String consoleLogs = "";
        WebDriver driver = getDriver(result);
        if (isBrowserActive(driver)) {
            String screenshotPath = captureScreenshot(driver);
            resultJson.get().put("screenshot", screenshotPath);
            extentTestThreadLocal.get().addScreenCaptureFromPath(screenshotPath);
            consoleLogs = captureConsoleLogs(driver);
        }
        extentTestThreadLocal.get().info(result.getThrowable());
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        result.getThrowable().printStackTrace(pw);
        resultJson.get().put("error", sw.getBuffer());
        if (!consoleLogs.isEmpty()) {
            extentTestThreadLocal.get().info(MarkupHelper.createCodeBlock(consoleLogs));
        }
    }

    @Override
    public synchronized void onFinish(ITestContext context) {
        if (!failedAndSkippedTests.isEmpty()) {
            logger.info("Failed And Skipped Tests:");
            for (String failedOrSkippedTest : failedAndSkippedTests) {
                logger.info(failedOrSkippedTest);
            }
        }
        extentReports.flush();
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writeValue(new File(reportDirectory + "Report_" + reportName + ".json"), jsonArray);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Extracts class name from IClass object
    private synchronized String getTrimmedClassName(IClass testClass) {
        String className = testClass.getName();
        int lastDotIndex = className.lastIndexOf(".");
        if (lastDotIndex != -1 && lastDotIndex < className.length() - 1) {
            className = className.substring(lastDotIndex + 1);
        }
        return className;
    }

    private synchronized String captureScreenshot(WebDriver driver) {
        String screenshotFileName = null;
        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            byte[] screenshotBytes = screenshot.getScreenshotAs(OutputType.BYTES);
            screenshotFileName = "screenshot_" + (new Faker()).number().digits(10) + ".png";
            FileUtils.writeByteArrayToFile(new File("extent-report/Screenshots/" + screenshotFileName),
                    screenshotBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Screenshots/" + screenshotFileName;
    }

    private synchronized String captureConsoleLogs(WebDriver driver) {
        LogEntries logEntries;
        try {
            logEntries = driver.manage().logs().get("browser");
        } catch (UnsupportedCommandException e) {
            return "Browser does not support console log capture.";
        }
        StringBuilder consoleLog = new StringBuilder();
        for (LogEntry log : logEntries.getAll()) {
            consoleLog.append(log.toString()).append("\n");
        }
        return String.valueOf(consoleLog);
    }

    private synchronized WebDriver getDriver(ITestResult result) {
        WebDriver driver = null;
        try {
            driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return driver;
    }

    private synchronized String getParameters(ITestResult result) {
        StringBuilder paramValues = new StringBuilder();
        for (Object params : result.getParameters()) {
            paramValues.append(params.toString()).append(",");
        }
        return paramValues.substring(0, paramValues.length() - 1);
    }

    public boolean isBrowserActive(WebDriver driver) {
        try {
            return driver != null && driver.getTitle() != null;
        } catch (Exception e) {
            return false;
        }
    }
}