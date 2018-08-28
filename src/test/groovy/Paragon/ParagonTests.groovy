package Paragon

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

class ParagonTests extends ExtentReportsListener{

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


    @Test
    public void ParagonTest1()
    {
        // Make a POST request. This line of code is always same
        responseMap= api.getServiceResponse(testDataFile,getCurrentTestName());
        // Check the responses. This line of code is always same
        Assert.assertEquals(responseMap.get("statusCode"),"200");
        //Create JsonPath object to parse json file. This line of code is always same
        JsonPath jsonPath = new JsonPath(responseMap.get("responseBody").toString());

        //Customized Assertions
        Assert.assertEquals(jsonPath.get("bclCode"),BCL);
        Assert.assertTrue(jsonPath.get("defaultPayrollId")>0);

        //Storing temp values
        tempMap.put("defaultPayrollId",jsonPath.get("defaultPayrollId"));
    }
}
