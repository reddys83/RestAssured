package com.surepayroll.DB

import com.codoid.products.fillo.Fillo
import com.codoid.products.fillo.Recordset
import com.codoid.products.fillo.*;


class ExcelConnectionFactory {
    public static Map<String,String> testDataMap=[:];
    public static Map<String,String> headersMap=[:];

    public static Recordset readExcelUsingQuery(String ExcelPath, String strQuery) throws Exception {
        Fillo fillo=new Fillo();
        Connection con=fillo.getConnection(ExcelPath);
        Recordset recordset=con.executeQuery(strQuery);

        return recordset;
    }

    public static Map<String,String>  getTestData(String ExcelPath, String strQuery)
    {
        Recordset recordset=readExcelUsingQuery(ExcelPath,strQuery)

        while(recordset.next()){
            testDataMap.put("TestName",recordset.getField("TestName"))
            testDataMap.put("ProductName",recordset.getField("ProductName"))
            testDataMap.put("URL",recordset.getField("URL"))
            testDataMap.put("Method",recordset.getField("Method"))
            testDataMap.put("RequestData",recordset.getField("RequestData"))
        }
        return testDataMap
    }

    public static Map<String,String>  getHeaderData(String ExcelPath, String strQuery)
    {
        Recordset recordset=readExcelUsingQuery(ExcelPath,strQuery)

        while(recordset.next()){
            for(int i=1;i<=(recordset.fieldNames.size()-1)/2;i++) {

                if(recordset.getField("Key"+i)!="") {
                    headersMap.put(recordset.getField("Key" + i), recordset.getField("Value" + i))
                }
            }
        }
        return headersMap
    }

    public static String getHostName(String ExcelPath, String strQuery)
    {
        Recordset recordset=readExcelUsingQuery(ExcelPath,strQuery)
        String hostname;
        while(recordset.next()){
            hostname  =recordset.getField("HostName");
        }
        return hostname;

    }

    public static Map getPath(String ExcelPath, String strQuery)
    {
        Recordset recordset=readExcelUsingQuery(ExcelPath,strQuery)
        Map pathMap=[:];
        while(recordset.next()){
            for(int i=0;i<recordset.fieldNames.size();i++) {

                pathMap.put(recordset.fieldNames.get(i), recordset.getField(recordset.fieldNames.get(i)))
            }
        }
        return pathMap;

    }
    }


