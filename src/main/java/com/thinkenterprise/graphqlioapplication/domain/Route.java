package com.thinkenterprise.graphqlioapplication.domain;

import java.sql.Date;
import java.util.UUID;

import javax.persistence.*;


@Entity
public class Route extends AbstractEntity {

    private String flightNumber;
    private String departure;
    private String destination;
    private String disabled = "FALSE";
    private UUID signature = null;
    private Date bookingDate = null;

//    private LocalTime departureTime;
//    private LocalTime arrivalTime;
//
//    @Enumerated(EnumType.ORDINAL)
//    @ElementCollection(targetClass = DayOfWeek.class)
//    private Set<DayOfWeek> scheduledWeekdays = new HashSet<>();

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @OrderColumn(name = "date")
//    private List<Flight> flights = new ArrayList<>();
//
//    @Transient
//    private Double total;

    public Route() {
        super();
    }

    public Route(String flightNumber) {
        super();
        this.flightNumber = flightNumber;
    }

    public Route(String flightNumber, String departure, String destination) {
        super();
        this.flightNumber = flightNumber;
        this.destination = destination;
        this.departure = departure;
    }
    public Route(String flightNumber, String departure, String destination,String disabled, UUID signature,Date bookingDate) {
        super();
        this.flightNumber = flightNumber;
        this.destination = destination;
        this.departure = departure;
        this.disabled = disabled;
        this.signature = signature;
        this.bookingDate = bookingDate;
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

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String number) {
        this.flightNumber = number;
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

//
//    public void addFlight(Flight flight) {
//        flights.add(flight);
//    }
//
//    public List<Flight> getFlights() {
//        return flights;
//    }
//
//    public void setFlights(List<Flight> flights) {
//        this.flights = flights;
//    }
//
//    public Double getTotal() {
//        return total;
//    }
//
//    public void setTotal(Double total) {
//        this.total = total;
//    }
//
//    public LocalTime getDepartureTime() {
//        return departureTime;
//    }
//
//    public void setDepartureTime(LocalTime time) {
//        this.departureTime = time;
//    }
//
//    public LocalTime getArrivalTime() {
//        return arrivalTime;
//    }
//
//    public void setArrivalTime(LocalTime time) {
//        this.arrivalTime = time;
//    }
//
//    public Set<DayOfWeek> getScheduledWeekdays() {
//        return scheduledWeekdays;
//    }
//
//    public void setScheduledWeekdays(Set<DayOfWeek> scheduledWeekday) {
//        this.scheduledWeekdays = scheduledWeekday;
//    }
//
//    public void addScheduledWeekday(DayOfWeek scheduledWeekday) {
//        this.scheduledWeekdays.add(scheduledWeekday);
//    }
//
//    public void addScheduledDaily() {
//        this.scheduledWeekdays.add(DayOfWeek.MONDAY);
//        this.scheduledWeekdays.add(DayOfWeek.TUESDAY);
//        this.scheduledWeekdays.add(DayOfWeek.WEDNESDAY);
//        this.scheduledWeekdays.add(DayOfWeek.THURSDAY);
//        this.scheduledWeekdays.add(DayOfWeek.FRIDAY);
//        this.scheduledWeekdays.add(DayOfWeek.SATURDAY);
//        this.scheduledWeekdays.add(DayOfWeek.SUNDAY);
//    }

}
