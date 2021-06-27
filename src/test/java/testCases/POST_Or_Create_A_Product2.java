package testCases;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class POST_Or_Create_A_Product2 {

	@Test
	public void create_A_Product() {
		//https://techfios.com/api-prod/api/product/create.php
		
		String payloadPath = ".\\src\\main\\java\\data\\create_payload.json";
			
		Response response =
				given()
					.log().all()
					.baseUri("https://techfios.com/api-prod/api/product")
					.header("Content-Type","application/json; charset=UTF-8")
					.body(new File (payloadPath))
				.when()
					.log().all()
					.post("/create.php")
				.then()
					.log().all()
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
