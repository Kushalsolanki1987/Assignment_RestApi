package resources;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utils {

	public static RequestSpecification req;

	// This Method will help in Re-using the common paramaters such URL , Headers which are common for all the APIs
	
	
	public RequestSpecification requestSpecification() throws IOException {

		if (req == null) {
			PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
			req = new RequestSpecBuilder().setBaseUri(getGlobalVariable("baseUrl"))
					.addFilter(RequestLoggingFilter.logRequestTo(log))
					.addFilter(ResponseLoggingFilter.logResponseTo(log)).setContentType(ContentType.JSON).build();
			return req;
		}
		return req;
	}
	
//	This method is used for Token Generation for PATCH and DELETE APIs

	public String generateAccessToken() {

		String authtoken = given().spec(req)
				.body("{\r\n" + "    \"username\" : \"admin\",\r\n" + "    \"password\" : \"password123\"\r\n" + "}")
				.when().post("/auth").asString();

		JsonPath js1 = new JsonPath(authtoken);
		String accesstoken = js1.getString("token");

		return accesstoken;
	}

	
//	This method is used for fetching the Base URL from the File
	
	public static String getGlobalVariable(String key) throws IOException {
		String path=System.getProperty("user.dir")+"\\src\\test\\java\\resources\\global.properties";
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(path);
				
		prop.load(fis);
		return prop.getProperty(key);

	}

//	 This method converts JSON response into String and thereafter can be used to fetch the value of any json response parameter
	public String getJsonPath(Response response, String key) {

		String responseString = response.asString();
		JsonPath js = new JsonPath(responseString);
		return js.get(key).toString();
	}

}
