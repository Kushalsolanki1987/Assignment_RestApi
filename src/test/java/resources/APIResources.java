package resources;

public enum APIResources {

	AddBookingAPI("/booking/"), UpdateBookingApi("/booking/"), GetBookingAPI("/booking/"), DeleteBookingAPI("/booking/");

	private String resource;

	APIResources(String resource) {

		this.resource = resource;
	}

	public String getResource() {

		return resource;
	}

}