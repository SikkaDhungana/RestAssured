package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

import java.util.concurrent.TimeUnit;

public class Get_Or_Read_A_Product {

	@Test
	public void read_A_Product() {
		//https://techfios/api-prod/api/product/read_one.php?id=1685
		Response response =
				given()
					.baseUri("https://techfios.com/api-prod/api/product")
					.header("Content-Type","application/json; charset=UTF-8")
					.queryParam("id", "1685")
				.when()
					.get("/read_one.php")
				.then()
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
		
		String productName = jp.getString("name");
		System.out.println("productName:" + productName);
		Assert.assertEquals(productName, "Iphone");
		
		String productDescription = jp.getString("description");
		System.out.println("productDescription:" + productDescription);
		Assert.assertEquals(productDescription, "The one phone ever");
		
		String productPrice = jp.getString("price");
		System.out.println("productPrice:" + productPrice);
		Assert.assertEquals(productPrice, "1922");
		
		//SoftAssert.assertAll();
 }
}
