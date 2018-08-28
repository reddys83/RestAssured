package com.surepayroll.Assertions

import io.restassured.path.xml.XmlPath
import io.restassured.path.xml.config.XmlPathConfig

class AssertionLibrary {

    public static String getTaxAmountByTaxID(String xmlResponse, String taxId)
    {


        XmlPath xmlpath = new XmlPath(xmlResponse).using(XmlPathConfig.xmlPathConfig().namespaceAware(false))
        xmlpath.get("'s:Envelope'.'s:Body'.CalculatePreviewResponse.CalculatePreviewResult.'a:Checks'.'a:Check'.'a:CheckTaxDetail'.'a:CheckTaxDetail'.findAll{it.'a:TaxCode'=='"+taxId+"'}.a:Amount")

    }
}
