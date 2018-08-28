package com.surepayroll.ExtentReports
import com.aventstack.extentreports.*
import com.aventstack.extentreports.reporter.*;
import com.aventstack.extentreports.reporter.configuration.*
import org.testng.reporters.Files


public class ExtentManager {
    private static ExtentReports extent;
    private static String platform;
    private static String reportFileName = "Test-Automaton-Report.html";
    private static String macPath = System.getProperty("user.dir")+ "/TestReport";
    private static String windowsPath = System.getProperty("user.dir")+ "\\TestReport";
    private static String macReportFileLoc = macPath + "/" + reportFileName;
    private static String winReportFileLoc = windowsPath + "\\" + reportFileName;

    public static ExtentReports getInstance() {
        if (extent == null)
            createInstance();
        return extent;
    }

    //Create an extent report instance
    public static ExtentReports createInstance() {
        platform = getCurrentPlatform();
        String fileName = getReportFileLocation(platform);
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle("Test-Automation-Report");
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName("Test-Automation-Report");

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        return extent;
    }

    public static copyFile(String ReportLocation,String ReportFileName)
    {
        createReportPath(ReportLocation)
        ReportLocation=ReportLocation+"\\"+ReportFileName
      FileInputStream ff=new FileInputStream(winReportFileLoc)
        File target=new File(ReportLocation)
        Files.copyFile(ff,target)

    }

    //Select the extent report file location based on platform
    private static String getReportFileLocation (String platform) {
        String reportFileLocation = null;
        switch (platform) {
            case "MAC":
                reportFileLocation = macReportFileLoc;
                createReportPath(macPath);
                System.out.println("ExtentReport Path for MAC: " + macPath + "\n");
                break;
            case "WINDOWS":
                reportFileLocation = winReportFileLoc;
                createReportPath(windowsPath);

                System.out.println("ExtentReport Path for WINDOWS: " + windowsPath + "\n");
                break;
            default:
                System.out.println("ExtentReport path has not been set! There is a problem!\n");
                break;
        }
        return reportFileLocation;
    }

    //Create the report path if it does not exist
    private static void createReportPath (String path) {
        File testDirectory = new File(path);
        if (!testDirectory.exists()) {
            if (testDirectory.mkdir()) {
                System.out.println("Directory: " + path + " is created!" );
            } else {
                System.out.println("Failed to create directory: " + path);
            }
        } else {
            System.out.println("Directory already exists: " + path);
        }
    }

    //Get current platform
    private static String getCurrentPlatform () {
        if (platform == null) {
            String operSys = System.getProperty("os.name").toLowerCase();
            if (operSys.contains("win")) {
                platform = "WINDOWS";

            } else if (operSys.contains("nix") || operSys.contains("nux")
                    || operSys.contains("aix")) {
                platform = "LINUX";
            } else if (operSys.contains("mac")) {
                platform = "MAC";
            }
        }
        return platform;
    }
}