package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class POST_Or_Create_A_Product {

	@Test
	public void create_A_Product() {
		//https://techfios.com/api-prod/api/product/create.php
		
//		{
//		    "name" : "111 Amazing Pillow 2.0",
//		    "price" : "199",
//		    "description" : "The best pillow for amazing programmers.",
//		    "category_id" : 2,
//		    "created" : "2018-06-01 00:35:07"
//		}
		
//		HashMap payload = new HashMap();
//		payload.put("name","Fundamentals for QA People");
//		payload.put("price","149");
//		payload.put("description","You have to buy this book!!!");
//		payload.put("category_id","6");
		
		HashMap<String,String> payload = new HashMap<String,String>();
		payload.put("name","Fundamentals for QA People");
		payload.put("price","149");
		payload.put("description","You have to buy this book!!!");
		payload.put("category_id","6");
		
		Response response =
				given()
					.log().all()
					.baseUri("https://techfios.com/api-prod/api/product")
					.header("Content-Type","application/json; charset=UTF-8")
					.body(payload)
				.when()
					.post("/create.php")
				.then()
					.log().headers()
					.extract().response();
		
		//different types of assertions
		int statusCode = response.getStatusCode();
		System.out.println("Status code:"+ statusCode);
		Assert.assertEquals(statusCode, 201);
		
		long responseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
		System.out.println("Response time: "+ responseTime);
		if(responseTime<=2000) {
			System.out.println("Response time is within range.");
		}else {
			System.out.println("Response time is out of range.");
		}
		
		
	//	response.getBody().prettyPrint();
		String responseBody = response.getBody().asString();		
		System.out.println("Response Body:" + responseBody);
		
		JsonPath jp = new JsonPath(responseBody);
		
		String successMessage = jp.getString("message");
		System.out.println("successMessage:" + successMessage);
		Assert.assertEquals(successMessage, "Product was created.");
		
	}		
}
