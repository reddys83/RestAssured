package com.surepayroll.Utils

import com.surepayroll.DB.ExcelConnectionFactory
import com.surepayroll.Environment
import io.restassured.path.json.JsonPath
import org.aeonbits.owner.ConfigFactory


class UtilityFactory {
    def static returnMap = [:];
    public static Environment testEnvironment;
    public static String rootFolder;

    public static Map getServiceProperties(String apiName)
    {
        File jsonFile = new File("src/test/resources/environment/serviceproperties.json");
        JsonPath Jpath=JsonPath.from(jsonFile);
        String url=Jpath.get("properties."+apiName+".url").toString();
        HashMap<String,String> headersMap = Jpath.get("properties."+apiName+".headers");
        HashMap<String,String> paramMap = Jpath.get("properties."+apiName+".params");
        //HashMap<String,String> headersMap = new Gson().fromJson(headersJson, new TypeToken<HashMap<String, String>>(){}.getType());

        returnMap.put("url",url);
        returnMap.put("headers",headersMap);
        returnMap.put("params",paramMap);
        return returnMap;

    }

    public static String readJsonFile(String filePath)
    {

        File jsonFile = new File(filePath);
        String jsonString=jsonFile.text;

        return jsonString;
    }


    public static String readXMLFile(String filePath)
    {

        File xmlFile = new File(filePath);
        String xmlstring = xmlFile.text;

        return xmlstring;
    }





    public static String getClassName(Class c) {
        String FQClassName = c.getName();
        int firstChar;
        firstChar = FQClassName.lastIndexOf ('.') + 1;
        if ( firstChar > 0 ) {
            FQClassName = FQClassName.substring ( firstChar );
        }
        return FQClassName;
    }


    public static String setEnvironment(String suiteName) {

        Map myVars = new HashMap();
        def Map pathMap = [:];
        if(System.getProperty("env")==null)
        {
            Environment testEnvironment1 = ConfigFactory.create(Environment.class);
            System.setProperty("env",testEnvironment1.environment())
        }

        myVars.put("environment", System.getProperty("env"));
        testEnvironment = ConfigFactory.create(Environment.class,myVars);
        pathMap= ExcelConnectionFactory.getPath(testEnvironment.environmentFile_Path(),"select * from Paths where SuiteName='"+suiteName+"'");
        rootFolder=pathMap.get("RootFolder")
        String testDataFilePath=testEnvironment.shared_Path()+"\\"+pathMap.get("RootFolder")+"\\"+testEnvironment.environment()+"\\TestData\\"+pathMap.get("TestDataFile")
         return testDataFilePath;
    }


}
