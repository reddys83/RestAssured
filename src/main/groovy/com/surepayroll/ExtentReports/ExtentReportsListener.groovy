package com.surepayroll.ExtentReports

import com.aventstack.extentreports.ExtentReports
import com.aventstack.extentreports.ExtentTest
import com.surepayroll.Utils.UtilityFactory
import org.testng.ISuite
import org.testng.ISuiteListener
import org.testng.ITestContext
import org.testng.ITestListener
import org.testng.ITestNGMethod
import org.testng.ITestResult
import org.testng.annotations.BeforeClass
import org.testng.xml.ISuiteParser

import java.text.DateFormat
import java.text.SimpleDateFormat;

public class ExtentReportsListener implements ITestListener,ISuiteListener {

    //Extent Report Declarations

    public static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static ThreadLocal<ITestNGMethod> currentMethods = new ThreadLocal<>();
    private static ThreadLocal parentTest = new ThreadLocal();
    public static String methodName;
    private static ExtentReports extent = ExtentManager.createInstance();
    private static int SuitesIterator = 1;
    HashMap<String,List<String>> tcMap=new HashMap<String,List<String>>();

//    @Override
//    public synchronized void onStart(ITestContext context) {
//
//
//
//    }
//
//    @Override
//    public synchronized void onFinish(ITestContext context) {
////        System.out.println(("Extent Reports is ending!"));
////
////        final DateFormat df = new SimpleDateFormat("MM-dd-yyyy_HH-mm-ss");
////        Date date = new Date();
////        String ReportFileName=context.getCurrentXmlTest().getName()+"_Report_"+UtilityFactory.testEnvironment.environment()+"_"+df.format(date)+".html"
////        String ReportLocation=UtilityFactory.testEnvironment.shared_Path()+"\\"+context.getCurrentXmlTest().getParameter("RootFolder")+"\\"+UtilityFactory.testEnvironment.environment()+"\\Reports"
////        String ReportLocation1=UtilityFactory.testEnvironment.shared_Path()+"\\Reports"
////        ExtentManager.copyFile(ReportLocation,ReportFileName)
////        ExtentManager.copyFile(ReportLocation1,ReportFileName)
////        extent.flush();
//    }
//
//    @Override
//    public synchronized void onFinish(ISuite context) {
//
//        if (context.getXmlSuite().m_parentSuite==null) {
//            extent.flush();
//        System.out.println(("Extent Reports is ending!"));
//        final DateFormat df = new SimpleDateFormat("MM-dd-yyyy_HH-mm-ss");
//        Date date = new Date();
//
//        String ReportFileName = context.getName() + "_Report_" + UtilityFactory.testEnvironment.environment() + "_" + df.format(date) + ".html"
//        String ReportLocation = UtilityFactory.testEnvironment.shared_Path() + "\\" + context.getParameter("RootFolder") + "\\" + UtilityFactory.testEnvironment.environment() + "\\Reports"
//        String ReportLocation1 = UtilityFactory.testEnvironment.shared_Path() + "\\Reports"
//        ExtentManager.copyFile(ReportLocation, ReportFileName)
//        ExtentManager.copyFile(ReportLocation1, ReportFileName)
//
//    }
//        SuitesIterator++;
//    }
//
//    @Override
//    public synchronized void onStart(ISuite context) {
//        System.out.println(("Extent Reports started!"));
//
//    }
//
//    @Override
//    public synchronized void onTestStart(ITestResult result) {
//        methodName=result.getMethod().getMethodName();
//        System.out.println((result.getMethod().getMethodName() + " started!"));
//        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(),result.getMethod().getDescription());
//        test.set(extentTest);
//    }
//
//    @Override
//    public synchronized void onTestSuccess(ITestResult result) {
//        System.out.println((result.getMethod().getMethodName() + " passed!"));
//        test.get().pass("Test passed");
//    }
//
//    @Override
//    public synchronized void onTestFailure(ITestResult result) {
//        System.out.println((result.getMethod().getMethodName() + " failed!"));
//        test.get().fail(result.getThrowable());
//
//    }
//
//    @Override
//    public synchronized void onTestSkipped(ITestResult result) {
//        System.out.println((result.getMethod().getMethodName() + " skipped!"));
//        test.get().skip(result.getThrowable());
//    }
//
//    @Override
//    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
//        System.out.println(("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName()));
//    }
//    public static String getCurrentTestName() {
//        return methodName;
//    }

//****************************************

    @Override
    public synchronized void onStart(ITestContext context) {
        ExtentTest parent = extent.createTest(context.getName());
        parentTest.set(parent);
    }

    @Override
    public synchronized void onFinish(ITestContext context) {
   //     extent.flush();
        System.out.println("hello")
    }

    @Override
    public synchronized void onTestStart(ITestResult result) {

        methodName=result.getMethod().getMethodName();
        ExtentTest child = parentTest.get().createNode(result.getMethod().getMethodName());
        test.set(child);
    }



    @Override
    public synchronized void onTestSuccess(ITestResult result) {
        test.get().pass("Test passed");
        tcMap.put(result.name,Arrays.asList("Passed"))
    }

    @Override
    public synchronized void onTestFailure(ITestResult result) {
        test.get().fail(result.getThrowable());
        tcMap.put(result.name,Arrays.asList("Failed","2","failed reason"))
    }

    @Override
    public synchronized void onTestSkipped(ITestResult result) {
        ExtentTest child = parentTest.get().createNode(result.getMethod().getMethodName());
        test.set(child);
        test.get().skip(result.getThrowable());


    }

    @Override
    public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }



    @Override
    public synchronized void onFinish(ISuite context) {

        if (context.getXmlSuite().m_parentSuite==null) {
            extent.flush();
        System.out.println(("Extent Reports is ending!"));
        final DateFormat df = new SimpleDateFormat("MM-dd-yyyy_HH-mm-ss");
        Date date = new Date();

        String ReportFileName = context.getName() + "_Report_" + UtilityFactory.testEnvironment.environment() + "_" + df.format(date) + ".html"
        String ReportLocation = UtilityFactory.testEnvironment.shared_Path() + "\\" + context.getParameter("RootFolder") + "\\" + UtilityFactory.testEnvironment.environment() + "\\Reports"
        String ReportLocation1 = UtilityFactory.testEnvironment.shared_Path() + "\\Reports"
        ExtentManager.copyFile(ReportLocation, ReportFileName)
        ExtentManager.copyFile(ReportLocation1, ReportFileName)

    }
        SuitesIterator++;
    }

    @Override
    public synchronized void onStart(ISuite context) {
        System.out.println(("Extent Reports started!"));

    }

    public static String getCurrentTestName() {
   return methodName;
    }
}