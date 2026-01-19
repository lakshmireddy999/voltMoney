package com.example.volt.Utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewName;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class JsonsToExtentReport {
    public static void main(String[] args) throws IOException, ParseException {
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/extent-report/"
                + "ConsolidatedReport_" + System.currentTimeMillis() + ".html");
        htmlReporter.viewConfigurer().viewOrder()
                .as(new ViewName[] { ViewName.DASHBOARD, ViewName.CATEGORY, ViewName.TEST, ViewName.EXCEPTION })
                .apply();
        htmlReporter.config().setDocumentTitle("Automation Test Report");
        htmlReporter.config().setReportName("Volt-Money");
        htmlReporter.config().setEncoding("UTF-8");
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");
        htmlReporter.config().setTimelineEnabled(false);
        ExtentReports reports = new ExtentReports();
        reports.attachReporter(htmlReporter);

        Date earliestStartTime = new Date(Long.MAX_VALUE);

        Map<String, Map<String, String>> testsMap = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd.yyyy hh:mm:ss a");

        ObjectMapper objectMapper = new ObjectMapper();
        File folder = new File(System.getProperty("user.dir") + "/extent-report/");
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {
            for (File jsonFile : listOfFiles) {
                if (jsonFile.isFile() && jsonFile.getName().endsWith(".json")) {
                    JsonNode rootNode = objectMapper.readTree(jsonFile);
                    for (JsonNode node : rootNode) {
                        String test = node.get("test").asText();
                        String status = node.get("status").asText();
                        String category = node.get("category").asText();
                        String screenshot = node.has("screenshot") ? node.get("screenshot").asText() : null;
                        String error = node.has("error") ? node.get("error").asText() : null;
                        String startTime = node.get("startTime").asText();
                        String endTime = node.get("endTime").asText();

                        Map<String, String> testMap = new HashMap<>();
                        testMap.put("status", status);
                        testMap.put("category", category);
                        testMap.put("screenshot", screenshot);
                        testMap.put("error", error);
                        testMap.put("startTime", startTime);
                        testMap.put("endTime", endTime);

                        Date startTimeDT = dateFormat.parse(startTime);
                        earliestStartTime = startTimeDT.before(earliestStartTime) ? startTimeDT : earliestStartTime;

                        // If the test is not present or the status is "Pass" and the existing status is
                        // not "Pass"
                        if (!testsMap.containsKey(test)
                                || (status.equals("Pass") && !testsMap.get(test).get("status").equals("Pass"))) {
                            testsMap.put(test, testMap);
                        }
                    }
                }
            }
        }

        for (String test : testsMap.keySet()) {
            ExtentTest extentTest = reports.createTest(test);
            Map<String, String> testMap = testsMap.get(test);
            extentTest.assignCategory(testMap.get("category"));
            Status testResult = Arrays.stream(Status.values())
                    .filter(status -> status.getName().equalsIgnoreCase(testMap.get("status"))).findFirst()
                    .orElse(null);
            extentTest.generateLog(testResult, "");
            if (testMap.get("screenshot") != null) {
                extentTest.addScreenCaptureFromPath(testMap.get("screenshot"));
            }
            if (testMap.get("error") != null) {
                extentTest.info(MarkupHelper.createCodeBlock(testMap.get("error")));
            }
            extentTest.getModel().setStartTime(dateFormat.parse(testMap.get("startTime")));
            extentTest.getModel().setEndTime(dateFormat.parse(testMap.get("endTime")));
        }

        reports.getReport().setStartTime(earliestStartTime);
        reports.flush();
    }
}