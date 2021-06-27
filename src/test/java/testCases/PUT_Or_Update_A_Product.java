package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class PUT_Or_Update_A_Product {

	@Test
	public void update_A_Product() {
		//https://techfios/api-prod/api/product/update.php
		
		String payloadPath = ".\\src\\main\\java\\data\\update_payload.json";
		
	
		
		Response response =
				given()
					.log().all()
					.baseUri("https://techfios.com/api-prod/api/product")
					.header("Content-Type","application/json; charset=UTF-8")
					.body(new File (payloadPath))
				.when()
					.log().all()
					.put("/update.php")
				.then()
					.log().all()
					.extract().response();
		
		
		int statusCode = response.getStatusCode();
		System.out.println("Status code:"+ statusCode);
		Assert.assertEquals(statusCode, 200);
		
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
		Assert.assertEquals(successMessage, "Product was updated.");
		
	}		
}
