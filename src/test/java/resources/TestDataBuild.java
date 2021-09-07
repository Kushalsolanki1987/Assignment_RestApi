package resources;

import pojo.Booking;
import pojo.BookingDates;

public class TestDataBuild {
	Booking bk = new Booking();
	BookingDates bd = new BookingDates();

	public Booking addBookingPayload() {

		bd.setCheckin("2018-11-11");
		bd.setCheckout("2021-11-11");
		bk.setAdditionalneeds("Snacks");
		bk.setBookingdates(bd);
		bk.setDepositpaid(true);
		bk.setFirstname("Alok");
		bk.setLastname("Nath");
		bk.setTotalprice(1234);

		return bk;

	}

	public Booking updateBookingPayload(String name, String surname) {

		bk.setFirstname(name);
		bk.setLastname(surname);

		return bk;

	}

}
