package com.surepayroll.API

import io.restassured.RestAssured
import io.restassured.response.Response
import io.restassured.specification.RequestSpecification

class HTTPMethod {



    Map GET(RequestSpecification spec){
        try{
            Response response =spec.get()
            def responseMap=[:]
            responseMap['statusCode']= response.getStatusCode().toString()
            responseMap['responseBody']=response.getBody().asString()
            return responseMap
        } catch (Exception e){
            throw new Exception(e.getMessage())
        }
    }

    Map PUT(RequestSpecification spec){
        try{
            Response response =spec.put()
            def responseMap=[:]
            responseMap['statusCode']= response.getStatusCode().toString()
            responseMap['responseBody']=response.getBody().asString()
            return responseMap
        } catch (Exception e){
            throw new Exception(e.getMessage())
        }
    }

    Map POST(RequestSpecification spec){
        try{
            Response response = spec.post()


            def responseMap=[:]
            responseMap['statusCode']= response.getStatusCode().toString()
            responseMap['requestBody']=spec.given().request().properties.get("body")
            responseMap['responseBody']=response.getBody().asString()
            return responseMap
        } catch (Exception e){
            throw new Exception(e.getMessage())
        }
    }

    Map DELETE(RequestSpecification spec){
        try{
            Response response =spec.delete()
            def responseMap=[:]
            responseMap['statusCode']= response.getStatusCode().toString()
            responseMap['responseBody']=response.getBody().asString()
            return responseMap
        } catch (Exception e){
            throw new Exception(e.getMessage())
        }
    }

}
