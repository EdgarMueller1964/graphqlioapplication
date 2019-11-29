package com.thinkenterprise.graphqlioapplication.domain.mutationinput;

import java.sql.Date;
import java.util.UUID;

import com.thinkenterprise.graphqlioapplication.domain.Route;

public class CreateRouteInput {
	private String flightNumber = null;
	private String departure = null;
	private String destination = null;
	private String disabled = null;
	private UUID signature = null;
	private Date bookingDate = null;

	public CreateRouteInput() {
	}

	public CreateRouteInput(String flightNumber, String departure, String destination, String disabled, UUID signature,
			Date bookingDate) {
		this.flightNumber = flightNumber;
		this.departure = departure;
		this.destination = destination;
		this.disabled = disabled;
		this.signature = signature;
		this.bookingDate = bookingDate;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getDeparture() {
		return departure;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public UUID getSignature() {
		return signature;
	}

	public void setSignature(UUID signature) {
		this.signature = signature;
	}

	public Date getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public Route toRoute() {
		return new Route(flightNumber, departure, destination,disabled,signature,bookingDate);
	}

}
