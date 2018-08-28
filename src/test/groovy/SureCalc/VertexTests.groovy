package SureCalc

import com.surepayroll.Assertions.AssertionLibrary
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


class VertexTests extends ExtentReportsListener{

    APIFactory api =  new APIFactory()

    String baseURI="";
    String requestData="";
    def responseMap=[:];
    def tempMap=[:];
    def dataMap=[:];
    Environment testEnvironment;
    String testDataFile;


    //Can use this for data driven testing
    @DataProvider(name = "getData")
    public static String[][] cities() {
        String[][] queryResult = DBConnectionFactory.executeQuery1
        ("select ParamValue from TestCaseParameters where TestCaseName='getWeatherData'");
        return queryResult;
    }
    @BeforeClass(alwaysRun=true)
    public void setEnvironment() {
         testDataFile=  UtilityFactory.setEnvironment(UtilityFactory.getClassName(this.getClass()));
    }


    @Test(groups = ["smokeTest"])
    public void validate_TaxAmount() {
        // Make a POST request
        responseMap= api.getServiceResponse(testDataFile,getCurrentTestName());
        //        // Check the responses
        Assert.assertEquals(responseMap.get("statusCode"), "200");
        String taxAmount=AssertionLibrary.getTaxAmountByTaxID(responseMap.get("responseBody").toString(),"PA1137-051")
        Assert.assertEquals(taxAmount,"2.16");
    }

    @Test(groups = ["smokeTest"])
    public void validate_TaxAmount1() {
        // Make a POST request
        responseMap= api.getServiceResponse(testDataFile,getCurrentTestName());
        //        // Check the responses
        Assert.assertEquals(responseMap.get("statusCode"), "200");
        String taxAmount=AssertionLibrary.getTaxAmountByTaxID(responseMap.get("responseBody").toString(),"PA1137-051")
        Assert.assertEquals(taxAmount,"2.16");
    }
}