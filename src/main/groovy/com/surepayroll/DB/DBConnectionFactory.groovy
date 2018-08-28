package com.surepayroll.DB

import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.ResultSetMetaData
import java.sql.Statement

class DBConnectionFactory {

    public static ArrayList<List<String>> executeQuery(String query)
    {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection conn = DriverManager.getConnection("jdbc:sqlserver://192.168.0.25;user=QAAutoUser;" +"password=865091AA-9CB9-4381-9495-E21D01D7C8B9;database=SurePayrollQAAutomation");
        Statement sta = conn.createStatement();
        ResultSet rs = sta.executeQuery(query);
        ResultSetMetaData rsmd=rs.getMetaData();

        int colCount=rsmd.getColumnCount();
        int rowIterator=0;
        ArrayList<String> colNames=new ArrayList<String>();
        List<List<String>> dataList=new ArrayList<List<String>>();


        for(int colIterator=1;colIterator<=colCount;colIterator++)
        {
            colNames.add(rsmd.getColumnName(colIterator))
        }

        dataList.add(colNames);


        while (rs.next())
        {
            List RowData = new ArrayList();
            for(int colIterator=0;colIterator<colCount;colIterator++)
            {
                RowData.add(rs.getObject(colIterator+1));
            }
            dataList.add(RowData);
            RowData=null;
            rowIterator++;
        }

        List[] arr = dataList.toArray(new ArrayList[dataList.size()]);

        return dataList;
    }




public static String[][] executeQuery1(String query)
{
    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    Connection conn = DriverManager.getConnection("jdbc:sqlserver://192.168.0.25;user=QAAutoUser;password=865091AA-9CB9-4381-9495-E21D01D7C8B9;database=SurePayrollQAAutomation");
    Statement sta = conn.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    ResultSet rs = sta.executeQuery(query);
    ResultSetMetaData rsmd=rs.getMetaData();

    int colCount=rsmd.getColumnCount();
    int rowIterator=0;
    int rowCount=0;
    ArrayList<String> colNames=new ArrayList<String>();




    for(int colIterator=1;colIterator<=colCount;colIterator++)
    {
        colNames.add(rsmd.getColumnName(colIterator))
    }

    while (rs.next())
    {
        rowCount++;
    }
    rs.beforeFirst()
    String[][] resultArray=new String[rowCount][colCount]

    while (rs.next())
    {


        for(int colIterator=0;colIterator<colCount;colIterator++)
        {

            resultArray[rowIterator][colIterator]=rs.getObject(colIterator+1)
        }


        rowIterator++;
    }



    return resultArray;
}


}