Feature: Validating CRUD (Create Update Delete Get) APIs of booking applications

@Regression
Scenario Outline: To verify if user is able to perform Partial update of Booking information successfully.
Given Partial Update Payload information "<name>" "<surname>"
When User calls  "UpdateBookingApi" with "PATCH" Http request
Then Partial update is successful with Status code 200
And Parameters "firstname" is updated as "<name>" in the response body
And Parameters "lastname" is updated as "<surname>" in the response body
And Verify Booking Id updated maps to "<name>" "<surname>" using "GetBookingAPI"


Examples:

|name     | surname|
|Automated|Test    |
|Dwight   |Schrute |


@Regression
Scenario: To verify if the user is able to hit partial update api without request body since the parameters are optional
Given Partial Update header information 
When User calls  "UpdateBookingApi" with "PATCH" Http request
Then Partial update is successful with Status code 200

@Regression
Scenario: To verify error message if user tries to partially update the booking without  ID
Given Partial Update Payload information without  Id
When User calls  "UpdateBookingApi" with "PATCH" Http request
Then Partial update is unsuccessful with Status code 404

@Regression
Scenario: To verify error message if user tries to partially update the booking without  token
Given Partial Update Payload information without  token
When User calls  "UpdateBookingApi" with "PATCH" Http request
Then Partial update is unsuccessful with Status 403

@Regression
Scenario: To verify if User gets all booking ids without providing any parameters
Given Get Request Specification details
When User calls  "GetBookingAPI" with "GET" Http request
Then Get is successful with Status code 200


@Regression
Scenario: To verify if User gets all booking ids by providing  firstname and last name
Given Get Request Specification using "firstname" and "last name"
When User calls  "GetBookingAPI" with "GET" Http request
Then Get is successful with Status code 200

@Regression
Scenario: To verify if User is able to  Delete  booking information successfully.
Given Delete Request header information 
When User calls  "DeleteBookingAPI" with "DELETE" Http request
Then Delete is successful with Status code 201
And Verify if user is not able to get booking information by calling "GetBookingAPI"
