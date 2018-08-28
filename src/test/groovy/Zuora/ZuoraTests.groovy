package Zuora

import com.surepayroll.DB.DBConnectionFactory
import com.surepayroll.Environment
import com.surepayroll.Utils.UtilityFactory
import io.restassured.path.json.JsonPath
import io.restassured.specification.RequestSpecification
import org.testng.Assert
import org.testng.annotations.AfterMethod
import org.testng.annotations.AfterTest
import org.testng.annotations.BeforeClass
import org.testng.annotations.DataProvider
import org.testng.annotations.Test
import com.surepayroll.API.APIFactory;
import com.surepayroll.ExtentReports.ExtentReportsListener;


class ZuoraTests extends ExtentReportsListener{

    APIFactory api =  new APIFactory()

    String baseURI="";
    String requestData="";
    def responseMap=[:];
    def tempMap=[:];
    def dataMap=[:];
    Environment testEnvironment;
    String testDataFile;

    @BeforeClass(alwaysRun=true)
    public void setEnvironment() {
        testDataFile=  UtilityFactory.setEnvironment(UtilityFactory.getClassName(this.getClass()));
    }
    //Can use this for data driven testing
//    @DataProvider(name = "getData")
//    public static String[][] cities() {
//        String[][] queryResult = DBConnectionFactory.executeQuery1("select ParamValue from TestCaseParameters where TestCaseName='getWeatherData'");
//        return queryResult;
//    }

// @Test(groups = ["smokeTest","regressionTest"])
// public void post_partnerChange_test1()
//    {
//        // Make a POST request
//        responseMap= api.getServiceResponse(testDataFile,getCurrentTestName());
//        // Check the responses
//        //global Assertion- common for all Api's
//        Assert.assertEquals(responseMap.get("statusCode"),"200");
//        //getting response body into Jsonpath
//        JsonPath jsonPath = new JsonPath(responseMap.get("responseBody").toString());
//        //Local Assertions
//        Assert.assertEquals(jsonPath.get("Success").toString(),"true");
//        Assert.assertEquals(jsonPath.get("Message"),"Change Partner Code success.");
//    }



    @Test(groups = ["smokeTest","regressionTest"])
    public void GetAccount_validation_Zoura_test7()
    {
        // Make a POST request
        responseMap= api.getServiceResponse(testDataFile,getCurrentTestName());
        // Check the responses
        //global Assertion- common for all Api's
        Assert.assertEquals(responseMap.get("statusCode"),"200");
        //getting response body into Jsonpath
        JsonPath jsonPath = new JsonPath(responseMap.get("responseBody").toString());
        //Local Assertions
        Assert.assertEquals(jsonPath.get("Success").toString(),"true");
        Assert.assertEquals(jsonPath.get("Message"),"Change Partner Code success.");
    }

}