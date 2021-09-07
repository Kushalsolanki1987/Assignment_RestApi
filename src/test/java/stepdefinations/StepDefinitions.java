package stepdefinations;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.BookingPayload;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

public class StepDefinitions extends Utils {
	RequestSpecification req2;
	ResponseSpecification resp;
	Response response;
	TestDataBuild data = new TestDataBuild();
	static int id;
	APIResources resourceAPI;

	@Given("Partial Update Payload information {string} {string}")
	public void partial_update_payload_information(String name, String surname) throws IOException {

		RequestSpecification req1 = given().spec(requestSpecification()).body(data.addBookingPayload());
		resp = new ResponseSpecBuilder().expectStatusCode(200).expectHeader("Server", "Cowboy").build();
		BookingPayload bp1 = req1.when().post("/booking").then().spec(resp).extract().response()
				.as(BookingPayload.class);

		id = bp1.getBookingid();

		req2 = given().pathParam("id", id).spec(requestSpecification()).body(data.updateBookingPayload(name, surname))
				.header("Cookie", "token=" + generateAccessToken());

	}

	@When("User calls  {string} with {string} Http request")
	public void user_calls_with_http_request(String resource, String method) throws IOException {

		 resourceAPI = APIResources.valueOf(resource);
		resourceAPI.getResource();

		if (method.equalsIgnoreCase("PATCH"))
			response = req2.when().patch(resourceAPI.getResource() + "{id}");
		else if (method.equalsIgnoreCase("DELETE"))
			response = req2.when().delete(resourceAPI.getResource() + "{id}");
		else if (method.equalsIgnoreCase("GET"))
			response = req2.when().get(resourceAPI.getResource());

	}

	@Then("Partial update is successful with Status code {int}")
	public void partial_update_is_successful_with_status_code(Integer int1) {

		assertEquals(response.getStatusCode(), 200);

	}

	@And("^Parameters \"([^\"]*)\" is updated as \"([^\"]*)\" in the response body$")
	public void parameters_something_is_updated_as_something_in_the_response_body(String keyValue, String expectedValue)
			throws Throwable {

		assertEquals(getJsonPath(response, keyValue), expectedValue);

	}

	@And("Verify Booking Id updated maps to {string} {string} using {string}")
	public void verify_booking_id_updated_maps_to_using(String name, String surname, String resource)
			throws IOException {

		req2 = given().pathParam("id", id).spec(requestSpecification());
		 resourceAPI = APIResources.valueOf(resource);
			resourceAPI.getResource();
			
				response = req2.when().get(resourceAPI.getResource()+ "{id}");

		String actualfirstname = getJsonPath(response, "firstname");
		String actuallasttname = getJsonPath(response, "lastname");

		assertEquals(actualfirstname, name);
		assertEquals(actuallasttname, surname);
	}

	@Given("Partial Update header information")
	public void partial_update_header_information() throws IOException {
		req2 = given().pathParam("id", id).spec(requestSpecification())
				.header("Cookie", "token=" + generateAccessToken());
	}
	
	@Given("Partial Update Payload information without  Id")
	public void partial_update_payload_information_without_id() throws IOException {
		req2 = given().pathParam("id", "").spec(requestSpecification())
				.header("Cookie", "token=" + generateAccessToken());
	}
	@Then("Partial update is unsuccessful with Status code {int}")
	public void partial_update_is_unsuccessful_with_status_code(Integer int1) {
		assertEquals(response.getStatusCode(), 404);
	}
	@Then("Partial update is unsuccessful with Status {int}")
	public void partial_update_is_unsuccessful_with_status(Integer int1) {
		assertEquals(response.getStatusCode(), 403);
	}
	
	@Given("Get Request Specification details")
	public void get_request_specification_details() throws IOException {
		req2 = given().spec(requestSpecification());
	}
	@Then("Get is successful with Status code {int}")
	public void get_is_successful_with_status_code(Integer int1) {
		assertEquals(response.getStatusCode(), 200);

}
	@Given("Get Request Specification using {string} and {string}")
	public void get_request_specification_using_and(String name, String surname) throws IOException {
		req2 = given().spec(requestSpecification().queryParam(name, "Michael").queryParam(surname, "Scott"));
	}
	
	@Given("Delete Request header information")
	public void delete_Request_header_information() throws IOException {
		req2 = given().pathParam("id", id).spec(requestSpecification())
				.header("Cookie", "token=" + generateAccessToken());
	}

	@Then("Delete is successful with Status code {int}")
	public void delete_is_successful_with_Status_code(Integer int1) {
		assertEquals(response.getStatusCode(), 201);
	}
	
	@Then("Verify if user is not able to get booking information by calling {string}")
	public void verify_if_user_is_not_able_to_get_booking_information_by_calling(String resource) {
		 resourceAPI = APIResources.valueOf(resource);
			resourceAPI.getResource();
			response = req2.when().get(resourceAPI.getResource()+ "{id}");
			assertEquals(response.getStatusCode(), 404);
		
	}
	@Given("Partial Update Payload information without  token")
	public void partial_Update_Payload_information_without_token() throws IOException {
		req2 = given().pathParam("id", id).spec(requestSpecification());
	}

}