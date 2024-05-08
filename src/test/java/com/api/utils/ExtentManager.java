package com.api.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
    private static ExtentReports extent;


    public static ExtentReports getInstance() {
        if (extent == null) {
            String reportPath = System.getProperty("user.dir") + "/report/ExtentReport.html";
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

            // Title of the report
            sparkReporter.config().setDocumentTitle("Test automation API");

            // Name of the report
            sparkReporter.config().setReportName("Test automation Assessment API");

            // Encoding
            sparkReporter.config().setEncoding("utf-8");

            // Theme of the report (DARK or STANDARD)
            sparkReporter.config().setTheme(Theme.DARK);

            // Add charts (Virtual representation)
            sparkReporter.config().setTimelineEnabled(true);




            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);

            // Sample System Information
            extent.setSystemInfo("OS", "Windows");
            extent.setSystemInfo("Host Name", "API Automation");
            extent.setSystemInfo("Environment", "Test");
            extent.setSystemInfo("User", "Abdirahman");
        }
        return extent;
    }
}