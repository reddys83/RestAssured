package Aegis

import com.aventstack.extentreports.ExtentTest
import com.aventstack.extentreports.Status
import com.aventstack.extentreports.markuputils.ExtentColor
import com.aventstack.extentreports.markuputils.MarkupHelper
import com.surepayroll.API.APIFactory
import com.surepayroll.DB.DBConnectionFactory
import com.surepayroll.Environment
import com.surepayroll.ExtentReports.ExtentReportsListener
import com.surepayroll.Utils.UtilityFactory
import io.restassured.path.json.JsonPath
import org.testng.Assert
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeClass
import org.testng.annotations.DataProvider
import org.testng.annotations.Listeners
import org.testng.annotations.Test

class DefaultPayrollTests extends ExtentReportsListener{

    APIFactory api =  new APIFactory()

    String baseURI="";
    String requestData="";
    def responseMap=[:];
    def tempMap=[:];
    def dataMap=[:];
    Environment testEnvironment;
    String testDataFile;
    ExtentTest Reporter=test.get();


    //****************************************************************************************************************
    @BeforeClass(alwaysRun=true)
    public void setEnvironment() {
        testDataFile=  UtilityFactory.setEnvironment(UtilityFactory.getClassName(this.getClass()));
    }

    //****************************************************************************************************************
    @Test(groups = ["smokeTest"])
    //post request
    public void createDefaultPayroll_Post()
    {
        // Make a POST request
        responseMap= api.getServiceResponse(testDataFile,getCurrentTestName());
//        Reporter.log(Status.INFO,"Hello")

        //test.get().log("REQUEST", responseMap.get("requestBody"))
//        String[][] data=new String[1][3]
//      data=[["Status","Request","Response"],[responseMap.get("statusCode"),responseMap.get("requestBody"),responseMap.get("responseBody")]]
//
//        test.get().log(Status.INFO,MarkupHelper.createTable(data));
//
//     MarkupHelper.createLabel(ResponseLog,ExtentColor.RED)

        // Check the responses
        Assert.assertEquals(responseMap.get("statusCode"),"200");
        JsonPath jsonPath = new JsonPath(responseMap.get("responseBody").toString());
       // Assert.assertEquals(jsonPath.get("bclCode"),BCL);
        Assert.assertTrue(jsonPath.get("defaultPayrollId")>0);

        //Storing temp values
        tempMap.put("defaultPayrollId",jsonPath.get("defaultPayrollId"));
    }

    //****************************************************************************************************************
    @Test(dependsOnMethods="createDefaultPayroll_Post")
    public void get_defaultPayroll_GET()
    {
        // Make a GET request
        responseMap= api.getServiceResponse(testDataFile,getCurrentTestName());

        // Check the responses
        Assert.assertEquals(responseMap.get("statusCode"),"200");
        JsonPath jsonPath = new JsonPath(responseMap.get("responseBody").toString());
        Assert.assertEquals(jsonPath.get(("defaultPayrollId")),tempMap.get("defaultPayrollId"));
    }

    //****************************************************************************************************************
    @Test(dependsOnMethods="get_defaultPayroll_GET")
    //Delete request
    public void deleteDefaultPayroll_Delete()
    {
        // Make a Delete request
        responseMap= api.getServiceResponse(testDataFile,getCurrentTestName());
        // Check the responses
        Assert.assertEquals(responseMap.get("statusCode"),"200");
        JsonPath jsonPath = new JsonPath(responseMap.get("responseBody").toString());
//        Assert.assertEquals(jsonPath.get("bclCode"),BCL);
        Assert.assertEquals(jsonPath.get(("defaultPayrollId")),tempMap.get("defaultPayrollId"));
    }




    //**************************


    @Test(groups = ["smokeTest"])
    //post request
    public void CreateDPWith07_EC()
    {
        // Make a POST request
        responseMap= api.getServiceResponse(testDataFile,getCurrentTestName());

              // Reporter.log(Status.INFO,"Hello")

        //test.get().log("REQUEST", responseMap.get("requestBody"))
//        String[][] data=new String[1][3]
//      data=[["Status","Request","Response"],[responseMap.get("statusCode"),responseMap.get("requestBody"),responseMap.get("responseBody")]]
//
//        test.get().log(Status.INFO,MarkupHelper.createTable(data));
//
//     MarkupHelper.createLabel(ResponseLog,ExtentColor.RED)

        // Check the responses //Global Assertions
        Assert.assertEquals(responseMap.get("statusCode"),"200");
        JsonPath jsonPath = new JsonPath(responseMap.get("responseBody").toString());
        // Assert.assertEquals(jsonPath.get("bclCode"),BCL);

        //local Asserions
        Assert.assertTrue(jsonPath.get("defaultPayrollId")>0);
        Assert.assertEquals(jsonPath.get("bclCode"),"A02F");

        //Storing temp values
        tempMap.put("defaultPayrollId",jsonPath.get("defaultPayrollId"));
    }


    //****************************************************************************************************************
    @AfterMethod()
    public void tearDown()
    {   baseURI="";
        requestData="";
        dataMap=[:];
        responseMap=[:];
    }
}


