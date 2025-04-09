package com.myCompany.app;

import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

/*
 * erum ad simulatea database, thvi erum ad nota hashmap til ad geyma bookings 
 * hofum ss bookings i memory map
 * 
 */

public class MockBookingRepo implements BookingRepo {

    private Map<String, Booking> bookings = new HashMap<>(); // bookingID er key, booking object er value
    private int availableSeats = 100; // byrjum med 100 laus saeti

    public MockBookingRepo() {
      // ekt herna, db a ad byrja sem tomt gagnasafn  
    }

    @Override
    public void confirmBooking(Booking booking) {

        if (availableSeats <= 0) {
            System.out.println("Engin laus saeti til! booking ekki moegulegt");
            return; // ef engin laus saeti til, skilar null
        }
        // add booking to the database
        bookings.put(booking.getBookingID(), booking);
        decrementAvailableSeats(); // draga 1 saeti fra available seats
        System.out.println("Booking buid til: " + booking.getBookingID());
    }

    @Override
    public void deleteBooking(String bookingID) {
        // delete booking from the database
        Booking removed = bookings.remove(bookingID);
        if (removed != null) { // ef bookingID er til i bookings
            incrementAvailableSeats(); // auka 1 saeti fyrir available seats
            System.out.println("Booking deleted" + removed.getBookingID());
        }
    }

    @Override
    public void updateBooking(Booking booking) {
        // update booking in the database
        // logic ekki komid
        System.out.println("Booking updated: " + booking.getBookingID());
    }

    @Override
    public boolean containsBooking(String bookingID) {
        return bookings.containsKey(bookingID); // athugum hvort bookingID se til i gagnasafni
    }

    // aetti ad na i allar current bookings i databaseinu
    @Override
    public Booking[] getBookings() {
        Collection<Booking> values = bookings.values();
        return values.toArray(new Booking[values.size()]); // skilar array af bookings
    }

    // available seats logic:

    @Override
    public int getAvailableSeats() {
        return availableSeats;
    }

    @Override
    public void decrementAvailableSeats() {
        availableSeats--;
    }

    @Override
    public void incrementAvailableSeats() {
        availableSeats++;
    }

    @Override
    public void setAvailableSeats(int seats) {
        availableSeats = seats;
    }
}
