package com.myCompany.app;

// controllerinn okkar
public class BookingService {

    private BookingRepo bookingRepo; // booking repo objectin okkar til ad geta breytt gognum

    public BookingService(BookingRepo bookingRepo) {
        this.bookingRepo = bookingRepo;
    }


    /**
     * byr til booking ef thad er laust saeti i flight objectinum
     * @param userID user identifier
     * @param flightID flight identifier
     * @return true ef booking var successful, false ef booking var ekki successful, auk thess ad bua til bookingID
     * @throws IllegalArgumentException ef enhv gerist, tharf ad accounta fyrir mismunandi errors
     */

    // this is just the same as the booking creator
    public boolean confirmBooking(String userID, String flightID) { 

        // annars getum vid buid til valid bookingID 
        if (bookingRepo.getAvailableSeats() <= 0) { // athugum hvort laus saeti se til
            System.out.println("Engin laus saeti til! booking ekki mogulegt!!!");
            return false; // ef engin laus saeti til, skilar false
        }
        Booking booking = new Booking(flightID, userID);
        bookingRepo.confirmBooking(booking); // add booking to the database
        return true; // ef booking var successful, skilar true
    }

    public void deleteBooking(String bookingID) {
        bookingRepo.deleteBooking(bookingID); // delete booking from the database
    }

    public void updateBooking(Booking booking) {
        bookingRepo.updateBooking(booking); // update booking in the database
    }
    public Booking[] getBookings() {
        return bookingRepo.getBookings(); // aetti ad na i allar current bookings i databaseinu
    }
}
