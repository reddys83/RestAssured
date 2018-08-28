package com.surepayroll.API

import com.aventstack.extentreports.markuputils.MarkupHelper
import com.surepayroll.DB.ExcelConnectionFactory
import com.surepayroll.ExtentReports.ExtentReportsListener
import com.surepayroll.Utils.UtilityFactory
import io.restassured.RestAssured
import io.restassured.http.ContentType
import io.restassured.specification.RequestSpecification
import org.aeonbits.owner.ConfigFactory


import java.nio.file.Files
import java.nio.file.Paths

import static io.restassured.RestAssured.given

class APIFactory {
    String propertiesFile;

    public void setPropertiesFileLocation(String propertiesfileLocation)

    {
         propertiesFile=propertiesfileLocation;
    }

    public static void main(String[] args)
    {APIFactory api=new APIFactory();
        api.setPropertiesFileLocation("src/test/resources/environment/QA.properties");
       api.hello();
    }


    HTTPMethod httpMethods = new HTTPMethod()


    public String generateStringFromResource(String path) throws IOException {

        return new String(Files.readAllBytes(Paths.get(path)));

    }

    RequestSpecification createRequestSpecification(String baseURI,String apiType, Map HeadersMap, String requestBody) {
        setRestAssuredURI(baseURI);
        switch (apiType) {
            case "GET":
                return given().log().all(true).headers(HeadersMap).contentType(ContentType.JSON).proxy("10.44.48.13",80).relaxedHTTPSValidation().when()
                break
            case "DELETE":
                return given().contentType(ContentType.JSON).headers(HeadersMap).when()
                break
            case "PUT":
                return given().contentType(ContentType.JSON).headers(HeadersMap).when().body(requestBody)
                break
            case "POST":
                return given().contentType(ContentType.JSON).headers(HeadersMap).when().body(requestBody)
                break
            default:
                throw new Exception("API request type is not supported. Please provide a valid request type or add the request specification to the utilities")
        }

    }

    RequestSpecification createXMLRequestSpecification(String baseURI,String apiType, Map HeadersMap,Map paramsMap, String requestBody) {
        setRestAssuredURI(baseURI);
        switch (apiType) {
            case "GET":
                return given().log().all(true).headers(HeadersMap).params(paramsMap).contentType(ContentType).when()
                break
            case "DELETE":
                return given().params(paramsMap).contentType(ContentType.XML).headers(HeadersMap).when()
                break
            case "PUT":
                return given().params(paramsMap).contentType(ContentType.XML).headers(HeadersMap).when().body(requestBody)
                break
            case "POST":
                return given().params(paramsMap).contentType(ContentType.XML).headers(HeadersMap).when().body(requestBody)
                break
            default:
                throw new Exception("API request type is not supported. Please provide a valid request type or add the request specification to the utilities")
        }

    }

    void setRestAssuredURI(String endPoint){
        //Environment env = ConfigFactory.create(Environment.class,ConfigFactory.getProperties())
        //RestAssured.baseURI=""

        RestAssured.baseURI=endPoint

    }

    Map apiRequest(String apiType, RequestSpecification spec){

        switch (apiType){
            case "GET":
                return httpMethods.GET(spec)
                break
            case "DELETE":
                return httpMethods.DELETE(spec)
                break
            case "PUT":
                return httpMethods.PUT(spec)
                break
            case "POST":
                return httpMethods.POST(spec)
                break
        }
    }


    public Map getServiceResponse(String apiName,Map<String,String>urlParamMap,String method,String filePath)
    {
        def servicePropertiesMap=[:];
        def headersMap = [:];
        def paramMap = [:];
        def responseMap=[:];

        servicePropertiesMap = UtilityFactory.getServiceProperties(apiName);
        String URL = servicePropertiesMap.get("url");
        URL=URL.replace("[HostName]",UtilityFactory.testEnvironment.hostname())
        urlParamMap.each
            {   key,value ->
            if(URL.contains("["+key+"]"))
            {
               URL=URL.replace("["+key+"]",value)
            }

        }

        headersMap = servicePropertiesMap.get("headers");
        paramMap=servicePropertiesMap.get("params");
        if(paramMap==null)
        {
            paramMap=[:];
        }

        if(filePath!="")
        {
            filePath=UtilityFactory.testEnvironment.jsonFilePath()+filePath;
        }
        // Create Request Specification with all required parameters
        RequestSpecification ReqSpec=createRequestSpecification(URL,method,headersMap,paramMap,UtilityFactory.readJsonFile(filePath));

        // Make a get request
        responseMap= apiRequest(method,ReqSpec);


    }

    public Map getXMLServiceResponse(String apiName,Map<String,String>urlParamMap,String method,String filePath)
    {
        def servicePropertiesMap=[:];
        def headersMap = [:];
        def paramMap = [:];
        def responseMap=[:];

        servicePropertiesMap = UtilityFactory.getServiceProperties(apiName);
        String URL = servicePropertiesMap.get("url");
        URL=URL.replace("[HostName]",UtilityFactory.testEnvironment.hostname())
        urlParamMap.each
                {   key,value ->
                    if(URL.contains("["+key+"]"))
                    {
                        URL=URL.replace("["+key+"]",value)
                    }

                }

        headersMap = servicePropertiesMap.get("headers");
        paramMap=servicePropertiesMap.get("params");
        if(paramMap==null)
        {
            paramMap=[:];
        }

        if(filePath!="")
        {
            filePath=UtilityFactory.testEnvironment.jsonFilePath()+filePath;

        }
        // Create Request Specification with all required parameters
        RequestSpecification ReqSpec=createXMLRequestSpecification(URL,method,headersMap,paramMap,UtilityFactory.readXMLFile(filePath));

        // Make a get request
        responseMap= apiRequest(method,ReqSpec);


    }


    public Map getServiceResponse(String excelPath,String testName)
    {
        def servicePropertiesMap=[:];
        def headersMap = [:];
        def dataMap = [:];
        def responseMap=[:];
        String requestString=""
        dataMap= ExcelConnectionFactory.getTestData(excelPath,"select * from TestData where TestName='"+testName+"'")
        String URL = dataMap.get("URL");
        String method=dataMap.get("Method");
        String APIName=dataMap.get("ProductName");
        headersMap= ExcelConnectionFactory.getHeaderData(UtilityFactory.testEnvironment.environmentFile_Path(),"select * from Headers where ProductName='"+APIName+"'")
        String hostname=ExcelConnectionFactory.getHostName(UtilityFactory.testEnvironment.environmentFile_Path(),"select * from HostNames where ProductName='"+APIName+"' and Environment='"+UtilityFactory.testEnvironment.environment()+"'")
        URL=URL.replace("[HostName]",hostname)
        String RequestDataFile=dataMap.get("RequestData");

        String payloadPath=UtilityFactory.testEnvironment.shared_Path()+"\\"+UtilityFactory.rootFolder+"\\"+UtilityFactory.testEnvironment.environment()+"\\Payloads\\"
        if(RequestDataFile!="") {
            requestString = UtilityFactory.readJsonFile(payloadPath + RequestDataFile);
        }
        // Create Request Specification with all required parameters
        RequestSpecification ReqSpec=createRequestSpecification(URL,method,headersMap,requestString);

        // Make a get request
        responseMap= apiRequest(method,ReqSpec);



        ExtentReportsListener.test.get().info("Status "+responseMap.get("statusCode"))
        ExtentReportsListener.test.get().info(MarkupHelper.createCodeBlock("RESPONSE "+responseMap.get("responseBody")))
        ExtentReportsListener.test.get().info(MarkupHelper.createCodeBlock("REQUEST "+responseMap.get("requestBody")))

        return responseMap;
    }

}
